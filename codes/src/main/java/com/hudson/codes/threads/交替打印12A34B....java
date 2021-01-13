package com.hudson.codes.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程，一个线程打印1-52，另一个线程打印字母A-Z，
 * 打印顺序是12A34B56C...5152Z
 *
 *
 * 注意：不能使用join来操作，join只能保证线程终止时的顺序，不能保证线程开始的顺序，见
 * https://stackoverflow.com/questions/11544843/interview-how-to-ensure-that-a-thread-runs-after-another
 */
class SwitchPrint1 {
    private final Object obj = new Object();
    private int num = 1;
    private char mChar = 'A';

    public void run(){
        ThreadNum a = new ThreadNum();
        ThreadChar c = new ThreadChar();
        a.start();
        // 保证c后于a，见下面2的【注意】，似乎这种方式并不是很优雅，在上面的stackoverflow上面有一个更好的答案是在a线程中调用b的start，这是JMM保证的顺序,
        // 因此更加的方案见下面run2
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c.start();// 有没有可能c线程先开始执行，然后先获得锁？Happen-before?，见下面2的【注意】
    }

    public void run2(){
        //保证c线程后于a线程的启动（不能使用join，因为join只能保证线程完成顺序，无法保证开始顺序）
        ThreadChar c = new ThreadChar();
        ThreadNum a = new ThreadNum(c);
        a.start();
    }

    class ThreadNum extends Thread{
        private Thread mAfterStartThread;

        public ThreadNum(){}

        public ThreadNum(Thread afterStartThread){
            mAfterStartThread = afterStartThread;
        }

        @Override
        public void run() {
            synchronized (obj){
                // 在获取锁之后，再开启要比它后面开始运行的线程
                if(mAfterStartThread != null){
                    mAfterStartThread.start();
                }
                // 【错误1】这是一个循环，要求的是交替打印到某个位置，所以应该是一个循环
                while(num<=52){
                    System.out.println(""+num);
                    num++;
                    System.out.println(""+num);
                    num++;
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 【错误2】注意：至关重要的一环，一定要先跳出循环的线程中通知唤醒它的线程，否则唤醒它的线程ThreadChar将一直等待
                // 因为没有人通知他完成了
                obj.notify();
            }
        }
    }

    class ThreadChar extends Thread{

        @Override
        public void run() {
            synchronized (obj){
                while(mChar <= 'Z'){
                    System.out.println(""+mChar);
                    mChar++;
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 如果保险起见的话，这里也应该加notify，因为实际开发中有可能不是ThreadNum唤醒的它
                obj.notify();
            }
        }
    }
}

class SwitchPrint2{
    final Lock mLock = new ReentrantLock();
    // 定义两个不同的Condition
    final Condition numCondition = mLock.newCondition();
    final Condition charCondition = mLock.newCondition();

    public void run(){
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        a.start();
        // 保证b后于a
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.start();// 【注意】实际测试过程发现，极少数情况下会先执行b后执行a，因此如果b先执行完，却没有通过signal通知a线程，a线程将一直等待下去，由于题目要求必须先数字，所以我们应该保证线程开始的先后
    }

    public void run2(){
        Thread after = new ThreadB();
        Thread a = new ThreadA(after);
        a.start();// 由a控制after的启动，避免可能的类似run中的b先于a的执行
    }

    class ThreadA extends Thread{

        private Thread mAfterStartThread;

        public ThreadA(){}

        public ThreadA(Thread afterStartThread){
            mAfterStartThread = afterStartThread;
        }

        @Override
        public void run() {
            // 第一步必须先获取锁 【注意：后面其他线程signal它的时候，是不需要重新lock操作的，因此wait的返回必须要当前线程重新获取锁
            // 见https://stackoverflow.com/questions/16760513/how-does-wait-get-the-lock-back-in-java】
            mLock.lock();
            // 在获取锁之后，再开启要比它后面开始运行的线程
            if(mAfterStartThread != null){
                mAfterStartThread.start();
            }
            try{
                int num = 1;
                while(num <= 52){
                    System.out.println(""+num);
                    num++;
                    System.out.println(""+num);
                    num++;
                    // 我执行完了，现在要阻塞，交给别人来执行
                    // 先通知，后把自己阻塞
                    charCondition.signal();// 【错误1】你通知的Condition应该是对方的Condition
                    numCondition.awaitUninterruptibly();// 不允许被外界中断
                }
                // 【注意】我优先执行完，会退出这里，所以通知另一个线程不要等待了。【如果不通知，另一个线程将一直等待下去】
                charCondition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
        }
    }

    class ThreadB extends Thread{
        @Override
        public void run() {
            mLock.lock();
            try {
                char c = 'A';
                while(c <= 'Z'){
                    System.out.println(""+c);
                    c ++;
                    // 我也执行完了，让另一个线程执行
                    numCondition.signal();
                    charCondition.awaitUninterruptibly();
                }
                // 最后时刻，被其他线程通知到后，会跳出循环，但这个时候是持有锁的，因此释放锁
//                  mLock.unlock();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock(); // 注意：如果这里不释放锁，那么线程B将在程序结束后还持有着锁
            }

        }
    }
}

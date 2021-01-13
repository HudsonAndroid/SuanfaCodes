package com.hudson.codes.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程，交替打印A1B2C3...
 */

// 方法1：使用object wait notify
class SwitchPrint {
    final Object object = new Object();

    public void run(){
        ThreadNum threadNum = new ThreadNum();
        ThreadChar threadChar = new ThreadChar(threadNum);
        threadChar.start();
    }

    class ThreadNum extends Thread{

        @Override
        public void run() {
            synchronized (object){
                int num = 1;
                while(num <= 26){
                    System.out.println(""+num);
                    num++;
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class ThreadChar extends Thread{
        private ThreadNum after;

        public ThreadChar(ThreadNum after) {
            this.after = after;
        }

        @Override
        public void run() {
            synchronized (object){
                after.start();
                char c = 'A';
                while(c <= 'Z'){
                    System.out.println(""+c);
                    c++;
                    object.notify();// 先通知，再阻塞自己，搞好先后顺序
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 由于我们先完成，因此应该避免最后另一个线程一直等待
                object.notify();
            }
        }
    }
}

// 方法二：使用ReentrantLock
class SwitchPrint_2{
    final ReentrantLock mLock = new ReentrantLock();
    final Condition numCondition = mLock.newCondition();
    final Condition charCondition = mLock.newCondition();

    public void run(){
        ThreadNum threadNum = new ThreadNum();
        ThreadChar threadChar = new ThreadChar(threadNum);
        threadChar.start();
    }

    class ThreadNum extends Thread{
        @Override
        public void run() {
            mLock.lock();
            try {
                int num = 1;
                while(num <= 26){
                    System.out.println(""+num);
                    num++;
                    // 我完成了，唤醒其他线程
                    charCondition.signal();
                    // 然后释放锁并阻塞我自己
                    numCondition.awaitUninterruptibly();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
        }
    }

    class ThreadChar extends Thread{
        private Thread after;

        public ThreadChar(Thread after) {
            this.after = after;
        }

        @Override
        public void run() {
            mLock.lock();// 先获取锁
            // 才允许开启另一个线程，这样才能保证顺序
            after.start();
            try {
                char c = 'A';
                while(c <= 'Z'){
                    System.out.println(""+c);
                    c++;
                    // 我执行完了，准备唤醒其他线程
                    numCondition.signal();
                    // 我自己释放并阻塞
                    charCondition.awaitUninterruptibly();
                }
                // 由于我们先执行完，所以应当避免另一个线程持续等待
                numCondition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
        }
    }
}


package com.hudson.codes.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程交替打印
 * Created by Hudson on 2021/1/17.
 */
public class ThreadThreadPrint {
    
    
    public static void main(String[] args){
        new Way1().run();
    }

    // 维持三个线程的执行顺序
    static class Way1 {
        final Lock mLock = new ReentrantLock();
        final Condition aCondition = mLock.newCondition();
        final Condition bCondition = mLock.newCondition();
        final Condition cCondition = mLock.newCondition();
        int count = 0;

        final Thread threadA = new Thread(){
            @Override
            public void run() {
                mLock.lock();
                threadB.start();
                try {
                    while(count < 100){
                        System.out.println(""+ (++count) + ", thread:"+Thread.currentThread().getName());
                        bCondition.signal();
                        aCondition.await();
                    }
                    // 执行完了的话，通知其他线程，避免其他线程一直阻塞
                    bCondition.signal();
                    cCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mLock.unlock();
                }
            }
        };
        final Thread threadB = new Thread(){
            @Override
            public void run() {
                mLock.lock();
                threadC.start();
                try {
                    while(count < 100){
                        System.out.println(""+ (++count) + ", thread:"+Thread.currentThread().getName());
                        cCondition.signal();
                        bCondition.await();
                    }
                    cCondition.signal();
                    aCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mLock.unlock();
                }
            }
        };
        final Thread threadC = new Thread(){
            @Override
            public void run() {
                mLock.lock();
                try {
                    while(count < 100){
                        System.out.println(""+ (++count) + ", thread:"+Thread.currentThread().getName());
                        aCondition.signal();
                        cCondition.await();
                    }
                    aCondition.signal();
                    bCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mLock.unlock();
                }
            }
        };

        public void run(){
            threadA.start();
        }
    }
    

    // 这个思路，来源于https://www.cnblogs.com/zzq-include/p/13527151.html
    // 本质上并不是线程按照顺序执行，而是所有线程杂乱执行，只不过，只有count刚好属于当前这个线程状态的时候，才输出并递增
    // 不晓得算不算钻空子哦
    static class Way2{
        int count = 1;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (count <= 100) {
                    // 上锁当前对象
                    synchronized (this) {

                        // 唤醒另一个线程
                        notifyAll();
                        if (count == 101) {
                            return;
                        }

                        int i = new Integer(Thread.currentThread().getName());
                        if (count % 3 == i) {
                            System.out.println("Thread " + Thread.currentThread().getName() + " " + count++);
                        }
                        if(count % 3==1){
                            System.out.println("==========");
                        }
                        try {
                            if (count == 101) {
                                notifyAll();
                                return;
                            } else {
                                // 释放掉锁
                                wait();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        public void run(){
            // 启动多个线程（想创建几个就创建几个）
            Thread t1 = new Thread(runnable);
            Thread t2 = new Thread(runnable);
            Thread t3 = new Thread(runnable);

            t1.setName("1");
            t2.setName("2");
            t3.setName("0");
            t1.start();
            t2.start();
            t3.start();
        }
        
    }
}

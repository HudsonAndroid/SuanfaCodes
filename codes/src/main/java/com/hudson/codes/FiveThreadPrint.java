package com.hudson.codes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 给定count=0，5个线程并发累加到100
 * Created by Hudson on 2021/1/17.
 */
public class FiveThreadPrint {

    public static void main(String[] args){
        new Way2().run();
    }

    // 最快思路，使用AtomicInteger
    static class Way1{
        final CountDownLatch mCountDownLatch = new CountDownLatch(100);
        final AtomicInteger mInteger = new AtomicInteger(0);

        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                while(mInteger.intValue() < 100){
                    int result = mInteger.incrementAndGet();
                    System.out.println(""+result + ", thread:"+Thread.currentThread().getName());
                    mCountDownLatch.countDown();
                }
            }
        };

        public void run(){
            for (int i = 0; i < 5; i++) {
                Thread thread = new Thread(mRunnable);
                thread.start();
            }
            try {
                mCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成，最终结果是"+mInteger.intValue());
        }
    }

    // 加锁操作（貌似只有一个线程操作）
    static class Way2{
        int count = 0;
        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                // 错误，synchronized应该放到外面，这与单例模式类似，如果放到里面，外面判断条件成立，但等待获取锁的过程
                // 中可能已经被加到100了
//                while(count < 100){
//                    synchronized (this){
//                        count++;
//                        System.out.println(""+count+", thread:"+Thread.currentThread().getName());
//                    }
//                }
                synchronized (this){
                    while(count < 100){
                        count++;
                        System.out.println(""+count+", thread:"+Thread.currentThread().getName());
                        // 这个说是让出CPU给别人执行（当然自己也会抢夺），但实际发现由于synchronized的优化，每次都是第一个线程执行
                        Thread.yield();
                    }
                }
            }
        };

        public void run(){
            for (int i = 0; i < 5; i++) {
                Thread thread = new Thread(mRunnable);
                thread.start();
            }
        }
    }
}

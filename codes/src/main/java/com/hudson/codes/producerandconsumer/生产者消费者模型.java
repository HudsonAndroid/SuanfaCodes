package com.hudson.codes.producerandconsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型
 * 核心是阻塞队列。
 */
class ProducerAndConsumer {

    public void run(){
        IBlockingQueue<Integer> queue = new ObjectQueue<>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        consumer.start();
        producer.start();
    }

    // 阻塞队列第一种形式
    class ObjectQueue<T> implements IBlockingQueue<T>{
        final Object object = new Object();
        // 队列的实际容器
        final LinkedList<T> container = new LinkedList<>();
        // 定义阻塞队列大小
        final int queueSize;

        public ObjectQueue(int queueSize) {
            this.queueSize = queueSize;
        }

        @Override
        public void put(T data) {
            // 为了保证不超标，需要在快超标的时候，阻塞，即wait，因此需要在同步代码块中
            synchronized (object){
                // 用while，不用if，因为如果外界代码写的有问题，可能会被唤醒，导致已经超标还添加
                while(container.size() >= queueSize){
                    // 阻塞
                    try {
                        System.out.println("队列满了");
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 添加元素到容器
                container.offer(data);
                // 进来一个元素，那么通知为空的情况已经不存在
                object.notifyAll();
            }
        }

        @Override
        public T take() {
            // 当容器已经空了，则也要阻塞等待有东西在里面
            synchronized (object){
                if(container.size() == 0){
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 从容器中获取一个元素， peek是查看元素，poll才是pop差不多
                T peek = container.poll();
                // 出了一个元素了，那么通知已满情况已经不存在
                object.notifyAll();
                return peek;
            }
        }
    }

    // 使用Lock实现的生产者消费者
    class ConditionQueue<T> implements IBlockingQueue<T> {
        final Lock mLock = new ReentrantLock();
        final Condition queueEmpty = mLock.newCondition();
        final Condition queueFull = mLock.newCondition();
        final LinkedList<T> queue = new LinkedList<>();
        final int queueSize;

        public ConditionQueue(int queueSize) {
            this.queueSize = queueSize;
        }

        @Override
        public void put(T data) {
            // 先获取锁
            mLock.lock();
            try {
                // 如果已经满了，那么等待且先通知消费者消费
                while(queue.size() >= queueSize){
                    System.out.println("队列满了");
                    queueFull.await();
                }
                // 添加元素到队列
                queue.offer(data);
                // 有元素了，通知可以消费了
                queueEmpty.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
        }

        @Override
        public T take() {
            mLock.lock();
            try {
                while(queue.size() == 0){
                    queueEmpty.await();
                }
                // 消费
                T poll = queue.poll();
                // 消费过了，所以容器不会满了，通知可以继续生产了
                queueFull.signal();
                return poll;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
            return null;
        }
    }
}

interface IBlockingQueue<T> {
    void put(T data); // put过程如果队列满了，会阻塞put方法
    T take(); // take过程如果队列空了，会阻塞take方法
}


class Producer extends Thread{
    private IBlockingQueue<Integer> mQueue;
    private int product = 0;

    public Producer(IBlockingQueue<Integer> queue) {
        mQueue = queue;
    }

    @Override
    public void run() {
        while(true){
            mQueue.put(product++);
            System.out.println("提供完一个元素，下一个将要提供的是"+product);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{
    private IBlockingQueue<Integer> mQueue;

    public Consumer(IBlockingQueue<Integer> queue) {
        mQueue = queue;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("准备消费");
            Integer product = mQueue.take();
            System.out.println("消费者消费元素"+product);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

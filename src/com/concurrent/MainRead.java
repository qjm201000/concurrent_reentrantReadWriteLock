package com.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 几个线程都申请读锁，都能用
 */
public class MainRead {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final MainRead main = new MainRead();

        new Thread(new Runnable() {
            @Override
            public void run() {
                main.insert(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                main.insert(Thread.currentThread());
            }
        }).start();
    }

    /**
     * 几个线程都申请读锁，都能用
     * @param thread
     */
    public void insert(Thread thread){
        reentrantReadWriteLock.readLock().lock();
        try{
            for(int i=0;i<5;i++){
                System.out.println(thread.getName()+":"+i+",进行读操作");
                TimeUnit.SECONDS.sleep(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"释放读锁");
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}

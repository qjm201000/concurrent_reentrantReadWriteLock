package com.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 几个线程，一个线程抢占了读锁，别的线程想用写锁时，需要等待读锁完成才行
 */
public class MainWrite {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final MainWrite main = new MainWrite();

        new Thread(new Runnable() {
            @Override
            public void run() {
                main.read(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.write(Thread.currentThread());
            }
        }).start();
    }

    public void read(Thread thread){
        reentrantReadWriteLock.readLock().lock();
        try{
            display(thread);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"释放读锁");
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void write(Thread thread){
        reentrantReadWriteLock.writeLock().lock();
        try{
            display(thread);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"释放写锁");
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void display(Thread thread) throws InterruptedException {
        for(int i=0;i<5;i++){
            System.out.println(thread.getName()+":"+i+",进行操作");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

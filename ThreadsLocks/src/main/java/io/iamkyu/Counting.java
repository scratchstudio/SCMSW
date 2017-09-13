package io.iamkyu;

/**
 * @author Kj Nam
 */
public class Counting {
    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        class CountThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.increment();
                }
            }
        }

        CountThread t1 = new CountThread();
        CountThread t2 = new CountThread();

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println(counter.getCount());

         // 20,000을 기대하지만 실행할 때마다 20,000 미만임.
    }
}

class Counter {
    private int count = 0;

    public void increment() {
        ++count;
    }

    public int getCount() {
        return count;
    }
}



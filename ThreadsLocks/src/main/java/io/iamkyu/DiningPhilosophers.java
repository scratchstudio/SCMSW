package io.iamkyu;

import java.util.Random;

/**
 * @see <a href="http://en.wikipedia.org/wiki/Dining_philosophers_problem">http://en.wikipedia.org/wiki/Dining_philosophers_problem</a>
 *
 * @author Kj Nam
 */

public class DiningPhilosophers extends Thread {
    public static void main(String[] args) {
        Chopstick left = new Chopstick();
        Chopstick right = new Chopstick();
        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i, left, right);

            Thread t = new Thread(philosophers[i], "Philosopher " + (i+1));
            t.start();
        }
    }
}



class Philosopher extends Thread {
    private Chopstick left;
    private Chopstick right;

    private long id;

    private Random random;

    public Philosopher(long id, Chopstick left, Chopstick right) {
        this.id = id;
        this.left = left;
        this.right = right;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            think();

            synchronized (left) {
                System.out.println(String.format("%d lift left chopsticks", id));

                synchronized (right) {
                    System.out.println(String.format("%d lift right chopsticks", id));

                    eat();
                }
            }
        }
    }

    private void eat() {
        try {
            System.out.println(String.format("%d eating...", id));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private void think() {
        try {
            System.out.println(String.format("%d thinking...", id));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}



class Chopstick {
}


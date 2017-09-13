package io.iamkyu;

/**
 * @author Kj Nam
 */
public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() ->
                System.out.println("Hello from new Thread"));

        thread.start();
        Thread.yield();
        System.out.println("Hello from main Thread");
        thread.join();

        // 코드를 실행할 때 마다 같은 결과가 보장되지 않는 코드
    }
}

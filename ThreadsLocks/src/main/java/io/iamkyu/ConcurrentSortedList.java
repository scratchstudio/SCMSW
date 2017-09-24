package io.iamkyu;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 멀티스레드 환경에서 연결리스트에 노드를 삽입할 때,
 * 전체 리스트를 잠그는게 아니라 일정 부분만 잠그는
 * 협동잠그기 예제
 *
 * @author Kj Nam
 */
public class ConcurrentSortedList {
    private class Node {
        int value;
        Node prev;
        Node next;
        ReentrantLock lock = new ReentrantLock();

        public Node() {
        }

        public Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public void insert(int value) {
        Node current  = head;
        current.lock.lock();
        Node next = current.next;

        try {
            while (true) {
                next.lock.lock();
                try {
                    if (next == tail || next.value < value) {
                        Node node = new Node(value, current, next);
                        next.prev = node;
                        current.next = node;
                        return;
                    }
                } finally {
                    current.lock.unlock();
                }

                current = next;
                next = current.next;
            }
        } finally {
            next.lock.unlock();
        }
    }
}

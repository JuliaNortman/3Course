/*
CAS (Compare and Set) - это принцип параллельного программирования, суть
которого заключается в следующем:
1. Читаем значение переменной;
2. Производим некоторую обработку;
3. Производим проверку текущего значения переменной в предположении, что
значение все еще равно старому;
4. Если значение было изменено другим потоком, то повторяем обработку.

Неблокирующая очередь - это вариант очереди, реализованной на потоках. Это
означает, что базовые операции вставки и удаления выполняются быстрее за счёт
того, что над ними работают несколько обработчиков. Однако, во избежание
блокировки потоков, в этой очереди используется принцип CAS.
 */

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    private AtomicReference<Node> head, tail;

    public LockFreeQueue() {
        head = new AtomicReference(new Node(null));
        tail = head;
    }

    public class Node {

        private T value;
        private AtomicReference<Node> next;

        public Node(T v) {
            value = v;
            next = new AtomicReference<>(null);
        }
    }

    public T deq() throws IllegalStateException {
        while (true) {
            Node first = head.get();
            Node last = tail.get();
            Node next = first.next.get();

            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        throw new IllegalStateException("The Queue is empty");
                    }
                    tail.compareAndSet(last, next);
                } else {
                    T value = next.value;

                    if (head.compareAndSet(first, next)) {
                        return value;
                    }
                }
            }
        }
    }

    public void show(LockFreeQueue Q) {
        Node first = head.get();
        Node last = tail.get();
        while (first != last.next.get()) {
            if (first != head.get()) {
                System.out.print(first.value + " ");
            }
            first = first.next.get();
        }
        System.out.println();
    }

    public void enq(T value) {
        Node node = new Node(value);

        while (true) {
            Node last = tail.get();
            Node next = last.next.get();

            if (last == tail.get()) {

                if (next == null) {

                    if (last.next.compareAndSet(next, node)) {
                        tail = new AtomicReference(node);
                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public static void main(String args[]) {
        System.out.println("Code built successfully!");
    }
}

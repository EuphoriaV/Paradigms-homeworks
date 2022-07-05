package queue;

import java.util.List;

public class MyArrayQueueTest {
    private static Object[] mas = {"Hello", 3, 0, List.of(100, 20)};

    public static void test1(ArrayQueue queue) {
        for (int i = 0; i < mas.length; i++) {
            queue.enqueue(mas[i]);
            System.out.println("\nSize: " + queue.size() + "\nFirst Element: " + queue.element() + "\nIs Empty: " + queue.isEmpty());
        }
        System.out.println("\nClear");
        queue.clear();
        System.out.println("\nSize: " + queue.size() + "\nIs Empty: " + queue.isEmpty());
    }

    public static void test2(ArrayQueue queue) {
        for (int i = 0; i < mas.length; i++) {
            queue.enqueue(mas[i]);
            System.out.println("\nSize: " + queue.size() + "\nPopped Element: " + queue.dequeue() + "\nIs Empty: " + queue.isEmpty());
        }
        System.out.println("\nClear");
        queue.clear();
        System.out.println("\nSize: " + queue.size() + "\nIs Empty: " + queue.isEmpty());
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        System.out.println("Test1 ---------------------------");
        test1(queue);
        System.out.println("\nTest2 ---------------------------");
        test2(queue);
    }
}

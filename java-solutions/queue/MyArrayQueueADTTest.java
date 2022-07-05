package queue;

import java.util.List;

public class MyArrayQueueADTTest {
    private static Object[] mas = {"Hello", 3, 0, List.of(100, 20)};

    public static void test1(ArrayQueueADT queue) {
        for (int i = 0; i < mas.length; i++) {
            queue.enqueue(queue, mas[i]);
            System.out.println("\nSize: " + queue.size(queue) + "\nFirst Element: " + queue.element(queue) + "\nIs Empty: " + queue.isEmpty(queue));
        }
        System.out.println("\nClear");
        queue.clear(queue);
        System.out.println("\nSize: " + queue.size(queue) + "\nIs Empty: " + queue.isEmpty(queue));
    }

    public static void test2(ArrayQueueADT queue) {
        for (int i = 0; i < mas.length; i++) {
            queue.enqueue(queue, mas[i]);
            System.out.println("\nSize: " + queue.size(queue) + "\nPopped Element: " + queue.dequeue(queue) + "\nIs Empty: " + queue.isEmpty(queue));
        }
        System.out.println("\nClear");
        queue.clear(queue);
        System.out.println("\nSize: " + queue.size(queue) + "\nIs Empty: " + queue.isEmpty(queue));
    }

    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        System.out.println("Test1 ---------------------------");
        test1(queue);
        System.out.println("\nTest2 ---------------------------");
        test2(queue);
    }
}

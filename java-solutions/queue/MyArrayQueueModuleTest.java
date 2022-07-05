package queue;

import java.util.List;

public class MyArrayQueueModuleTest {
    private static Object[] mas = {"Hello", 3, 0, List.of(100, 20)};

    public static void test1() {
        for (int i = 0; i < mas.length; i++) {
            ArrayQueueModule.enqueue(mas[i]);
            System.out.println("\nSize: " + ArrayQueueModule.size() + "\nFirst Element: " + ArrayQueueModule.element() + "\nIs Empty: " + ArrayQueueModule.isEmpty());
        }
        System.out.println("\nClear");
        ArrayQueueModule.clear();
        System.out.println("\nSize: " + ArrayQueueModule.size() + "\nIs Empty: " + ArrayQueueModule.isEmpty());
    }

    public static void test2() {
        for (int i = 0; i < mas.length; i++) {
            ArrayQueueModule.enqueue(mas[i]);
            System.out.println("\nSize: " + ArrayQueueModule.size() + "\nPopped Element: " + ArrayQueueModule.dequeue() + "\nIs Empty: " + ArrayQueueModule.isEmpty());
        }
        System.out.println("\nClear");
        ArrayQueueModule.clear();
        System.out.println("\nSize: " + ArrayQueueModule.size() + "\nIs Empty: " + ArrayQueueModule.isEmpty());
    }

    public static void main(String[] args) {
        System.out.println("Test1 ---------------------------");
        test1();
        System.out.println("\nTest2 ---------------------------");
        test2();
    }


}

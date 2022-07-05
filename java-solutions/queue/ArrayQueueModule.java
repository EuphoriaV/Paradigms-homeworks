package queue;

public class ArrayQueueModule {
    //Model: a[1] ... a[n]
    //Invariant a[i] != null for i in [1 ... n] && user is able to push sth in the end of the queue and get element from the top of the queue
    private static Object[] mas = new Object[10];
    private static int head = 0, size = 0;

    //Pred: a != null
    //Post: tail' = tail + 1 && mas'[1] = a, for all i 2 ... n - 1: mas'[i] = mas[i-1]
    public static void enqueue(Object a) {
        ensureCapacity();
        mas[(head + size) % mas.length] = a;
        size++;
    }

    private static void ensureCapacity() {
        if (size >= mas.length - 1) {
            Object[] a = new Object[2 * mas.length];
            for (int i = 0; i < size; i++) {
                a[i] = mas[(i + head) % mas.length];
            }
            mas = a;
            head = 0;
        }
    }

    //Pred: size > 0
    //Post R = mas[1]
    public static Object element() {
        return mas[head];
    }

    //Pred: size > 0
    //Post R = mas[1] && size' = size - 1 && for all i in 2 ... n: mas'[i] = mas[i + 1]
    public static Object dequeue() {
        Object r = mas[head];
        head = (head + 1) % mas.length;
        size--;
        return r;
    }

    //Pred: true
    //Post R = n
    public static int size() {
        return size;
    }

    //Pred: true
    //Post R = (n == 0)
    public static boolean isEmpty() {
        return size == 0;
    }


    //Pred: true
    //Post size' = 0
    public static void clear() {
        head = 0;
        size = 0;
    }


    //Pred: true
    //Post: return -1 if queue doesn't contains a or return i_min: mas[i] = a
    public static int indexOf(Object a) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (mas[(i + head)% mas.length].equals(a)) {
                index = i;
                break;
            }
        }
        return index;
    }

    //Pred: true
    //Post: return -1 if queue doesn't contains a or return i_max: mas[i] = a
    public static int lastIndexOf(Object a) {
        int index = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (mas[(i + head)% mas.length].equals(a)) {
                index = i;
                break;
            }
        }
        return index;
    }

}

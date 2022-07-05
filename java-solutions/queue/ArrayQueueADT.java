package queue;

public class ArrayQueueADT {
    //Model: a[1] ... a[n]
    //Invariant a[i] != null for i in [1 ... n] && user is able to push sth in the end of the queue and get element from the top of the queue
    private Object[] mas = new Object[10];
    private int head = 0, size = 0;

    //Pred: a != null
    //Post: tail' = tail + 1 && mas'[1] = a, for all i 2 ... n - 1: mas'[i] = mas[i-1]
    public static void enqueue(ArrayQueueADT queue, Object a) {
        ensureCapacity(queue);
        queue.mas[(queue.head + queue.size) % queue.mas.length] = a;
        queue.size++;
    }

    private static void ensureCapacity(ArrayQueueADT queue) {
        if (queue.size >= queue.mas.length - 1) {
            Object[] a = new Object[2 * queue.mas.length];
            for (int i = 0; i < queue.size; i++) {
                a[i] = queue.mas[(i + queue.head) % queue.mas.length];
            }
            queue.mas = a;
            queue.head = 0;
        }
    }

    //Pred: size > 0
    //Post R = mas[1]
    public static Object element(ArrayQueueADT queue) {
        return queue.mas[queue.head];
    }

    //Pred: size > 0
    //Post R = mas[1] && size' = size - 1 && for all i in 2 ... n: mas'[i] = mas[i + 1]
    public static Object dequeue(ArrayQueueADT queue) {
        Object r = queue.mas[queue.head];
        queue.head = (queue.head + 1) % queue.mas.length;
        queue.size--;
        return r;
    }

    //Pred: true
    //Post R = n
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //Pred: true
    //Post R = (n == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }


    //Pred: true
    //Post size' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.head = 0;
        queue.size = 0;
    }

    //Pred: true
    //Post: return -1 if queue doesn't contains a or return i_min: mas[i] = a
    public static int indexOf(ArrayQueueADT queue,Object a) {
        int index = -1;
        for (int i = 0; i < queue.size; i++) {
            if (queue.mas[(i + queue.head)% queue.mas.length].equals(a)) {
                index = i;
                break;
            }
        }
        return index;
    }

    //Pred: true
    //Post: return -1 if queue doesn't contains a or return i_max: mas[i] = a
    public static int lastIndexOf(ArrayQueueADT queue,Object a) {
        int index = -1;
        for (int i = queue.size - 1; i >= 0; i--) {
            if (queue.mas[(i + queue.head)% queue.mas.length].equals(a)) {
                index = i;
                break;
            }
        }
        return index;
    }


}

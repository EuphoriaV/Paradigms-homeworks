package queue;
// Меня можно не проверять
// Я уже сдал это дз АЮ
// Выздоравливайте :D
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {

    private Object[] mas = new Object[10];
    private int head = 0;

    @Override
    protected void enqueueImpl(Object a) {
        ensureCapacity();
        mas[(head + size) % mas.length] = a;
    }

    private void ensureCapacity() {
        if (size >= mas.length - 1) {
            Object[] a = new Object[2 * mas.length];
            for (int i = 0; i < size; i++) {
                a[i] = mas[(i + head) % mas.length];
            }
            mas = a;
            head = 0;
        }
    }

    public Object element() {
        return mas[head];
    }

    @Override
    protected void dequeueImpl() {
        head = (head + 1) % mas.length;
    }

    public void clear() {
        head = 0;
        size = 0;
        for (int i = 0; i < mas.length; i++) {
            mas[i] = null;
        }
    }

    public int lastIndexIf(final Predicate<Object> p){
        int index = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (p.test(mas[(i + head)% mas.length])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int indexIf(final Predicate<Object> p){
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (p.test(mas[(i + head)% mas.length])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int indexOf(Object a) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (mas[(i + head)% mas.length].equals(a)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int lastIndexOf(Object a) {
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

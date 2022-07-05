package queue;
// Меня можно не проверять
// Я уже сдал это дз АЮ
// Выздоравливайте :D
public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public void enqueue(Object a) {
        enqueueImpl(a);
        size++;
    }

    public Object dequeue() {
        Object r = element();
        size--;
        dequeueImpl();
        return r;
    }

    protected abstract void enqueueImpl(Object a);

    protected abstract void dequeueImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

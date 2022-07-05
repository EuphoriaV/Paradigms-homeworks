package queue;
// Меня можно не проверять
// Я уже сдал это дз АЮ
// Выздоравливайте :D
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {

    private static class Node {
        private Object element;
        private Node next;

        public Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node head, tail;

    @Override
    protected void enqueueImpl(Object a){
        if (size == 0) {
            head = tail = new Node(a, null);
        } else {
            Node b = new Node(a, null);
            tail.next = b;
            tail = b;
        }
    }
    
    public Object element() {
        return head.element;
    }

    @Override
    protected void dequeueImpl() {
        head = head.next;
    }

    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    public int lastIndexIf(final Predicate<Object> p){
        Node cur = head;
        int i = 0,ans = -1;
        while (cur!=null){
            if(p.test(cur.element)){
                ans = i;
            }
            i++;
            cur = cur.next;
        }
        return ans;
    }

    public int indexIf(final Predicate<Object> p){
        Node cur = head;
        int i = 0;
        while (cur!=null){
            if(p.test(cur.element)){
                return i;
            }
            i++;
            cur = cur.next;
        }
        return -1;
    }
}

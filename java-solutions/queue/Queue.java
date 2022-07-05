package queue;
// Меня можно не проверять
// Я уже сдал это дз АЮ
// Выздоравливайте :D
import java.util.function.Predicate;

public interface Queue {
    //Model: a[1] ... a[n]
    //Invariant a[i] != null for i in [1 ... n] && user is able to push sth in the end of the queue and get or pull element from the top of the queue

    //Pred: a != null
    //Post: n' = n + 1 && mas'[n] = a, for all i 1 ... n - 1: mas'[i] = mas[i-1]
    void enqueue(Object a);

    //Pred: size > 0
    //Post: R = mas[1]
    Object element();

    //Pred: n > 0
    //Post: R = mas[1] && n' = n - 1 && for all i in 1 ... n: mas'[i] = mas[i + 1]
    Object dequeue();

    //Pred: true
    //Post: R = n
    int size();

    //Pred: true
    //Post: R = (n == 0)
    boolean isEmpty();

    //Pred: true
    //Post: n' = 0
    void clear();

    //Pred: true
    //Post: returns first index of element that matches the predicate or returns -1 if there are no elements that match predicate 
    int indexIf(final Predicate<Object> p);

    //Pred: true
    //Post: returns last index of element that matches the predicate or returns -1 if there are no elements that match predicate 
    int lastIndexIf(final Predicate<Object> p);
}

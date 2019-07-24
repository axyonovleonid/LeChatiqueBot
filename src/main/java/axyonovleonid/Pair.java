package axyonovleonid;

public class Pair<T, B> {
    T first;
    B second;

    public Pair(T t, B b) {
        this.first = t;
        this.second = b;
    }

    //    public Pair<T, B> (T first, B second){

    //    }
    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }
}

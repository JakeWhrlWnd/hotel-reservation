package model;

public class Pair<K, V> {
    private final K bookedCheckIn;
    private final V bookedCheckOut;

    public static <K, V> Pair<K, V> createPair(K bookedCheckIn, V bookedCheckOut) {
        return new Pair<K, V>(bookedCheckIn, bookedCheckOut);
    }

    public Pair(K bookedCheckIn, V bookedCheckOut) {
        this.bookedCheckIn = bookedCheckIn;
        this.bookedCheckOut = bookedCheckOut;
    }

    public K getBookedCheckIn() {
        return bookedCheckIn;
    }

    public V getBookedCheckOut() {
        return bookedCheckOut;
    }
}

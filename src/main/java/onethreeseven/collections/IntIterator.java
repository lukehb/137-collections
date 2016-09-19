package onethreeseven.collections;


/**
 * Iterator for int[].
 * But can also be copied and position initialised at specified index.
 * @author Luke Bermingham
 */
public class IntIterator {
    private int i = 0;
    private final int[] arr;

    public IntIterator(int[] arr, int i){
        this.arr = arr;
        if(i < 0 || i >= arr.length){
            throw new IllegalArgumentException("Index i must be between 0 and " + arr.length);
        }
        this.i = i;
    }

    public IntIterator(int[] arr) {
        this(arr, 0);
    }

    public boolean hasNext() {
        return i < arr.length;
    }

    public boolean hasPrev(){
        return  i >= 0;
    }

    /**
     * Has a look at the current int, but does not move the iterator forward.
     * @return The int at the current index.
     */
    public int peek(){
        return arr[i];
    }

    public int prev(){
        int res = arr[i];
        i--;
        return res;
    }

    public int next() {
        int res = arr[i];
        i++;
        return res;
    }

    public void set(IntIterator iter){
        this.i = iter.i;
    }

    public IntIterator copy() {
        IntIterator iter = new IntIterator(arr);
        iter.i = i;
        return iter;
    }

}

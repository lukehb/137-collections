package onethreeseven.collections;

import java.nio.*;

/**
 * Array of ints that grows dynamically.
 * More memory efficient that ArrayList of Integers.
 * @author Luke Bermingham
 */
public class IntArray extends AbstractPrimitiveArray {

    public IntArray(IntArray toCopy, int capacity){
        super(capacity, toCopy.offHeap);
        IntBuffer bufDup = (IntBuffer) toCopy.duplicate();
        bufDup.rewind();
        bufDup.limit(toCopy.size);
        ((IntBuffer)this.buf).put(bufDup);
        this.size = toCopy.size;
    }

    public IntArray(int[] arr, boolean offHeap){
        super(arr.length, offHeap);
        ((IntBuffer)buf).put(arr);
        this.size = arr.length;
    }

    public IntArray(int initialCapacity, boolean offHeap){
        super(initialCapacity, offHeap);
    }

    public int get(int i){
        return ((IntBuffer)buf).get(i);
    }

    public void add(int d){
        if(buf.remaining() == 0){
            grow();
        }
        ((IntBuffer)buf).put(d);
        size++;
    }

    public void clear(){
        buf.clear();
        size = 0;
    }

    /**
     * @return The array of the buffers data, be careful if this is on-heap modifications to this
     * array will affect the buffer.
     */
    public int[] getArray(){
        Buffer activeBuf = getFilledBuffer(false);
        activeBuf.rewind();
        int[] dest = new int[size];
        ((IntBuffer)activeBuf).get(dest);
        return dest;
    }

    @Override
    Buffer asReadOnlyBuffer(Buffer buf) {
        return ((IntBuffer)buf).asReadOnlyBuffer();
    }

    @Override
    Buffer duplicate() {
        return ((IntBuffer)buf).duplicate();
    }

    @Override
    void copyToStart(Buffer src, Buffer dest, int offset, int length) {
        src.position(offset);
        src.limit(length);
        ((IntBuffer)dest).put((IntBuffer) src);
    }

    @Override
    Buffer newBuffer(int size) {
        if(offHeap){
            return ByteBuffer.allocateDirect(size * 4)
                    .order(ByteOrder.nativeOrder())
                    .asIntBuffer();
        }
        else{
            return IntBuffer.wrap(new int[size]);
        }
    }
}

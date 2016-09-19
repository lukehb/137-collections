package onethreeseven.collections;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * Array of doubles that grows dynamically.
 * More memory efficient that ArrayList of {@link Double}.
 * @author Luke Bermingham
 */
public class DoubleArray extends AbstractPrimitiveArray {

    public DoubleArray(int initialCapacity, boolean offHeap){
        super(initialCapacity, offHeap);
    }

    public void add(double d){
        if(buf.remaining() == 0){
            grow();
        }
        ((DoubleBuffer)buf).put(d);
        size++;
    }

    public void addAll(double[] d){
        while(buf.remaining() < d.length){
            grow();
        }
        ((DoubleBuffer)buf).put(d);
        size += d.length;
    }

    /**
     * @return The array of the buffers data, be careful if this is on-heap modifications to this
     * array will affect the buffer.
     */
    public double[] getArray(){
        Buffer activeBuf = getFilledBuffer(false);
        double[] dest = new double[size];
        activeBuf.rewind();
        ((DoubleBuffer)activeBuf).get(dest);
        return dest;
    }

    @Override
    Buffer asReadOnlyBuffer(Buffer buf) {
        return ((DoubleBuffer)buf).asReadOnlyBuffer();
    }

    @Override
    Buffer duplicate() {
        return ((DoubleBuffer)buf).duplicate();
    }

    @Override
    void copyToStart(Buffer src, Buffer dest, int offset, int length) {
        src.position(offset);
        src.limit(length);
        ((DoubleBuffer)dest).put((DoubleBuffer) src);
    }

    @Override
    Buffer newBuffer(int size) {
        if(offHeap){
            return ByteBuffer.allocateDirect(size * 8)
                    .order(ByteOrder.nativeOrder())
                    .asDoubleBuffer();
        }
        else{
            return DoubleBuffer.wrap(new double[size]);
        }
    }

}

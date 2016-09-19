package onethreeseven.collections;

import java.nio.Buffer;

/**
 * An buffer of primitives that can be stored on-heap or off-heap.
 * It handles growing the buffer size internally itself.
 * But lacks some of the functionality of traditional collections,
 * such as iteration or removal.
 * @author Luke Bermingham
 */
abstract class AbstractPrimitiveArray {

    int size = 0;
    Buffer buf;
    final boolean offHeap;

    AbstractPrimitiveArray(int initialCapacity, boolean offHeap){
        this.offHeap = offHeap;
        buf = newBuffer(initialCapacity);
    }

    /**
     * @return A readonly version of the part of the buffer that is filled with data.
     */
    public Buffer getBuffer(){
        return getFilledBuffer(true);
    }

    public int size() {
        return size;
    }

    /**
     * @param readonly whether or not to make it readonly.
     * @return Get the portion of the buffer from 0 to limit().
     */
    public Buffer getFilledBuffer(boolean readonly){
        //needs to be trimmed, it has empty space on the back
        if(this.size < this.buf.capacity()){
            Buffer trimmed = newBuffer(size);
            copyToStart(this.buf, trimmed, 0, size);
            trimmed.limit(trimmed.capacity());
            trimmed.position(trimmed.capacity());
            return readonly ? asReadOnlyBuffer(trimmed) : trimmed;
        }
        else{
            Buffer activeBuf = duplicate();
            activeBuf.limit(activeBuf.capacity());
            activeBuf.position(activeBuf.capacity());
            return readonly ? asReadOnlyBuffer(activeBuf) : activeBuf;
        }
    }

    void grow(){
        int newSize = (buf.capacity() * 3)/2 + 1;
        Buffer newBuf = newBuffer(newSize);
        copyToStart(buf, newBuf, 0, buf.position());
        buf = newBuf;
    }

    /**
     * Duplicates the underlying buffer, but not changes can be made.
     * @return The underlying buffer, but not changes can be made.
     */
    abstract Buffer asReadOnlyBuffer(Buffer buf);

    /**
     * Duplicates the underlying buffer.
     * @return A shallow copy of the working buffer, changes
     * will be reflected.
     */
    abstract Buffer duplicate();

    /**
     * Copy the source buffer and paste it into the start of the destination buffer.
     * @param src source buffer
     * @param dest destination buffer
     * @param offset the offset into the source buffer
     * @param length how much of the source to copy
     */
    abstract void copyToStart(Buffer src, Buffer dest, int offset, int length);

    /**
     * Makes a new buffer of a given size.
     * @param size The number of elements to fit in the buffer.
     *             Not necessarily the number of bytes.
     * @return A new buffer
     *         (note depending on whether the constructor was on-heap
     *          or off-heap this buffer will be the same).
     */
    abstract Buffer newBuffer(int size);

}

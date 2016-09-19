package onethreeseven.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link IntArray}
 * @author Luke Bermingham
 */
public class IntArrayTest {

    private static final int[] data = {1,3,7,7,3,1};

    @Test
    public void addAndCheckSize(){
        IntArray arr = new IntArray(5, false);
        for (int i : data) {
            arr.add(i);
        }
        //check size
        Assert.assertTrue(arr.size() == data.length);
    }

    @Test
    public void addAndCheckElements(){
        IntArray arr = new IntArray(5, false);
        for (int i : data) {
            arr.add(i);
        }
        //check element by element
        for (int i = 0; i < data.length; i++) {
            Assert.assertTrue(arr.get(i) == data[i]);
        }
    }

    @Test
    public void bulkAddAndCheckElements(){
        IntArray arr = new IntArray(data, false);
        Assert.assertArrayEquals(data, arr.getArray());
    }

}

package onethreeseven.collections;

import org.junit.Assert;
import org.junit.Test;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Luke Bermingham
 */
public class DoubleArrayTest {

    private static Logger logger = Logger.getLogger(DoubleArrayTest.class.getSimpleName());

    @Test
    public void testAddOnHeap(){

        int n = 10000;

        DoubleArray arr = new DoubleArray(10, false);
        for (int i = 0; i < n; i++) {
            arr.add(i);
        }
        Assert.assertTrue(arr.getArray().length == n);

    }

    @Test
    public void testAddSingleAndMulti(){
        int n = 10000;
        Random rand = new Random();

        ArrayList<Double> added = new ArrayList<>(n);

        DoubleArray arr = new DoubleArray(n, false);
        for (int i = 0; i < n; i++) {
            int len = rand.nextInt(8) + 1;
            //add single entry
            if(len == 1){
                double v = rand.nextDouble();
                arr.add(v);
                added.add(v);
            }
            //add multiple entries
            else{
                double[] v = new double[len];
                for (int j = 0; j < len; j++) {
                    v[j] = rand.nextDouble();
                    added.add(v[j]);
                }
                arr.addAll(v);
            }
        }

        //same size
        Assert.assertTrue(arr.size == added.size());

        //exact same entries in the same spots
        DoubleBuffer trimmed = (DoubleBuffer) arr.getFilledBuffer(false);
        for (int i = 0; i < added.size(); i++) {
            Assert.assertEquals(trimmed.get(i), added.get(i), 1e-10);
        }

    }

    @Test
    public void testAddLotsOfDoubles() throws Exception {

        int mb = 1024 * 1024;

        Runtime runtime = Runtime.getRuntime();
        int freeMemoryBefore = (int) (runtime.freeMemory()/mb);
        logger.info("Free memory now: " + freeMemoryBefore + "mb");
        int allocatedMemoryBefore = (int) (runtime.totalMemory()/mb);
        logger.info("Allocated memory now: " + allocatedMemoryBefore + "mb");

        logger.info("Making massive OffHeap data...");
        int size = (int) 1e7;
        DoubleArray ohArr = new DoubleArray(size, true);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            ohArr.add(rand.nextInt());
        }

        System.gc();
        Thread.sleep(5L);

        int freeMemoryAfter = (int) (runtime.freeMemory()/mb);
        logger.info("Free memory now: " + freeMemoryAfter + "mb");
        int allocatedMemoryAfter = (int) (runtime.totalMemory()/mb);
        logger.info("Allocated memory now: " + allocatedMemoryAfter + "mb");

        //the difference should certainly be now more than 100mb
        Assert.assertTrue(Math.abs(freeMemoryAfter - freeMemoryBefore) < 100);
        Assert.assertTrue(Math.abs(allocatedMemoryAfter - allocatedMemoryBefore) < 100);

    }
}
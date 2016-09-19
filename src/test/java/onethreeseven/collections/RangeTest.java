package onethreeseven.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Range}
 * @author Luke Bermingham
 */
public class RangeTest {

    @Test
    public void testIntersectingRanges(){
        Range a = new Range(1,3);
        Range b = new Range(2,5);
        Assert.assertTrue(!a.contains(b) && !a.isDisjointFrom(b));

        Range[] complements = b.minus(a);
        Assert.assertTrue(complements.length == 1);
        Range complement = complements[0];
        Assert.assertTrue(complement.lowerBound == 3);
        Assert.assertTrue(complement.upperBound == 5);
    }

    @Test
    public void testInRange(){
        Range r = new Range(0, 137);
        Assert.assertTrue(r.contains(50));
        Assert.assertTrue(!r.contains(200));
        Assert.assertTrue(r.contains(new Range(20, 70)));
    }

    @Test
    public void testIsAfter(){
        Range a = new Range(1,3);
        Range b = new Range(3,7);
        Assert.assertTrue(b.isAfter(a));
        Assert.assertTrue(!a.isAfter(b));
    }

    @Test
    public void testIsConnectible(){
        Range a = new Range(10, 20);
        Range b = new Range(5, 7);
        Range c = new Range(24, 29);

        Assert.assertTrue(b.isConnectible(a,5));
        Assert.assertTrue(a.isConnectible(c,4));
        Assert.assertTrue(c.isConnectibleAfter(a,4));
        Assert.assertTrue(!a.isConnectibleAfter(a,4));
    }


}

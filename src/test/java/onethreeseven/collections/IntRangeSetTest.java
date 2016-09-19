package onethreeseven.collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for {@link IntRangeSet}
 * @author Luke Bermingham
 */
public class IntRangeSetTest {

    private static final IntRangeSet r = new IntRangeSet();

    @BeforeClass
    public static void setup(){
        r.add(new Range(1,3));
        r.add(new Range(2,7));
        r.add(new Range(130, 137));
    }

    @Test
    public void testMerged() throws Exception {
        Range l = r.getLowest();
        Assert.assertTrue(l.lowerBound == 1 && l.upperBound == 7);
        Range h = r.getHighest();
        Assert.assertTrue(h.lowerBound == 130 && h.upperBound == 137);
    }

    @Test
    public void testGetCover() throws Exception {
        Assert.assertTrue(r.getCover() == 13);
    }

    @Test
    public void testIsEmpty() throws Exception {
        IntRangeSet e = new IntRangeSet();
        Assert.assertTrue(e.isEmpty());
        Assert.assertTrue(!r.isEmpty());
    }

    @Test
    public void copyTest() throws Exception {
        IntRangeSet c = new IntRangeSet(r);
        Range l = c.getLowest();
        Assert.assertTrue(l.lowerBound == 1 && l.upperBound == 7);
        Range h = c.getHighest();
        Assert.assertTrue(h.lowerBound == 130 && h.upperBound == 137);
    }

}
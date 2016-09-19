package onethreeseven.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * A range set of "closed" ranges.
 * I.e inclusive of both the starting and ending indices of the range.
 * @author Luke Bermingham
 */
public class IntRangeSet implements Iterable<Range> {

    private final ArrayList<Range> ranges;

    public IntRangeSet(IntRangeSet toCopy){
        this.ranges = new ArrayList<>(toCopy.ranges);
    }

    public IntRangeSet(){
        this.ranges = new ArrayList<>();
    }

    /**
     * Adds the new range. Handles a two-way merge (if necessary).
     * @param otherRange The range to add.
     */
    public void add(Range otherRange){
        //case: no ranges exist, so no need to look for merges
        if(ranges.isEmpty()){
            this.ranges.add(otherRange);
            return;
        }
        //case: could be merges so look for them
        Range toMerge = null;
        int i = 0;
        int size = ranges.size();
        for (; i < size; i++) {
            Range curRange = ranges.get(i);
            //case: they are touching or intersecting
            if(curRange.isTouching(otherRange) || !curRange.isDisjointFrom(otherRange)){
               toMerge = curRange;
               break;
            }
        }
        //case: nothing to merge, just add it
        if(toMerge == null){this.ranges.add(otherRange);}
        //case: something to merge
        else{
            //put our merge range in last index (to remove it faster)
            if(size > 1){
                Collections.swap(this.ranges, i, size-1);
            }
            this.ranges.remove(size-1);
            toMerge = Range.merge(toMerge, otherRange);
            this.ranges.add(toMerge);
        }
        Collections.sort(this.ranges);
    }

    /**
     * @return The total summation of all ranges in this set.
     */
    public int getCover(){
        return ranges.stream().mapToInt(Range::getRange).sum();
    }

    @Override
    public Iterator<Range> iterator() {
        return this.ranges.iterator();
    }

    public boolean isEmpty(){
        return this.ranges.isEmpty();
    }

    public Range getLowest(){
        return this.ranges.get(0);
    }

    public Range getHighest(){
        return this.ranges.get(this.ranges.size()-1);
    }

    @Override
    public String toString() {
        return this.ranges.stream().map(Range::toString).collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntRangeSet ranges1 = (IntRangeSet) o;
        return ranges.equals(ranges1.ranges);
    }

    @Override
    public int hashCode() {
        return ranges.hashCode();
    }
}

package onethreeseven.collections;


/**
 * Represents a range of numbers, can test if a number is within a given range.
 * Ranges go from lower bound (inclusive) to upper bound (inclusive). I.e. closed.
 * @author Luke Bermingham
 */
public class Range implements Comparable<Range> {

    public final int lowerBound;
    public final int upperBound;

    /**
     * Makes a range from lowerBound (inclusive) to upperBound (inclusive).
     * @param lowerBound the lower bound.
     * @param upperBound th upper bound.
     */
    public Range(int lowerBound, int upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * This range has a lower bound less than or equal to the other range
     * AND this range has an upper bound greater to or equal than the other range.
     * @param other the other range.
     * @return Whether this range fully contains the other range.
     */
    public boolean contains(Range other){
        return this.lowerBound <= other.lowerBound && this.upperBound >= other.upperBound;
    }

    public boolean contains(int value){
        //check if it is within range
        return value >= lowerBound && value <= upperBound;
    }

    public boolean isConnectible(Range other, int maxGap){
        return isConnectibleAfter(other, maxGap) || isConnectibleBefore(other, maxGap);
    }

    public boolean isConnectibleAfter(Range pre, int maxGap){
        return this.lowerBound - pre.upperBound <= maxGap && this.lowerBound >= pre.upperBound;
    }

    public boolean isConnectibleBefore(Range post, int maxGap){
        return post.lowerBound - this.upperBound <= maxGap && post.lowerBound >= this.upperBound;
    }

    public boolean isAfter(Range pre){
        return this.lowerBound >= pre.upperBound;
    }

    public boolean isDisjointFrom(Range b){
        return b.lowerBound > this.upperBound || b.upperBound < this.lowerBound;
    }

    public boolean isTouching(Range b){
        return this.upperBound == b.lowerBound || b.upperBound == this.lowerBound;
    }

    /**
     * @param b the other range.
     * @return Whatever is left of this range after "b" has been removed.
     *         Note: this could result in the range being split.
     */
    public Range[] minus(Range b){
        //case: not intersecting, simply return this range
        if(this.isDisjointFrom(b)){
            return new Range[]{this};
        }
        //case: this contains b
        //        this
        // |<-------------->|
        //          b
        //      |<----->|
        else if(this.contains(b)){
            return new Range[]{new Range(this.lowerBound, b.lowerBound), new Range(b.upperBound, this.upperBound)};
        }
        //case: b contains this
        //        this
        //      |<---->|
        //          b
        // |<-------------->|
        else if(b.contains(this)){
            return new Range[]{new Range(0,0)};
        }
        //case: intersects
        //      this
        //  |<--------->|
        //          b
        //      |<----------|>
        else if(this.lowerBound <= b.lowerBound){
            return new Range[]{new Range(this.lowerBound, b.lowerBound)};
        }
        //case: intersects
        //          this
        //      |<--------->|
        //        b
        //  |<--------->|
        else if(b.upperBound <= this.upperBound){
            return new Range[]{new Range(b.upperBound, this.upperBound)};
        }
        return null;
    }

    public Range copy(){
        return new Range(this.lowerBound, this.upperBound);
    }

    public static Range merge(Range a, Range b){
        return new Range( Math.min(a.lowerBound, b.lowerBound), Math.max(a.upperBound, b.upperBound) );
    }

    public int getRange(){
        return this.upperBound - this.lowerBound;
    }

    @Override
    public int compareTo(Range o) {
        return this.lowerBound - o.lowerBound;
    }

    @Override
    public String toString() {
        return "{" + lowerBound + " - " + upperBound + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        if (lowerBound != range.lowerBound) return false;
        return upperBound == range.upperBound;
    }

    @Override
    public int hashCode() {
        int result = lowerBound;
        result = 31 * result + upperBound;
        return result;
    }
}

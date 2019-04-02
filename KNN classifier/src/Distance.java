/**
 * @author kartikprabhu  03/01/19 11:51 AM
 */

public class Distance implements Comparable {

    private final TrainingSample sample;
    private final double distance;

    public Distance(TrainingSample caseSample, double distance) {
        this.sample = caseSample;
        this.distance = distance;
    }

    public TrainingSample getSample() {
        return sample;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Object o) {
        Distance d = (Distance) o;

        double d1 = this.distance;
        double d2 = d.distance;
        if (d1 < d2) {
            return -1;
        } else if (d1 > d2) {
            return 1;
        } else
            return 0;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "sample=" + sample +
                ", distance=" + distance +
                '}';
    }
}

/**
 * @author kartikprabhu  19/01/19 11:30 PM
 */

public class Point {

    private double xValue;
    private double yValue;

    public Point(double xValue, double yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public double getXValue() {
        return xValue;
    }

    public double getYValue() {
        return yValue;
    }

    public double getDistance(Point point){
        double distance = 0;
        distance = Math.pow(xValue - point.getXValue(), 2)+ Math.pow(yValue - point.getYValue(), 2);
        return Math.sqrt(distance);
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return point.getXValue() ==  xValue && point.getYValue() == yValue;
    }

    @Override
    public String toString() {
        return xValue+","+yValue;
    }
}

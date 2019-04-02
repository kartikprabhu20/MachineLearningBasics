import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kartikprabhu  19/01/19 11:41 PM
 */

public class Clusters {

    private List<Cluster> clusterList = new ArrayList<>();
    private double optimisationFoctor = 0 ;


    public Clusters(List<Point> centroids) {
        for (Point centroid: centroids){
            clusterList.add(new Cluster(centroid));
        }
    }

    public Clusters() {

    }

    public void addCluster(Cluster cluster) {
        clusterList.add(cluster);
    }

    public List<Cluster> getClusterList() {
        return clusterList;
    }

    public Double getOptimisationFactor(){
        for (Cluster cluster: clusterList){
                optimisationFoctor += cluster.getSumOfSquaredErrors();
        }
//       System.out.println("optimisationFoctor:"+ optimisationFoctor);

        return optimisationFoctor;
    }

    public void setOptimisationFoctor(double sum) {
         optimisationFoctor = sum;
    }

    @Override
    public String toString() {
        String centroids = "";
        for (Cluster cluster: clusterList) {
            centroids += cluster.getCentroid() + "\t" ;
        }
        centroids+= "\n";
        return centroids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clusters clusters = (Clusters) o;

        if (clusterList.size() !=  clusters.clusterList.size()) return false;

        for(int i=0; i < clusterList.size(); i ++){
            if(!clusterList.get(i).centroid.equals(clusters.clusterList.get(i).centroid))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterList);
    }

    public static class Cluster{
        private List<Point> cluster = new ArrayList<>();
        private Point centroid = new Point(0,0);

        public Cluster(List<Point> cluster) {
            this.cluster = cluster;
        }

        public Cluster(Point centroid) {
            this.centroid = centroid;
        }

        public Cluster() {

        }

        public void addTrainingPoint(Point point) {
            cluster.add(point);
            updateCentroid();
        }

        public Point getCentroid(){
            return centroid;
        }

        private void updateCentroid() {
            double x=0,y=0;
            for (Point point: cluster){
                x += point.getXValue();
                y += point.getYValue();
            }
            centroid = new Point(x/cluster.size(), y/cluster.size());
        }

        public Double getSumOfSquaredErrors(){
            double sum = 0;
            for (Point point: cluster){
                sum += Math.pow(centroid.getXValue() - point.getXValue(),2) + Math.pow(centroid.getYValue() - point.getYValue(),2);
            }
//          System.out.println("SUM:"+ sum);

            return sum;
        }
    }
}

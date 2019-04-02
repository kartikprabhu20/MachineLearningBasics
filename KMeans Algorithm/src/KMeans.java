import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kartikprabhu  19/01/19 11:49 PM
 */

public class KMeans {


    private final List<TrainingSample> trainingSamples;
    private double k = 0;

    public KMeans(List<TrainingSample> sampleList) {
        this.trainingSamples = sampleList;
    }

    public Clusters getNewClusters(Clusters clusters) {


        Map<String, Clusters.Cluster> hashMap = new HashMap<>();
        for (TrainingSample trainingSample: trainingSamples){
            Point trainingPoint = trainingSample.getTrainingPoint();

            List<ClusterDistancePair> clusterDistancePairList = new ArrayList<>();
            for (Clusters.Cluster cluster: clusters.getClusterList()){
                clusterDistancePairList.add(new ClusterDistancePair("c"+clusters.getClusterList().indexOf(cluster),cluster,trainingPoint.getDistance(cluster.getCentroid())));
            }
            clusterDistancePairList.sort(ClusterDistancePair::compareTo);

            String key = "c"+ clusters.getClusterList().indexOf(clusterDistancePairList.get(0).cluster);
            if(hashMap.keySet().contains(key)){
                Clusters.Cluster cluster =  hashMap.get(key);
                cluster.addTrainingPoint(trainingPoint);
                hashMap.remove(key);
                hashMap.put(key,cluster);
            }else {
                Clusters.Cluster cluster= new Clusters.Cluster();
                cluster.addTrainingPoint(trainingPoint);
                hashMap.put(key, cluster);
            }
        }

        Clusters newClusters = new Clusters();
        for (String key: hashMap.keySet()){
            newClusters.addCluster(hashMap.get(key));
        }
//        k += newClusters.getOptimisationFactor();
//        System.out.println("test:"+ k);

        return newClusters;
    }

        public class ClusterDistancePair implements Comparable {

        private final Clusters.Cluster cluster;
        private final double distance;
        private final String clusterName;

        public ClusterDistancePair(String clusterName, Clusters.Cluster cluster, double distance) {
            this.clusterName = clusterName;
            this.cluster = cluster;
            this.distance = distance;
        }

        @Override
        public int compareTo(Object o) {
            ClusterDistancePair clusterDistancePair = (ClusterDistancePair) o;

            double d1 = this.distance;
            double d2 = clusterDistancePair.distance;
            if (d1 < d2) {
                return -1;
            } else if (d1 > d2) {
                return 1;
            } else
                return 0;
        }

    }
}

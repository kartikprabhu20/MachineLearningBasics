import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kartikprabhu  03/01/19 11:56 AM
 */

public class IB2 {

    private final SampleManager sampleManager;
    private List<TrainingSample> caseBase = new ArrayList<>();

    public IB2(SampleManager sampleManager) {
        this.sampleManager = sampleManager;
    }

    public List<TrainingSample> getCaseBase(int kNN) {

        for (TrainingSample sample : sampleManager.getAllTrainingSamples()) {
            if (caseBase.isEmpty()) {
                caseBase.add(sample);
                continue;
            }

            List<Distance> distances = new ArrayList<>();
            for (TrainingSample caseSample: caseBase) {
                distances.add(new Distance(caseSample,distance(sample, caseSample)));
            }
            distances.sort(Distance::compareTo);

            if (!sample.getTrainingOutput().equalsIgnoreCase(predictClassification(getNearestNeighbors(distances, kNN))))// Misclassification
                caseBase.add(sample);
        }
        return caseBase;
    }

    private List<Distance> getNearestNeighbors(List<Distance> distances, int kNN) {
        List<Distance> nearestDistances = new ArrayList<>();
        for (Distance distance: distances ){
            kNN--;
            if (kNN < 0)
                break;

            nearestDistances.add(distance);
        }
        return nearestDistances;
    }

    private String predictClassification(List<Distance> distances) {
        String predictedClass = "";
        Map<String, Double> hashMap = new HashMap<>();

        Distance smallestDistance = distances.get(0);
        Distance largestDistance = distances.get(distances.size()-1);

        for (Distance distance : distances){
            String classification = distance.getSample().getTrainingOutput();
            double weight = getWeight(distance,smallestDistance,largestDistance) ;
//            System.out.println("Weight:"+ weight);
            hashMap.put(classification, hashMap.keySet().contains(classification)? hashMap.get(classification) + weight : weight);
        }


        double classWeight = 0;
        for (String key: hashMap.keySet()){

            if(hashMap.get(key) >= classWeight){
                classWeight = hashMap.get(key);
                predictedClass = key;
            }

//            System.out.println("HashMap: " + key + " weight: " + hashMap.get(key)  +" predictedClass: " + predictedClass + " classWeight:"+ classWeight);
        }
        return predictedClass;
    }

    private Double getWeight(Distance distance, Distance smallestDistance, Distance largestDistance) {
        if (smallestDistance.getDistance() == largestDistance.getDistance())
            return 1.0;

        return divide(largestDistance.getDistance()-distance.getDistance(), (largestDistance.getDistance()-smallestDistance.getDistance()));
    }

    private double divide(double numerator, double denominator) {

        BigDecimal num = BigDecimal.valueOf(numerator);
        BigDecimal den = BigDecimal.valueOf(denominator);
        BigDecimal response = num.divide(den, MathContext.DECIMAL128);

        return response.doubleValue();
    }

    public double distance(TrainingSample sample,TrainingSample caseSample) {
        int numberOfAttributes = sample.getTrainingInputs().size();

        double distance = 0;
        for (int i=0; i< numberOfAttributes; i++){
            distance += Math.pow(sample.getTrainingInputs().get(i) -  caseSample.getTrainingInputs().get(i), 2);
        }
//        System.out.println("Distance between "+ sample.toString() + " and "+ caseSample.toString() +" :" + Math.sqrt(distance));
        return Math.sqrt(distance);
    }


    public int getErrorCount(List<TrainingSample> samples, int kNN) {

        int count =0;
        for (TrainingSample sample: samples) {

            List<Distance> distances = new ArrayList<>();
            for (TrainingSample caseSample : caseBase) {
                distances.add(new Distance(caseSample, distance(sample, caseSample)));
            }
            distances.sort(Distance::compareTo);

            if (!sample.getTrainingOutput().equalsIgnoreCase(predictClassification(getNearestNeighbors(distances, kNN))))// Misclassification
                count++;
        }

        return count;
    }
}

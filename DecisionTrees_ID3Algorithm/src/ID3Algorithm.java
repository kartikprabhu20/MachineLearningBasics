import model.Feature;
import model.Node;
import model.Tree;

import java.util.*;

/**
 * @author kartikprabhu  24/11/18 11:30 AM
 */

public class ID3Algorithm {

    private SampleManager sampleManager;

    public ID3Algorithm(SampleManager sampleManager) {
        this.sampleManager =  sampleManager;
    }

    public String createDecisionTree() {

        Double decisionEntropy = calculateDecisionEntropy( new HashMap<>());
//        System.out.println("Tree entropy= "+ decisionEntropy);

        int level = 0;
        Node resultNode = new Tree(decisionEntropy);
        runID3Algorithm(decisionEntropy, level, resultNode, new HashMap<>());

//        System.out.println(resultNode.toString());

        return resultNode.toString();
    }

    private void runID3Algorithm(Double decisionEntropy, int level, Node node, Map<Integer, String> featureIndex_featureClass) {
        if (decisionEntropy == 0) {
//            System.out.println("\n Level:" + level +" Leaf Node reached");
            return;
        }
//        System.out.println("Level:" + level );
//        System.out.println("runID3Algorithm getFeatureListsample featureIndex_featureClass:"+ featureIndex_featureClass.toString());

        Map<Feature, Double> gainList =  new HashMap<>();
        for (Feature feature: sampleManager.getFeatureList(featureIndex_featureClass)){
//            System.out.println("\n FeatureName:"+feature.getFeatureName());

            double gain = decisionEntropy - calculateFeatureEntropy(feature);
            gainList.put(feature, gain);
        }

        Feature bestFeature = getBestFeature(gainList);

        for (String featureClass: bestFeature.getAllFeatureClasses()) {
//            System.out.println("Level:" + level + " Feature:"+ bestFeature.getFeatureName()+ " FeatureClass:"+ featureClass );

            Map<Integer, String> previous_featureIndex_featureClass =  new HashMap<>();
            previous_featureIndex_featureClass.putAll(featureIndex_featureClass);
            featureIndex_featureClass.put(bestFeature.getFeatureIndex(), featureClass);

//            System.out.println("ID3 getFeatureListsample:"+ featureIndex_featureClass.toString());
            decisionEntropy = calculateDecisionEntropy(featureIndex_featureClass);

            Node childNode = (decisionEntropy == 0) ?
                    new Node(decisionEntropy, bestFeature.getFeatureName(),featureClass, sampleManager.getTrainingOutputs(featureIndex_featureClass).get(0))
                    : new Node(decisionEntropy,bestFeature.getFeatureName(),featureClass);
            node.addChildNode(childNode);
            runID3Algorithm(decisionEntropy, level+1, childNode, featureIndex_featureClass);

//            System.out.println("Level:" + level + " runID3Algorithm complete");
//            System.out.println("//Level:" + level + " Feature:"+ bestFeature.getFeatureName()+ " FeatureClass:"+ featureClass + " FeatureOutput:"+ bestFeature.getFeatureOutputPairs().get(0).getFeatureOutput()+"\n\n\n");

            featureIndex_featureClass = previous_featureIndex_featureClass;
//            System.out.println("Node after completion:" + node);

        }
    }

    private double calculateDecisionEntropy(Map<Integer, String> featureIndex_featureClass) {

        double decisionEntropy = 0;
        List<String> sampleOutputs = sampleManager.getTrainingOutputs(featureIndex_featureClass);

//                System.out.println("DecisionEntropy:SampleOutputSize:" + sampleOutputs.size());

//        List<String> sampleOutputs = sampleManager.getTrainingOutputs(featureIndex_featureClass)
        for (String classification : sampleManager.getOutputClassifications()){
            double probability = Double.valueOf(Collections.frequency(sampleOutputs,classification))/Double.valueOf(sampleOutputs.size());
            decisionEntropy -= probability * logToBaseK(probability, sampleManager.getOutputClassifications().size());
        }

//        System.out.println("DecisionEntropy:" + decisionEntropy);
        return decisionEntropy;
    }

    private double calculateFeatureEntropy(Feature feature) {
        double probability = 0;
        double featureEntropy = 0;
        for (String featureClass : feature.getAllFeatureClasses()) {
//            System.out.println("FeatureClassName:"+featureClass);
            double frequencyOfClass = Double.valueOf(Collections.frequency(feature.getFeatureValues(), featureClass));
            double sampleSize = Double.valueOf(feature.getFeatureValues().size());
            probability = (frequencyOfClass/ sampleSize);
//                System.out.println("Probability:" + probability);

            List<Feature.FeatureOutputPair> featureOutputPairList = feature.getFeatureOutputPairs(featureClass);

            double subEntropy = 0;
            for (String outputClass: sampleManager.getOutputClassifications()) {
                List<Feature.FeatureOutputPair> featureOutputPairs = feature.getFeatureOutputPairs(featureOutputPairList, outputClass);
                double subProbability = Double.valueOf(featureOutputPairs.size())/ Double.valueOf(featureOutputPairList.size());

//                    System.out.println("OutputClass:" + outputClass);
//                    System.out.println("SubProbability:Numerator:" + Double.valueOf(featureOutputPairs.size()));
//                    System.out.println("SubProbability:Denominator:" + Double.valueOf(featureOutputPairList.size()));
//                    System.out.println("SubProbability:" + subProbability);

                subEntropy -= subProbability * logToBaseK(subProbability, sampleManager.getOutputClassifications().size());
//                    System.out.println("SubEntropy:"+ outputClass +":" + subEntropy);
            }

            featureEntropy += probability * subEntropy;

//                System.out.println("featureEntropy:" + featureEntropy +"\n");
        }

//        System.out.println("FeatureEntropy:" + featureEntropy);
        return featureEntropy;
    }

    private double logToBaseK(double number, double base){
        if (number == 0  || base == 0)
            return 0;

        return Math.log(number)/(Math.log(base));
    }

    private  Feature getBestFeature(Map<Feature, Double> gainList) {
        Feature bestFeature = null;
        double highestGain = 0;

        for (Feature feature: gainList.keySet()){
            if (highestGain < gainList.get(feature)) {
                highestGain =  gainList.get(feature);
                bestFeature = feature;
            }
        }

//      System.out.println("BestFeature:" + bestFeature.getFeatureName());
        return bestFeature;
    }

}

package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kartikprabhu  20/11/18 5:35 PM
 */

public class Feature {

    private String featureName;
    private int featureIndex = -1;
    private List<FeatureOutputPair> featureOutputPairs = new ArrayList<>();
    private List<String> featureValues =  new ArrayList<>();

    public Feature(String featureName, int featureIndex) {
        this.featureName = featureName;
        this.featureIndex = featureIndex;
    }

    public Set<String> getAllFeatureClasses() {

        Set<String> featureClasses =  new HashSet<>();  // unique feature values
        for (FeatureOutputPair featureOutputPair : featureOutputPairs){
            featureClasses.add(featureOutputPair.getFeatureValue());
        }
        return featureClasses;
    }

    public String getFeatureName() {
        return featureName;
    }

    public int getFeatureIndex() {
        return featureIndex;
    }

    public List<FeatureOutputPair> getFeatureOutputPairs() {
        return featureOutputPairs;
    }

    public List<FeatureOutputPair> getFeatureOutputPairs(String featureClass) {
        List<FeatureOutputPair> featureOutputPairList = new ArrayList<>();
        for (FeatureOutputPair featureOutputPair: featureOutputPairs){
            if (featureOutputPair.getFeatureValue().equalsIgnoreCase(featureClass))
                featureOutputPairList.add(featureOutputPair);
        }
        return featureOutputPairList;
    }

    public List<FeatureOutputPair> getFeatureOutputPairs(List<FeatureOutputPair> featureOutputPairList, String outputClass) {
        List<FeatureOutputPair> featureOutputPairs = new ArrayList<>();
        for (FeatureOutputPair featureOutputPair: featureOutputPairList){
            if (featureOutputPair.getFeatureOutput().equalsIgnoreCase(outputClass))
                featureOutputPairs.add(featureOutputPair);
        }
        return featureOutputPairs;
    }

    public List<String> getFeatureValues() {
        return featureValues;
    }

    public void addFeatureOutputs(String featureValue, String output) {
        featureValues.add(featureValue);
        featureOutputPairs.add(new FeatureOutputPair(featureValue, output));
    }


    public class FeatureOutputPair {

        private String featureValue;
        private String featureOutput;

        public FeatureOutputPair(String featureValue, String output) {
            this.featureValue = featureValue;
            this.featureOutput = output;
        }

        public String getFeatureValue() {
            return featureValue;
        }

        public String getFeatureOutput() {
            return featureOutput;
        }
    }
}

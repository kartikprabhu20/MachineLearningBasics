import model.Feature;
import model.TrainingSample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

/**
 * @author kartikprabhu  20/11/18 9:40 AM
 */

public class SampleManager {

    private final File trainingFile;
    private int attributeSize = 0;

    List<TrainingSample> trainingSamples = new ArrayList<>();

    public SampleManager(File file) {
        this.trainingFile = file;
        init(trainingFile);
    }

    private void init(File trainingFile) {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(trainingFile);

            do {
                String input = inputStream.next();
                trainingSamples.add(new TrainingSample(input));

                if (attributeSize == 0)
                    attributeSize = input.split(",").length - 1;

            }while (inputStream.hasNext());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Executor.LOGGER.log(Level.SEVERE, "FileNotFoundException while reading the input file");
        }finally {
            inputStream.close();
        }
    }

    public int getAttributeSize() {
        return attributeSize;
    }

    public List<TrainingSample> getAllTrainingSamples() {
        return trainingSamples;
    }

    public List<String> getTrainingOutputs(){
        List<String> sampleOutput = new ArrayList<>();
        for (TrainingSample sample : trainingSamples) {
            sampleOutput.add(sample.getTrainingOutput());
        }
        return sampleOutput;
    }

    public Set<String> getOutputClassifications(){
        Set<String> classifications =  new HashSet<>();
        for (String output: getTrainingOutputs()){
            classifications.add(output);
        }
        return classifications;
    }

    public List<Feature> getFeatureList(){
        List<Feature> featureList = new ArrayList<>();

        for (int i=0; i < trainingSamples.get(0).getTrainingInputs().size(); i++){
            Feature feature = new Feature("att"+i, i);
            for (TrainingSample sample : getAllTrainingSamples()){
                feature.addFeatureOutputs(sample.getTrainingInputs().get(i), sample.getTrainingOutput());
            }
            featureList.add(feature);
        }
        return featureList;
    }

    /**
     *
     * @param featureIndex_featureClass is map of featureIndex and its featureClass as filter
     * @return
     */
    public List<Feature> getFeatureList(Map<Integer, String> featureIndex_featureClass){

        if (featureIndex_featureClass.isEmpty())
            return getFeatureList();

        List<Feature> featureList = new ArrayList<>();
        for (int i=0; i < trainingSamples.get(0).getTrainingInputs().size(); i++){
            if (featureIndex_featureClass.keySet().contains(i)) // If Keyset contains the index then skip that feature
                continue;

            Feature feature = new Feature("att"+i, i);

            for (TrainingSample sample : getAllTrainingSamples()){

                boolean addInput = true;
                for (Integer featureIndex: featureIndex_featureClass.keySet()) {
                    addInput &= (sample.getTrainingInputs().get(featureIndex).equalsIgnoreCase(featureIndex_featureClass.get(featureIndex)));
                }

                if (addInput) {
//                    System.out.println("Feature:"+ i +" getFeatureListsample:"+ sample.toString());
                    feature.addFeatureOutputs(sample.getTrainingInputs().get(i), sample.getTrainingOutput());
                }
            }
            featureList.add(feature);
//            System.out.println("getFeatureList:FeatureName:"+feature.getFeatureName()+" featureValues:" + feature.getFeatureValues().size());
        }
        return featureList;
    }


    public List<String> getTrainingOutputs(Map<Integer, String> featureIndex_featureClass) {
        List<String> sampleOutput = new ArrayList<>();
        for (TrainingSample sample : trainingSamples) {
            boolean addOutput = true;
            for (Integer featureIndex: featureIndex_featureClass.keySet()) {
                addOutput &= (sample.getTrainingInputs().get(featureIndex).equalsIgnoreCase(featureIndex_featureClass.get(featureIndex)));
            }

            if (addOutput) {
                sampleOutput.add(sample.getTrainingOutput());
            }
        }
        return sampleOutput;
    }
}

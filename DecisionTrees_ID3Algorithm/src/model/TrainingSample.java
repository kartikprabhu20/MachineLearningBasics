package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kartikprabhu  20/11/18 9:42 AM
 */

public class TrainingSample {

    List<String> trainingValues = new ArrayList<>();

    public TrainingSample(String input) {
        this.trainingValues = Arrays.asList(input.split(","));
    }

    public List<String> getTrainingInputs(){
        List<String> trainingInputs = new ArrayList<>(trainingValues.size()-1);
        for (int i=0; i < trainingValues.size()-1; i++){
            trainingInputs.add(trainingValues.get(i));
        }
        return trainingInputs;
    }

    public String getTrainingOutput(){
        return trainingValues.get(trainingValues.size()-1); // Last value
    }

    @Override
    public String toString() {
        return "TrainingSample{" +
                "trainingValues=" + trainingValues +
                '}';
    }
}

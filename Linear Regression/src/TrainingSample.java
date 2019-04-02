import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kartikprabhu  25/10/18 12:00 AM
 */

public class TrainingSample {

    List <String> trainingValues = new ArrayList<>();

    public TrainingSample(String input) {
        this.trainingValues = Arrays.asList(input.split(","));
    }

    public List<Double> getTrainingInputs(){
        List<Double> trainingInputs = new ArrayList<>(trainingValues.size()-1);
        for (int i=0; i < trainingValues.size()-1; i++){
            trainingInputs.add(Double.valueOf(trainingValues.get(i)));
        }
        return trainingInputs;
    }

    public float getTrainingOutput(){
        return Float.parseFloat(trainingValues.get(trainingValues.size()-1)); // Last value
    }
}

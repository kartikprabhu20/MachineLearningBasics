import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kartikprabhu  25/12/18 5:34 PM
 */

public class TrainingSample {
    List<String> trainingValues = new ArrayList<>();

    public TrainingSample(String input) {
        this.trainingValues = Arrays.asList(input.split("\t"));
    }

    public List<Double> getTrainingInputs(){
        List<Double> trainingInputs = new ArrayList<>(trainingValues.size());
//        trainingInputs.add(1.0);// x0 = 1;
        for (int i=1; i < trainingValues.size(); i++){
            trainingInputs.add(Double.valueOf(trainingValues.get(i)));
        }
        return trainingInputs;
    }

    public String getTrainingOutput(){
        return trainingValues.get(0); // First value
    }
}

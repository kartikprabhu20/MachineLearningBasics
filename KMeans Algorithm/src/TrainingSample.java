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

    public Point getTrainingPoint(){
        return new Point(Double.valueOf(trainingValues.get(1)), Double.valueOf(trainingValues.get(2)));
    }

    public String getTrainingOutput(){
        return trainingValues.get(0); // First value
    }

    @Override
    public String toString() {
        return "TrainingSample{" +
                "trainingValues=" + trainingValues +
                '}';
    }
}

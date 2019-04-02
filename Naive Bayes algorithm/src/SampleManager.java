import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

/**
 * @author kartikprabhu  25/12/18 5:35 PM
 */

public class SampleManager {

    private final File trainingFile;
    private int weightSize = 0;

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
                String input = inputStream.nextLine();

                trainingSamples.add(new TrainingSample(input));

                if (weightSize == 0)
                    weightSize = input.split("\t").length;

            }while (inputStream.hasNext());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
    }

    public int getSampleSize() {
        return trainingSamples.size();
    }

    public List<TrainingSample> getAllTrainingSamples() {
        return trainingSamples;
    }

    public Set<String> getOutputClassifications(){
        Set<String> classifications =  new HashSet<>();
        for (TrainingSample sample: getAllTrainingSamples()){
            classifications.add(sample.getTrainingOutput());
        }
        return classifications;
    }

    public List<TrainingSample> getTrainingSamples(String classification) {
        List<TrainingSample> trainingSampleSet =  new ArrayList<>();
        for (TrainingSample sample: getAllTrainingSamples()){
            if(sample.getTrainingOutput().equalsIgnoreCase(classification))
                trainingSampleSet.add(sample);
        }
        return trainingSampleSet;
    }

    public int getClassificationCount(String classification) {
        int count = 0;
        for (TrainingSample sample: getAllTrainingSamples()){
            if(sample.getTrainingOutput().equalsIgnoreCase(classification))
                count++;
        }
        return count;
    }
}

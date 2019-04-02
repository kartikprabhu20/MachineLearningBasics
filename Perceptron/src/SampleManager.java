import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

/**
 * @author kartikprabhu  08/12/18 11:16 AM
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
            Executor.LOGGER.log(Level.SEVERE, "FileNotFoundException while reading the input file");
        }finally {
            inputStream.close();
        }
    }

    public int getWeightSize() {
        return weightSize;
    }

    public List<TrainingSample> getAllTrainingSamples() {
        return trainingSamples;
    }
}

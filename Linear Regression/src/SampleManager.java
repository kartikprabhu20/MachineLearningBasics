import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * @author kartikprabhu  26/10/18 10:30 AM
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
                String input = inputStream.next();
                trainingSamples.add(new TrainingSample(input));

                if (weightSize == 0)
                    weightSize = input.split(",").length;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author kartikprabhu  24/10/18 10:28 AM
 */

public class Executor {

    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        ///Users/kartikprabhu/Desktop/OVGU/1st sem/Machine learning/exercise/linreg/Random.csv
        System.out.println("Enter the absolute file path:");
        String fileName = args.length >0 ? args[0] : in.nextLine();
        File file= new File(fileName);

        if (!file.exists()) {
            System.out.println("Invalid path, file does not exist");
            return;
        }

        System.out.println("Enter the learning rate:");
        float learningRate = args.length > 0 ? Float.parseFloat(args[1]) : in.nextFloat();

        System.out.println("Enter the threshold value:");
        float threshold = args.length > 0 ? Float.parseFloat(args[2]) : in.nextFloat();


        SampleManager sampleManager = new SampleManager(file);
        List<TrainingSample> trainingSamples = sampleManager.getAllTrainingSamples();

        int numberOfWeights = sampleManager.getWeightSize();
        List<Double> oldWeights = new ArrayList<>(numberOfWeights);// With each iteration weights will change
        List<Double> gradients = new ArrayList<>(numberOfWeights);
        for (int i= 0 ; i< numberOfWeights; i++){
            oldWeights.add((double) 0.0);
            gradients.add((double) 0.0);
        }

        double sumOfErrorSquared = 0;
        double previousSumOfErrorSquared = 0;

        int iteration = 0;
        List<Double> newWeights = new ArrayList<>(numberOfWeights);// With each iteration weights will change

        do {
            StringBuilder finalResult = new StringBuilder();
            finalResult.append(iteration).append(",");
            previousSumOfErrorSquared = sumOfErrorSquared;
            sumOfErrorSquared = 0;

            for (TrainingSample sample : trainingSamples) {
                List<Double> trainingInputs = sample.getTrainingInputs();
                double trainingOutput = sample.getTrainingOutput();

                double error = trainingOutput - GradientHelper.getFunctionX(trainingInputs,oldWeights);
                gradients.set(0, gradients.get(0) + error);

                for (int i=1; i< numberOfWeights; i++){
                    gradients.set(i, Double.valueOf(gradients.get(i)) + (trainingInputs.get(i-1) * error));
                }
                sumOfErrorSquared += Math.pow(error,2);
            }

            newWeights.clear();
            newWeights.addAll(GradientHelper.getNewWeights(oldWeights, learningRate, gradients));

            for (int i= 0 ; i< numberOfWeights; i++){
                finalResult.append(oldWeights.get(i)).append(",");
                gradients.set(i, (double) 0.0);//Clear values for next iteration
            }

            finalResult.append(sumOfErrorSquared + "\n");
            iteration++;

            oldWeights.clear();
            oldWeights.addAll(newWeights);

            System.out.println(finalResult);
        }while(!(Math.abs(sumOfErrorSquared-previousSumOfErrorSquared) < threshold));
    }
}

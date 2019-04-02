import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author kartikprabhu  05/12/18 11:54 PM
 */

public class Executor {


    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        //        String fileName = "/Users/kartikprabhu/Desktop/OVGU/1st sem/Machine learning/exercise/perceptron/Example.tsv";

        String fileName = args[0];
        String outputfileName = args[1];

        int iterations = args.length > 2 ? Integer.parseInt(args[2]) : 100;
        double learningRate = args.length > 3 ? Integer.parseInt(args[3]) : 1;

        File file= new File(fileName);

        if (!file.exists()) {
            System.out.println("Invalid path, file does not exist");
            return;
        }

        String variant1 = getErrorCounts(iterations,learningRate,file, false);
        String variant2 = getErrorCounts(iterations,learningRate,file, true);
//        System.out.println(variant1 + "\n" + variant2);

        writeToFile(outputfileName, variant1 + "\n" + variant2);
    }

    private static void writeToFile(String outputfileName, String result) {
        FileOutputStream outputStream = null;
        try {
            File outputFile = new File(outputfileName);
            outputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] strToBytes = result.getBytes();
        try {
            outputStream.write(strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getErrorCounts(int iterations, double learningRate, File file, boolean isAnnealingRate) {

        SampleManager sampleManager = new SampleManager(file);
        List<TrainingSample> trainingSamples = sampleManager.getAllTrainingSamples();

        int numberOfWeights = sampleManager.getWeightSize();
        List<Double> oldWeights = new ArrayList<>(numberOfWeights);// With each iteration weights will change
        for (int i= 0 ; i< numberOfWeights; i++){
            oldWeights.add(0.0);
        }


        StringBuilder finalResult = new StringBuilder();

        for (int i=0; i<= iterations; i++){
            List<Double> errorFunctions = new ArrayList<>(numberOfWeights);
            int errorSampleCount = 0;

            for (int j=0; j< numberOfWeights;j++){
                errorFunctions.add((double)0.0);
            }

            for (TrainingSample sample : trainingSamples) {
                List<Double> trainingInputs = sample.getTrainingInputs();
                String trainingOutput = sample.getTrainingOutput();

                double netOutput = getNetOutput(trainingInputs, oldWeights);

                if (!trainingOutput.equalsIgnoreCase(netOutput > 0 ? "A":"B")){
                    errorSampleCount++;
                    for (int j=0; j< numberOfWeights; j++) {
                        double learningRateT = getLearningRate(isAnnealingRate,learningRate, i+1);
                        double error = ((trainingOutput.equalsIgnoreCase("A") ? 1.0 :0.0) - ( netOutput > 0 ? 1.0 : 0.0));
//                        System.out.println("error" + error);

                        errorFunctions.set(j, Double.valueOf(errorFunctions.get(j) +
                                Double.valueOf(learningRateT) *  error * Double.valueOf(trainingInputs.get(j))));
                    }
                }
            }

            finalResult.append(errorSampleCount + "\t");
//            System.out.println("ErrorCount:" + errorSampleCount);

            for (int j=0; j< numberOfWeights;j++){
                oldWeights.set(j, oldWeights.get(j) + errorFunctions.get(j));
            }
        }

//        System.out.println("FinalResult:" + finalResult);
        return finalResult.toString();
    }

    private static double getLearningRate(boolean isAnnealingRate, double learningRate, int t) {
        if (!isAnnealingRate)
            return learningRate;

        BigDecimal numerator = BigDecimal.valueOf(Double.valueOf(learningRate));
        BigDecimal denominator = BigDecimal.valueOf(Double.valueOf(t));
        BigDecimal response = numerator.divide(denominator, MathContext.DECIMAL128);

        return response.doubleValue();
    }

    private static double getNetOutput(List<Double> trainingInputs, List<Double> weights) {
        double sum = 0;

        for (int i = 0 ; i < trainingInputs.size() ; i++){
            double weight = weights.isEmpty() ? 0 : weights.get(i);
            sum += trainingInputs.get(i) * weight;
        }
        return sum;
    }
}

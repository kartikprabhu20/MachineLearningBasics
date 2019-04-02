import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * @author kartikprabhu  25/12/18 5:34 PM
 */

public class Executor {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ///Users/kartikprabhu/Desktop/OVGU/1st sem/Machine learning/exercise/decisiontree/car.csv
        if ( !(args.length >0))
            System.out.println("Enter the absolute file path:");
        String inputfileName = args.length >0 ? args[0] : in.nextLine();

        if ( !(args.length >0))
            System.out.println("Enter the output path:");
        String outputfileName = args.length >0 ? args[1] : in.nextLine();

        File file= new File(inputfileName);
        if (!file.exists()) {
            System.out.println("Invalid path, file does not exist");
            return;
        }

        SampleManager sampleManager = new SampleManager(file);

        Map<String, List<Double>> meanMap = new HashMap<>();
        Map<String, List<Double>> sdMap = new HashMap<>();

        for (String classification: sampleManager.getOutputClassifications()) {
            meanMap.put(classification, getMeanList(classification, sampleManager));
            sdMap.put(classification,getStandardDeviationList(classification, sampleManager, meanMap.get(classification)));
        }

        StringBuilder finalResult = new StringBuilder();
        for (String classification: sampleManager.getOutputClassifications()) {
            for (int i = 0; i < sampleManager.getAllTrainingSamples().get(0).getTrainingInputs().size(); i++) {
                finalResult.append(meanMap.get(classification).get(i) + " \t");
                finalResult.append(sdMap.get(classification).get(i) + " \t");
            }
            finalResult.append( divide(sampleManager.getClassificationCount(classification), sampleManager.getSampleSize()) + "\n");
        }

        int errorCount = 0;
        for (TrainingSample sample : sampleManager.getAllTrainingSamples()) {
            List<Double> trainingInputs = sample.getTrainingInputs();
            String trainingOutput = sample.getTrainingOutput();

            double predictedProbability = 0;
            String predictedClass = "";
            for (String classification: sampleManager.getOutputClassifications()) {
                double priorProbability = divide(sampleManager.getClassificationCount(classification), sampleManager.getSampleSize());
                List<Double> meanList  = meanMap.get(classification);
                List<Double> sdList = sdMap.get(classification);

                int numberOfAttributes = trainingInputs.size();
                double p = 1;
                for (int i = 0; i < numberOfAttributes; i++) {
                    p *= divide(Math.exp(-1 * divide(Math.pow(trainingInputs.get(i) - meanList.get(i), 2), 2 * sdList.get(i))), Math.sqrt(2 * Math.PI * sdList.get(i)));
                }

                p *= priorProbability;
//                System.out.println("predictedProbability: " + predictedProbability);
//                System.out.println("P(x/" + classification + "):" + p);

                if (p > predictedProbability){
                    predictedProbability = p;
                    predictedClass = classification;
                }
            }

            if (!trainingOutput.equalsIgnoreCase(predictedClass))
                errorCount++;
//            System.out.println("predictedClass:" + predictedClass + " trainingOutput:" + trainingOutput);
        }
//        System.out.println("ErrorCount:"+ errorCount);

        finalResult.append(errorCount);
//        System.out.println(finalResult);

        writeToFile(outputfileName, finalResult.toString());

    }

    private static List<Double> getMeanList(String classification, SampleManager sampleManager) {

        int numberOfAttributes = sampleManager.getAllTrainingSamples().get(0).getTrainingInputs().size();
        List<Double> meanList = new ArrayList<>(numberOfAttributes);

        for(int i=0; i < numberOfAttributes; i ++){
            meanList.add(i, 0.0);

            for (TrainingSample trainingSample: sampleManager.getTrainingSamples(classification)){
                meanList.set(i,meanList.get(i) + trainingSample.getTrainingInputs().get(i));
            }
            meanList.set(i, divide(meanList.get(i), sampleManager.getClassificationCount(classification)));
//            System.out.println("Mean:"+ meanList.get(i));
        }
        return meanList;
    }

    private static List<Double> getStandardDeviationList(String classification, SampleManager sampleManager, List<Double> meanList) {
        int numberOfAttributes = sampleManager.getAllTrainingSamples().get(0).getTrainingInputs().size();
        List<Double> sdList = new ArrayList<>(numberOfAttributes);

        for(int i=0; i < numberOfAttributes; i ++){
            sdList.add(i, 0.0);

            for (TrainingSample trainingSample: sampleManager.getTrainingSamples(classification)){
                sdList.set(i,sdList.get(i) + Math.pow(trainingSample.getTrainingInputs().get(i)- meanList.get(i), 2));
            }
            sdList.set(i, divide(sdList.get(i), sampleManager.getClassificationCount(classification) - 1));
//            System.out.println("sd:"+ sdList.get(i));
        }
        return sdList;
    }

    private static double divide(double numerator, double denominator) {

        BigDecimal num = BigDecimal.valueOf(numerator);
        BigDecimal den = BigDecimal.valueOf(denominator);
        BigDecimal response = num.divide(den, MathContext.DECIMAL128);

        return response.doubleValue();
    }

    private static void writeToFile(String outputfileName, String result) {
        FileOutputStream outputStream = null;
        try {
            File outputFile = new File(outputfileName);
            outputFile.getParentFile().mkdir();
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
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kartikprabhu  03/01/19 11:27 AM
 */

public class Executor {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        if (!(args.length > 0))
            System.out.println("Enter the absolute file path:");
        String inputfileName = args.length > 0 ? args[0] : in.nextLine();

        if (!(args.length > 0))
            System.out.println("Enter the output path:");
        String outputfileName = args.length > 0 ? args[1] : in.nextLine();

        File file = new File(inputfileName);
        if (!file.exists()) {
            System.out.println("Invalid path, file does not exist");
            return;
        }

        SampleManager sampleManager = new SampleManager(file);

        StringBuilder result = new StringBuilder();
        for (int i=1; i <= 5; i++) {
            IB2 ib2 = new IB2(sampleManager);
            List<TrainingSample> caseBase = ib2.getCaseBase(2*i);

//            System.out.println("CaseBase(k="+2*i+"): ");
//            for (TrainingSample sample : caseBase) {
//                System.out.println(sample.toString());
//            }

            List<TrainingSample> samples =  new ArrayList<>();
            samples.addAll(sampleManager.getAllTrainingSamples());
            samples.removeAll(caseBase);
//
            int error =  ib2.getErrorCount(samples, 2*i);
            result.append(error + "\t");
        }

        result.append("\n");
        for (TrainingSample sample: new IB2(sampleManager).getCaseBase(4)){
            result.append( sample.getTrainingOutput() + "\t") ;
            for (int i=0; i < sample.getTrainingInputs().size(); i++){
                result.append( sample.getTrainingInputs().get(i) + "\t" ) ;
            }
            result.append("\n");
        }

//        System.out.println(result);
        writeToFile(outputfileName,result.toString());
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

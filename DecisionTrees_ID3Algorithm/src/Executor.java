import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author kartikprabhu  20/11/18 9:23 AM
 */

public class Executor {

    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) {

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


        ID3Algorithm id3Algorithm = new ID3Algorithm(new SampleManager(file));
        String treeXml = id3Algorithm.createDecisionTree();


        FileOutputStream outputStream = null;
        try {
            File outputFile = new File(outputfileName);
            outputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] strToBytes = treeXml.getBytes();
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


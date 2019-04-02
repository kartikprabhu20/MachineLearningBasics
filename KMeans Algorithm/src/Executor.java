import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kartikprabhu  19/01/19 11:26 PM
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

        List<Clusters> listOfClusters = new ArrayList<>();
        List<Point> initialCentroids = new ArrayList<>();
        initialCentroids.add(new Point(0,5));
        initialCentroids.add(new Point(0,4));
        initialCentroids.add(new Point(0,3));
        listOfClusters.add(new Clusters(initialCentroids));

        KMeans kMeans = new KMeans(sampleManager.getAllTrainingSamples());

        boolean unchangedCetroidList = false;

        while(!unchangedCetroidList){
            Clusters oldCluster = listOfClusters.get(listOfClusters.size()-1);
            Clusters newClusters = kMeans.getNewClusters(oldCluster);

            unchangedCetroidList = oldCluster.equals(newClusters);
            if (!unchangedCetroidList)
                listOfClusters.add(newClusters);
        }

        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
         for (Clusters clusters: listOfClusters){
             result1.append(clusters);
             result2.append(clusters.getOptimisationFactor()+ "\n");
        }

        writeToFile(outputfileName+File.separator+ file.getName().replace(".tsv","-Proto"), result1.toString());
        writeToFile(outputfileName+File.separator+ file.getName().replace(".tsv", "-Progr.tsv"), result2.toString());

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
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

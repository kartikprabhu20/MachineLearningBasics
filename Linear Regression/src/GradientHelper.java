import java.util.*;

/**
 * @author kartikprabhu  24/10/18 10:28 AM
 */

public class GradientHelper {

    /**
     * w = w + n gradient
     *
     * @param oldWeight
     * @param learningRate
     * @param gradient
     * @return
     */
    public static double getNewWeight(double oldWeight, float learningRate, double gradient){
        return oldWeight + (learningRate * gradient);
    }

    public static List<Double> getNewWeights(List<Double> oldWeights, float learningRate, List<Double> gradient) {
        List<Double> weights = new ArrayList<>();

        for (int i=0; i< oldWeights.size(); i++){
            double weight = oldWeights.isEmpty() ? 0:oldWeights.get(i);
            weights.add(getNewWeight(weight,learningRate,gradient.get(i)));
        }
        return weights;
    }

    /**
     *
     * f(x) = w0 + w1 * x1 + w2 * x2 + ...
     *
     * @param inputValues inputValues from input file
     * @param weights weights for each value
     * @return
     */
    public static double getFunctionX(List<Double> inputValues, List<Double> weights){
        double sum = weights.get(0);//w0
        for (int i = 0 ; i < inputValues.size() ; i++){
            double weight = weights.isEmpty() ? 0 : weights.get(i+1);
            sum += inputValues.get(i) * weight;
        }
        return sum;
    }

}

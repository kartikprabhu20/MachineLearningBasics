package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kartikprabhu  20/11/18 2:53 PM
 */

public class Node {
    public double entropy = 0;
    private String featureName;
    private String featureValue;
    private String nodeValue; // if childNodeList != empty only then this is leaf and will have value
    public List<Node> childNodeList = new ArrayList<>();

    public Node(double entropy, String featureName, String featureValue, String nodeValue) {
        this.entropy = entropy;
        this.featureName = featureName;
        this.featureValue = featureValue;
        this.nodeValue = nodeValue;
    }

    public Node(double entropy) {
        this.entropy = entropy;
    }

    public Node(double entropy, String featureName) {
        this.entropy = entropy;
        this.featureName = featureName;
    }

    public Node(double entropy, String featureName, String featureValue) {
        this.entropy = entropy;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public List<Node> getChildNodeList() {
        return childNodeList;
    }

    public void addChildNode(Node childNode){
        childNodeList.add(childNode);
    }

    @Override
    public String toString() {
        return "<node" +
                " entropy=" + "\""+entropy+ "\" " +
                "feature=" + "\"" +featureName+ "\" " +
                "value:" + "\""+featureValue+ "\">"+
                ((entropy==0)? nodeValue : getChildNodes(childNodeList))+
                "</node>" ;
    }

    public String getChildNodes(List<Node> childNodeList) {
        String result = "";

        for (Node node: childNodeList){
            result += node.toString();
        }
        return result;
    }
}

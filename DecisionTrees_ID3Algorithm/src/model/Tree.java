package model;

/**
 * @author kartikprabhu  25/11/18 10:16 PM
 */

public class Tree  extends Node{

    public Tree(double entropy) {
        super(entropy);
    }

    @Override
    public String toString() {
        return "<tree" +
                " entropy=" + "\""+ entropy+ "\">"+
                getChildNodes(childNodeList) +
                "</tree>" ;
    }
}

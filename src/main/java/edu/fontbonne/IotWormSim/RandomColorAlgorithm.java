package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by vikramh on 6/20/17.
 */
public class RandomColorAlgorithm implements Algorithm {

    protected String attrName = "RandomColorAlgorithm.color";

    protected Graph graph;

    protected int chromaticNumber;

    public RandomColorAlgorithm(String attrName, int numColors) {
        this.attrName = attrName;
        this.chromaticNumber = numColors;
    }

    protected int getChromaticNumber()
    {
        return chromaticNumber;
    }

    public void init(Graph graph) {
        this.graph = graph;
    }

    public void compute() {
        String attributeName = "RandomColorAlgorithm.color";

        if (attrName != null)
            attributeName = attrName;

        Random r = new Random();

        for(Node node : graph.getEachNode()) {
            node.addAttribute(attributeName, r.nextInt(chromaticNumber));
        }
    }
}

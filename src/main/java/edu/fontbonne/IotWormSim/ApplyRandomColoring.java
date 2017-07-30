package edu.fontbonne.IotWormSim;

import org.graphstream.graph.Graph;

/**
 * Created by vikramh on 7/14/17.
 */
public class ApplyRandomColoring extends ApplyColoring
{
    public ApplyRandomColoring(Graph graph) {
        super(graph);
    }

    @Override
    public String toString()
    {
        return "Random";
    }

    @Override
    protected int setNodeColorAttribute() {
        RandomColorAlgorithm rc = new RandomColorAlgorithm("color", 4);
        rc.init(internalGraph);
        rc.compute();
        int chromaticNumber = rc.getChromaticNumber();

        return chromaticNumber;
    }
}

package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Graph;

/**
 * Created by vikramh on 7/14/17.
 */
public class ApplyWpColoring extends ApplyColoring {

    public ApplyWpColoring(Graph graph) {
        super(graph);
    }

    @Override
    public String toString()
    {
        return "Welsh-Powell";
    }

    @Override
    protected int setNodeColorAttribute()
    {
        WelshPowell wp = new WelshPowell("color");
        wp.init(internalGraph);
        wp.compute();
        int chromaticNumber = wp.getChromaticNumber();

        return chromaticNumber;
    }
}

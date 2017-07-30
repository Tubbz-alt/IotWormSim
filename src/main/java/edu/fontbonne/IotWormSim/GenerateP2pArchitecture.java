package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Graph;

/**
 * Created by vikramh on 7/16/17.
 */
public class GenerateP2pArchitecture implements GenerateArchitecture {
    RandomEuclideanGenerator gen = new RandomEuclideanGenerator();

    @Override
    public String toString()
    {
        return "P2P";
    }

    public void generateGraph(double threshold, Graph graph, int numNodes)
    {
        gen.setThreshold(threshold);
        gen.addSink(graph);
        gen.begin();

        for (int i = 1; i < numNodes; i++) {
            gen.nextEvents();
        }

        gen.end();
    }
}

package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Graph;

/**
 * Created by vikramh on 7/16/17.
 */
public class GenerateHubArchitecture implements GenerateArchitecture {
    protected RandomEuclideanHubGenerator gen = new RandomEuclideanHubGenerator();

    @Override
    public String toString()
    {
        return "Hub";
    }

    public void generateGraph(double threshold, Graph graph, int numNodes)
    {
        gen.setThreshold(threshold);
        gen.addSink(graph);
        gen.begin();

        for (int i = 1; i < numNodes / gen.getMean(); i++) {
            gen.nextEvents();
        }

        gen.end();
    }
}

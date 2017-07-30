package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Graph;

/**
 * Created by vikramh on 7/14/17.
 */
public interface GenerateArchitecture {

    void generateGraph(double threshold, Graph graph, int numNodes);
}

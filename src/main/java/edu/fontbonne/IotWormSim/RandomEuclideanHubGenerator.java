package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Node;

/**
 * Created by vikramh on 6/8/17.
 */

public class RandomEuclideanHubGenerator extends RandomEuclideanGenerator {

    private int stdDev = 2;
    private int mean = 5;
    //private int edgesBetweenHubsCount;


    public int getStdDev() {
        return stdDev;
    }

    public void setStdDev(int stdDev) {
        this.stdDev = stdDev;
    }

    public int getMean() {
        return mean;
    }

    public void setMean(int mean) {
        this.mean = mean;
    }

    public RandomEuclideanHubGenerator()
    {
        super();
        addNodeAttribute("isHub");
    }

    private double distance(String n1, String n2) {
        double d = 0.0;

        if (dimension == 2) {
            double x1 = internalGraph.getNode(n1).getNumber("x");
            double y1 = internalGraph.getNode(n1).getNumber("y");
            double x2 = internalGraph.getNode(n2).getNumber("x");
            double y2 = internalGraph.getNode(n2).getNumber("y");

            d = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
        } else if (dimension == 3) {
            double x1 = internalGraph.getNode(n1).getNumber("x");
            double y1 = internalGraph.getNode(n1).getNumber("y");
            double x2 = internalGraph.getNode(n2).getNumber("x");
            double y2 = internalGraph.getNode(n2).getNumber("y");
            double z1 = internalGraph.getNode(n1).getNumber("z");
            double z2 = internalGraph.getNode(n2).getNumber("z");

            d = Math.pow(z1 - z2, 2) + Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
        } else {
            for (int i = 0; i < dimension; i++) {
                double xi1 = internalGraph.getNode(n1).getNumber("x" + i);
                double xi2 = internalGraph.getNode(n2).getNumber("x" + i);

                d += Math.pow(xi1 - xi2, 2);
            }
        }

        return Math.sqrt(d);
    }

    @Override
    public boolean nextEvents() {
        String id = Integer.toString(nodeNames++);

        addNode(id);
        for (Node n : internalGraph.getEachNode()) {
            internalGraph.getNode(id).setAttribute("isHub", 1);
            if (!id.equals(n.getId()) && distance(id, n.getId()) < threshold && n.getNumber("isHub")==1) {
                addEdge(id + "-" + n.getId(), id, n.getId());
                //edgesBetweenHubsCount++;
                //System.out.println("Added edge between new hub " + id + " and old hub " + n.getId());
                //System.out.println(edgesBetweenHubsCount);
            }
        }

        int nodesAttachedToHub = (int)(random.nextGaussian()*stdDev+mean);

        for(int i = 0; i < nodesAttachedToHub; ++i) {
            String leafId = Integer.toString(nodeNames++);
            addNode(leafId);
            addEdge(leafId + "-" + id, leafId, id);
            internalGraph.getNode(leafId).setAttribute("isHub",0);
        }

        return true;
    }
}

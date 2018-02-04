package edu.fontbonne.IotWormSim;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by vikramh on 6/14/17.
 */
public class IotWormSimulation {

    private SingleGraph graph;
    private StringBuilder sb;
    private double threshold;
    private int iterations;
    private GenerateArchitecture generateArchitecture;
    private ApplyColoring coloringAlgorithm;
    private int chromaticNumber;
    private ConnectedComponents cc = new ConnectedComponents();
    private String rootDir;


    public enum Architecture
    {
        HUB,
        P2P
    }
    public enum Coloring
    {
        WELSH_POWELL,
        RANDOM
    }

    public IotWormSimulation(double threshold, int iterations, Architecture architecture,
                             Coloring coloring, String rootDir)
    {
        this.threshold = threshold;
        this.iterations = iterations;
        this.graph = new SingleGraph(architecture.toString());
        this.rootDir = rootDir;

        switch(architecture)
        {
            case HUB:
                generateArchitecture = new GenerateHubArchitecture();
                break;
            case P2P:
                generateArchitecture = new GenerateP2pArchitecture();
                break;
            default:
                throw new IllegalArgumentException("Didn't choose a valid architecture");
        }

        switch(coloring)
        {
            case WELSH_POWELL:
                coloringAlgorithm = new ApplyWpColoring(graph);
                break;
            case RANDOM:
                coloringAlgorithm = new ApplyRandomColoring(graph);
                break;
            default:
                throw new IllegalArgumentException("Didn't choose a valid coloring algorithm");
        }

        sb = new StringBuilder();

        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
    }

    public void run(boolean visuals)
    {
        generateArchitecture.generateGraph(threshold,graph,iterations);
        chromaticNumber = coloringAlgorithm.run();
        sb.append("Threshold,Component,ComponentNodes,PctCompromised\n");
        getComponents();
        printStats();

        if(visuals) {
            Viewer viewer = new Viewer(graph,
                    Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            Layout layout = Layouts.newLayoutAlgorithm();
            layout.setForce(1);
            viewer.enableAutoLayout(layout);
            ViewPanel view = viewer.addDefaultView(false);

            JFrame frame = new JFrame(generateArchitecture.toString() + "_" +
                    coloringAlgorithm.toString() + "_" + threshold);
            frame.setLayout(new BorderLayout());
            frame.add(view, BorderLayout.CENTER);
            frame.setSize(1600, 1200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addWindowListener((DefaultView) view);
            frame.addComponentListener((DefaultView) view);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception ex) {
                System.out.println("Error while sleeping");
            }
            makeScreenshot();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception ex) {
                System.out.println("Error while sleeping");
            }
            frame.dispose();
        }
        writeToFile(sb.toString());
    }

    private void printStats()
    {
        System.out.println("Graph Type: " + generateArchitecture.toString() + "  Color Type: " +
                coloringAlgorithm.toString() + "  Threshold: " + threshold + "\n");
        System.out.println("Connected Components: " + cc.getConnectedComponentsCount());
        System.out.println("Total number of nodes in graph: " + graph.getNodeCount());
        System.out.println("Total number of edges in graph: " + graph.getEdgeCount() + "\n");

        System.out.println("The chromatic number of this graph is: " + chromaticNumber + "\n");
    }

    private void getComponents() {

        cc.init(graph);

        cc.setCountAttribute("cluster");

        int clusterCounts[] = new int[cc.getConnectedComponentsCount()];
        int numNodesCompromised[] = new int[cc.getConnectedComponentsCount()];

        for(Node node : graph.getEachNode()) {
            int cluster = node.getAttribute("cluster");
            clusterCounts[cluster]++;
            if (node.getAttribute("compromised") == "true") {
                numNodesCompromised[cluster]++;
            }
        }

        for (int i = 0; i < clusterCounts.length; ++i) {
            sb.append(threshold + "," + (i + 1) + "," + clusterCounts[i] + "," +
                    (float)numNodesCompromised[i]/(float)clusterCounts[i] + "\n");
        }
    }

    private void makeScreenshot()
    {
        graph.addAttribute("ui.screenshot", rootDir + "images/" +
                generateArchitecture + "_" + coloringAlgorithm + "_" + threshold +  ".png");
    }


    private void writeToFile(String s)
    {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(rootDir + generateArchitecture + "_" +
                    coloringAlgorithm + "_" + threshold + ".csv");
            bw = new BufferedWriter(fw);
            bw.write(s);

            System.out.println("Done\n");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
}

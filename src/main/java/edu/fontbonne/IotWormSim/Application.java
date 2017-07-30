package edu.fontbonne.IotWormSim;

/**
 * Created by vikramh on 3/17/17.
 */



public class Application {
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        double[] thresholds = {.02, .03, .04, .045, .05};

        // Number of nodes to generate
        int iterations = 1000;

        // Show graphs and make screenshots
        boolean visuals = false;

        // Set this to where you want data saved
        String rootDir = System.getProperty("user.home") + "/Desktop/";

        run(IotWormSimulation.Architecture.HUB, IotWormSimulation.Coloring.RANDOM,
                thresholds,iterations,visuals,rootDir);
        run(IotWormSimulation.Architecture.P2P, IotWormSimulation.Coloring.WELSH_POWELL,
                thresholds,iterations,visuals,rootDir);
        run(IotWormSimulation.Architecture.HUB, IotWormSimulation.Coloring.WELSH_POWELL,
                thresholds,iterations,visuals,rootDir);
        run(IotWormSimulation.Architecture.P2P, IotWormSimulation.Coloring.RANDOM,
                thresholds,iterations,visuals,rootDir);
    }

    public static void run(IotWormSimulation.Architecture architecture,
                           IotWormSimulation.Coloring coloring, double[] thresholds,
                           int iterations, boolean visuals, String rootDir)
    {
        for (int i = 0; i < thresholds.length; ++i) {
            IotWormSimulation sim = new IotWormSimulation(thresholds[i],iterations,
                    architecture,coloring,rootDir);

            sim.run(visuals);
        }

    }




}

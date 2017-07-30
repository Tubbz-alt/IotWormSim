package edu.fontbonne.IotWormSim;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Created by vikramh on 7/14/17.
 */
public abstract class ApplyColoring {

    protected Graph internalGraph;


    protected String[] colors = new String[]{
            "green", "blue", "cyan", "orange", "purple", "brown", "magenta",
            "yellow", "aquamarine", "red"
    };

    protected String[] shapes = new String[]{
            "circle", "box", "diamond", "cross"
    };

    protected ApplyColoring(Graph graph) {
        internalGraph = graph;
    }

    protected abstract int setNodeColorAttribute();

    protected int run()
    {
        initializeStylesheetAttributes();
        int chromaticNumber = setNodeColorAttribute();
        setNodeColorStyleAttribute();
        setEdgeCompromisedAttribute();

        return chromaticNumber;
    }

    private void initializeStylesheetAttributes() {
        internalGraph.addAttribute("ui.stylesheet",
                "edge.compromised { fill-color:red; stroke-width:4px; stroke-mode:plain; }\n" +
                        "node { size: 10px; }\n");

        for (String color : colors) {
            internalGraph.addAttribute("ui.stylesheet", internalGraph.getAttribute("ui.stylesheet") +
                    "node." + color + " { fill-color: " + color + "; }\n");
        }
        for (String shape : shapes) {
            internalGraph.addAttribute("ui.stylesheet", internalGraph.getAttribute("ui.stylesheet") +
                    "node.shape_" + shape + " { shape: " + shape + "; }\n");
        }
    }

    protected void setNodeColorStyleAttribute()
    {
        for (Node node : internalGraph.getEachNode()) {
            int color = node.getAttribute("color");
            if (color < colors.length) {
                node.addAttribute("ui.class", colors[color]);
            }
            else
            {
                node.addAttribute("ui.class", "blue");
            }

            if (color < shapes.length) {
                node.addAttribute("ui.class", node.getAttribute("ui.class") + ", shape_" + shapes[color]);

            }
            else {
                node.addAttribute("ui.class", node.getAttribute("ui.class") + ", shape_cross");
            }
        }
    }

    protected void setEdgeCompromisedAttribute() {
        for(Edge edge:internalGraph.getEachEdge()) {
            if (edge.getSourceNode().getAttribute("color") ==
                    edge.getTargetNode().getAttribute("color"))
            {
                edge.addAttribute("ui.class", "compromised");
                edge.getSourceNode().addAttribute("compromised", "true");
                edge.getTargetNode().addAttribute("compromised", "true");

                //Make animation
//                graph.addAttribute("ui.screenshot", rootScreenshotDir +
//                    graphType + "_" + threshold + "_" + edge.getId() + ".png");
//                try {
//                    TimeUnit.MILLISECONDS.sleep(10);
//                }
//                catch(InterruptedException ex)
//                {
//
//                }
            }
        }
    }
}

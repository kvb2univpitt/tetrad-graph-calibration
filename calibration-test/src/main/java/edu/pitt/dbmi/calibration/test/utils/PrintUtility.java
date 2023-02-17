package edu.pitt.dbmi.calibration.test.utils;

import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.aa;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ac;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.at;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ca;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.cc;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ta;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.tt;
import edu.cmu.tetrad.graph.Node;
import edu.pitt.dbmi.calibration.test.EdgeProbability;
import java.io.PrintStream;
import java.util.Set;

/**
 *
 * Feb 16, 2023 7:43:43 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class PrintUtility {

    private PrintUtility() {
    }

    public static void displayText(Set<EdgeProbability> edgeProbabilities, PrintStream writer) {
        edgeProbabilities.forEach(e -> writer.println(displayText(e)));
    }

    public static void displayCSV(Set<EdgeProbability> edgeProbabilities, PrintStream writer) {
        edgeProbabilities.forEach(e -> writer.println(displayCSV(e)));
    }

    private static String displayCSV(EdgeProbability edgeProbability) {
        Node node1 = edgeProbability.getNode1();
        Node node2 = edgeProbability.getNode2();
        double probability = edgeProbability.getProbability();
        int observedValue = edgeProbability.getObservedValue();

        switch (edgeProbability.getEdgeType()) {
            case aa:
                return String.format("%s <-> %s,%f,%d", node1, node2, probability, observedValue);
            case ac:
                return String.format("%s <-o %s,%f,%d", node1, node2, probability, observedValue);
            case at:
                return String.format("%s <-- %s,%f,%d", node1, node2, probability, observedValue);
            case ca:
                return String.format("%s o-> %s,%f,%d", node1, node2, probability, observedValue);
            case cc:
                return String.format("%s o-o %s,%f,%d", node1, node2, probability, observedValue);
            case ta:
                return String.format("%s --> %s,%f,%d", node1, node2, probability, observedValue);
            case tt:
                return String.format("%s --- %s,%f,%d", node1, node2, probability, observedValue);
            default:
                return String.format("no edge,%f,%d", probability, observedValue);
        }
    }

    private static String displayText(EdgeProbability edgeProbability) {
        Node node1 = edgeProbability.getNode1();
        Node node2 = edgeProbability.getNode2();
        double probability = edgeProbability.getProbability();
        int observedValue = edgeProbability.getObservedValue();

        switch (edgeProbability.getEdgeType()) {
            case aa:
                return String.format("%s <-> %s: %f %d", node1, node2, probability, observedValue);
            case ac:
                return String.format("%s <-o %s: %f %d", node1, node2, probability, observedValue);
            case at:
                return String.format("%s <-- %s: %f %d", node1, node2, probability, observedValue);
            case ca:
                return String.format("%s o-> %s: %f %d", node1, node2, probability, observedValue);
            case cc:
                return String.format("%s o-o %s: %f %d", node1, node2, probability, observedValue);
            case ta:
                return String.format("%s --> %s: %f %d", node1, node2, probability, observedValue);
            case tt:
                return String.format("%s --- %s: %f %d", node1, node2, probability, observedValue);
            default:
                return String.format("no edge: %f %d", probability, observedValue);
        }
    }

}

package edu.pitt.dbmi.calibration.test.utils;

import edu.pitt.dbmi.calibration.test.EdgeValue;
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

    public static void displayCSV(Set<EdgeValue> edgeValues, PrintStream writer) {
        edgeValues.forEach(edgeValue -> writer.println(displayCSV(edgeValue)));
    }

    private static String displayCSV(EdgeValue edgeValue) {
        String node1 = edgeValue.getNode1();
        String node2 = edgeValue.getNode2();
        double probability = edgeValue.getProbability();
        int observedValue = edgeValue.getObservedValue();

        if (probability > 0) {
            switch (edgeValue.getEdgeType()) {
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
        } else {
            switch (edgeValue.getEdgeType()) {
                case aa:
                    return String.format("%s <-> %s,0,%d", node1, node2, observedValue);
                case ac:
                    return String.format("%s <-o %s,0,%d", node1, node2, observedValue);
                case at:
                    return String.format("%s <-- %s,0,%d", node1, node2, observedValue);
                case ca:
                    return String.format("%s o-> %s,0,%d", node1, node2, observedValue);
                case cc:
                    return String.format("%s o-o %s,0,%d", node1, node2, observedValue);
                case ta:
                    return String.format("%s --> %s,0,%d", node1, node2, observedValue);
                case tt:
                    return String.format("%s --- %s,0,%d", node1, node2, observedValue);
                default:
                    return String.format("no edge,0,%d", observedValue);
            }
        }
    }

}

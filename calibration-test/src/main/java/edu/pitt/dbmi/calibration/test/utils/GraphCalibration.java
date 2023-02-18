package edu.pitt.dbmi.calibration.test.utils;

import edu.cmu.tetrad.graph.Edge;
import edu.cmu.tetrad.graph.EdgeTypeProbability;
import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import edu.cmu.tetrad.graph.Endpoint;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.Node;
import edu.pitt.dbmi.calibration.test.EdgeValue;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * Feb 16, 2023 7:39:49 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class GraphCalibration {

    private GraphCalibration() {
    }

    public static void examineDirectEdge(Graph searchGraph, Graph trueGraph, PrintStream writer, boolean csv) {
        EdgeType edgeType = EdgeType.ta;
        Set<EdgeValue> edgeValues = createEdgeValues(trueGraph, edgeType, true);
        setObservedValues(trueGraph, edgeType, edgeValues);
        setPredictedValues(searchGraph, edgeType, edgeValues);

        if (csv) {
            PrintUtility.displayCSV(edgeValues, writer);
        } else {
            edgeValues.forEach(writer::println);
        }
    }

    private static void setPredictedValues(Graph searchGraph, EdgeType edgeType, Set<EdgeValue> edgeValues) {
        for (EdgeValue edgeValue : edgeValues) {
            Node node1 = searchGraph.getNode(edgeValue.getNode1());
            Node node2 = searchGraph.getNode(edgeValue.getNode2());
            Edge edge = searchGraph.getEdge(node1, node2);
            if (edge != null) {
                List<EdgeTypeProbability> edgeTypeProbs = edge.getEdgeTypeProbabilities();
                if (edgeTypeProbs != null) {
                    for (EdgeTypeProbability edgeTypeProb : edgeTypeProbs) {
                        if (edgeType == edgeTypeProb.getEdgeType()) {
                            edgeValue.setProbability(edgeTypeProb.getProbability());
                        }
                    }
                }
            }
        }
    }

    private static void setObservedValues(Graph trueGraph, EdgeType edgeType, Set<EdgeValue> edgeValues) {
        for (EdgeValue edgeValue : edgeValues) {
            Node node1 = trueGraph.getNode(edgeValue.getNode1());
            Node node2 = trueGraph.getNode(edgeValue.getNode2());

            Edge edge = trueGraph.getEdge(node1, node2);
            if (edge != null) {
                Endpoint endpoint1 = edge.getProximalEndpoint(node1);
                Endpoint endpoint2 = edge.getProximalEndpoint(node2);
                if (edgeType == getEdgeType(endpoint1, endpoint2)) {
                    edgeValue.setObservedValue(1);
                }
            }
        }
    }

    private static EdgeType getEdgeType(Endpoint endpoint1, Endpoint endpoint2) {
        if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.ARROW) {
            return EdgeType.aa;
        } else if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.CIRCLE) {
            return EdgeType.ac;
        } else if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.TAIL) {
            return EdgeType.at;
        } else if (endpoint1 == Endpoint.CIRCLE && endpoint2 == Endpoint.ARROW) {
            return EdgeType.ca;
        } else if (endpoint1 == Endpoint.CIRCLE && endpoint2 == Endpoint.CIRCLE) {
            return EdgeType.cc;
        } else if (endpoint1 == Endpoint.TAIL && endpoint2 == Endpoint.ARROW) {
            return EdgeType.ta;
        } else if (endpoint1 == Endpoint.TAIL && endpoint2 == Endpoint.TAIL) {
            return EdgeType.tt;
        } else {
            return EdgeType.nil;
        }
    }

    private static Set<EdgeValue> createEdgeValues(Graph graph, EdgeType edgeType, boolean isAsymmetric) {
        Set<EdgeValue> edgeValues = new TreeSet<>();

        String[] nodeNames = graph.getNodeNames().stream().toArray(String[]::new);
        for (int i = 0; i < nodeNames.length - 1; i++) {
            for (int j = i + 1; j < nodeNames.length; j++) {
                edgeValues.add(new EdgeValue(nodeNames[i], nodeNames[j], edgeType));
                if (isAsymmetric) {
                    edgeValues.add(new EdgeValue(nodeNames[j], nodeNames[i], edgeType));
                }
            }
        }

        return edgeValues;
    }

}

package edu.pitt.dbmi.calibration.test.utils;

import edu.pitt.dbmi.calibration.test.utils.PrintUtility;
import edu.cmu.tetrad.graph.Edge;
import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.aa;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ac;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.at;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ca;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.cc;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.ta;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.tt;
import edu.cmu.tetrad.graph.Endpoint;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.Node;
import edu.pitt.dbmi.calibration.test.EdgeProbability;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Feb 16, 2023 7:39:49 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class GraphCalibration {

    private GraphCalibration() {
    }

    public static void examineGraphs(Graph searchGraph, Graph trueGraph, EdgeType edgeType, PrintStream writer, boolean csv) {
        Set<EdgeProbability> edgeProbabilities = getSearchGraphEdgeProbabilities(searchGraph, edgeType);
        setTrueGraphEdgeProbabilities(trueGraph, edgeProbabilities);

        // print out
        if (csv) {
            PrintUtility.displayCSV(edgeProbabilities, writer);
        } else {
            PrintUtility.displayText(edgeProbabilities, writer);
        }
    }

    private static void setTrueGraphEdgeProbabilities(Graph trueGraph, Set<EdgeProbability> edgeProbabilities) {
        edgeProbabilities.forEach(edgeProb -> {
            Node node1 = edgeProb.getNode1();
            Node node2 = edgeProb.getNode2();
            Edge edge = trueGraph.getEdge(trueGraph.getNode(node1.getName()), trueGraph.getNode(node2.getName()));
            if (edge == null) {
                edge = trueGraph.getEdge(trueGraph.getNode(node2.getName()), trueGraph.getNode(node1.getName()));
            }

            if (edge == null) {
                edgeProb.setObservedValue(0);
            } else {
                Endpoint endpoint1 = edge.getEndpoint1();
                Endpoint endpoint2 = edge.getEndpoint2();
                switch (edgeProb.getEdgeType()) {
                    case aa:
                        if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.ARROW) {
                            edgeProb.setObservedValue(1);
                        }
                    case ac:
                        if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.CIRCLE) {
                            edgeProb.setObservedValue(1);
                        }
                    case at:
                        if (endpoint1 == Endpoint.ARROW && endpoint2 == Endpoint.TAIL) {
                            edgeProb.setObservedValue(1);
                        }
                    case ca:
                        if (endpoint1 == Endpoint.CIRCLE && endpoint2 == Endpoint.ARROW) {
                            edgeProb.setObservedValue(1);
                        }
                    case cc:
                        if (endpoint1 == Endpoint.CIRCLE && endpoint2 == Endpoint.CIRCLE) {
                            edgeProb.setObservedValue(1);
                        }
                    case ta:
                        if (endpoint1 == Endpoint.TAIL && endpoint2 == Endpoint.ARROW) {
                            edgeProb.setObservedValue(1);
                        }
                    case tt:
                        if (endpoint1 == Endpoint.TAIL && endpoint2 == Endpoint.TAIL) {
                            edgeProb.setObservedValue(1);
                        }
                    default:
                }
            }
        });
    }

    private static Set<EdgeProbability> getSearchGraphEdgeProbabilities(Graph graph, EdgeType edgeType) {
        Set<EdgeProbability> edges = new HashSet<>();

        graph.getEdges().forEach(edge -> {
            Node node1 = edge.getNode1();
            Node node2 = edge.getNode2();
            edge.getEdgeTypeProbabilities().forEach(edgeProb -> {
                EdgeType edgeProbEdgeType = edgeProb.getEdgeType();
                if ((edgeType == EdgeType.aa) && (edgeProbEdgeType == EdgeType.aa)) {
                    edges.add(new EdgeProbability(node1, node2, EdgeType.aa, edgeProb.getProbability()));
                } else if ((edgeType == EdgeType.ac || edgeType == EdgeType.ca) && (edgeProbEdgeType == EdgeType.ac || edgeProbEdgeType == EdgeType.ca)) {
                    EdgeProbability probEdge = (edgeProbEdgeType == EdgeType.ca)
                            ? new EdgeProbability(node1, node2, EdgeType.ca, edgeProb.getProbability())
                            : new EdgeProbability(node2, node1, EdgeType.ca, edgeProb.getProbability());
                    edges.add(probEdge);
                } else if ((edgeType == EdgeType.ta || edgeType == EdgeType.at) && (edgeProbEdgeType == EdgeType.ta || edgeProbEdgeType == EdgeType.at)) {
                    EdgeProbability probEdge = (edgeProbEdgeType == EdgeType.ta)
                            ? new EdgeProbability(node1, node2, EdgeType.ta, edgeProb.getProbability())
                            : new EdgeProbability(node2, node1, EdgeType.ta, edgeProb.getProbability());
                    edges.add(probEdge);
                } else if ((edgeType == EdgeType.cc) && (edgeProbEdgeType == EdgeType.cc)) {
                    edges.add(new EdgeProbability(node1, node2, EdgeType.cc, edgeProb.getProbability()));
                } else if ((edgeType == EdgeType.nil) && (edgeProbEdgeType == EdgeType.nil)) {
                    edges.add(new EdgeProbability(node1, node2, EdgeType.nil, edgeProb.getProbability()));
                } else if ((edgeType == EdgeType.tt) && (edgeProbEdgeType == EdgeType.tt)) {
                    edges.add(new EdgeProbability(node1, node2, EdgeType.tt, edgeProb.getProbability()));
                }
            });
        });

        return edges;
    }

}

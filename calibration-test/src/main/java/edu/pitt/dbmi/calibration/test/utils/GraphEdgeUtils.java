package edu.pitt.dbmi.calibration.test.utils;

import edu.cmu.tetrad.graph.Edge;
import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import static edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType.tt;
import edu.cmu.tetrad.graph.Edges;
import edu.cmu.tetrad.graph.Endpoint;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.Node;

/**
 *
 * Feb 21, 2023 2:07:02 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class GraphEdgeUtils {

    private GraphEdgeUtils() {
    }

    public static EdgeType getEdgeType(Edge edge) {
        Endpoint endpoint1 = edge.getEndpoint1();
        Endpoint endpoint2 = edge.getEndpoint2();

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

    public static boolean hasEdge(Graph graph, Node node1, Node node2, EdgeType edgeType) {
        switch (edgeType) {
            case aa:
                return hasBidirectedEdge(graph, node1, node2);
            case ac:
                return hasPartiallyOrientedEdge(graph, node1, node2);
            case at:
                return hasDirectEdge(graph, node2, node1);
            case ca:
                return hasPartiallyOrientedEdge(graph, node2, node1);
            case cc:
                return hasNondirectedEdge(graph, node1, node2);
            case ta:
                return hasDirectEdge(graph, node1, node2);
            case tt:
                return hasUndirectedEdge(graph, node1, node2);
            default:
                return false;
        }
    }

    /**
     * Determine node1 --- node2.
     *
     * @param graph
     * @param node1
     * @param node2
     * @return
     */
    public static boolean hasUndirectedEdge(Graph graph, Node node1, Node node2) {
        Edge edge = graph.getEdge(node1, node2);

        return edge != null
                && Edges.isUndirectedEdge(edge);
    }

    /**
     * Determine node1 o-o node2.
     *
     * @param graph
     * @param node1
     * @param node2
     * @return
     */
    public static boolean hasNondirectedEdge(Graph graph, Node node1, Node node2) {
        Edge edge = graph.getEdge(node1, node2);

        return edge != null
                && Edges.isNondirectedEdge(edge);
    }

    /**
     * Determine node1 --> node2.
     *
     * @param graph
     * @param node1
     * @param node2
     * @return true if there's a direct edge from node1 to node2 (node1 -->
     * node2)
     */
    public static boolean hasDirectEdge(Graph graph, Node node1, Node node2) {
        Edge edge = graph.getEdge(node1, node2);

        return edge != null
                && Edges.isDirectedEdge(edge)
                && (edge.getProximalEndpoint(node2) == Endpoint.ARROW);
    }

    /**
     * Determine node1 o-> node2
     *
     * @param graph
     * @param node1
     * @param node2
     * @return
     */
    public static boolean hasPartiallyOrientedEdge(Graph graph, Node node1, Node node2) {
        Edge edge = graph.getEdge(node1, node2);

        return edge != null
                && Edges.isPartiallyOrientedEdge(edge)
                && (edge.getProximalEndpoint(node2) == Endpoint.ARROW);
    }

    /**
     * Determine node1 <-> node2
     *
     * @param graph
     * @param node1
     * @param node2
     * @return
     */
    public static boolean hasBidirectedEdge(Graph graph, Node node1, Node node2) {
        Edge edge = graph.getEdge(node1, node2);

        return edge != null
                && Edges.isBidirectedEdge(edge);
    }

}

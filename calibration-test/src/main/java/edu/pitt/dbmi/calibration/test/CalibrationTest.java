package edu.pitt.dbmi.calibration.test;

import edu.cmu.tetrad.algcomparison.algorithm.oracle.pag.GFCI;
import edu.cmu.tetrad.algcomparison.independence.GSquare;
import edu.cmu.tetrad.algcomparison.score.BdeuScore;
import edu.cmu.tetrad.data.DataModel;
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
import edu.cmu.tetrad.graph.GraphPersistence;
import edu.cmu.tetrad.graph.Node;
import edu.cmu.tetrad.util.DataConvertUtils;
import edu.cmu.tetrad.util.Parameters;
import edu.pitt.dbmi.data.reader.Delimiter;
import edu.pitt.dbmi.data.reader.tabular.VerticalDiscreteTabularDatasetFileReader;
import edu.pitt.dbmi.data.reader.tabular.VerticalDiscreteTabularDatasetReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kvb2
 */
public class CalibrationTest {

    private static void runCalibrationTest(DataModel dataModel, Graph trueGraph) {
        Graph searchGraph = runGfic(dataModel, AlgoRunParameters.getParametersForGfciRun());
        System.out.println("--------------------------------------------------------------------------------");
        examineGraphs(searchGraph, trueGraph);
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void examineGraphs(Graph searchGraph, Graph trueGraph) {
        Set<EdgeProbability> edgeProbabilities = getSearchGraphEdgeProbabilities(searchGraph, EdgeType.ta);
        setTrueGraphEdgeProbabilities(trueGraph, edgeProbabilities);
        edgeProbabilities.forEach(e -> System.out.println(e.printCsv()));
        System.out.println("--------------------------------------------------------------------------------");
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

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("Calibration Test");
        System.out.println("================================================================================");
        Path dataFile = Paths.get(args[0]);
        Path graphFile = Paths.get(args[1]);
        try {
            DataModel dataModel = readInDataModel(dataFile, Delimiter.TAB);
            Graph trueGraph = loadGraph(graphFile);
            runCalibrationTest(dataModel, trueGraph);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        System.out.println("================================================================================");
    }

    private static Graph runGfic(DataModel dataModel, Parameters parameters) {
        return (new GFCI(new GSquare(), new BdeuScore())).search(dataModel, parameters);
    }

    private static Graph loadGraph(Path graphFile) {
        return GraphPersistence.loadGraphTxt(graphFile.toFile());
    }

    private static DataModel readInDataModel(Path dataFile, Delimiter delimiter) throws IOException {
        VerticalDiscreteTabularDatasetReader dataReader = new VerticalDiscreteTabularDatasetFileReader(dataFile, delimiter);

        return DataConvertUtils.toDataModel(dataReader.readInData());
    }

    private static class EdgeProbability {

        private final Node node1;
        private final Node node2;
        private final EdgeType edgeType;
        private double probability;
        private int observedValue;

        public EdgeProbability(Node node1, Node node2, EdgeType edgeType) {
            this.node1 = node1;
            this.node2 = node2;
            this.edgeType = edgeType;
        }

        public EdgeProbability(Node node1, Node node2, EdgeType edgeType, double probability) {
            this.node1 = node1;
            this.node2 = node2;
            this.edgeType = edgeType;
            this.probability = probability;
        }

        public String printCsv() {
            switch (edgeType) {
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
                    return String.format("no edge,%f,%d", node1, node2, probability, observedValue);
            }
        }

        @Override
        public String toString() {
            switch (edgeType) {
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
                    return String.format("no edge: %f %d", node1, node2, probability, observedValue);
            }
        }

        public Node getNode1() {
            return node1;
        }

        public Node getNode2() {
            return node2;
        }

        public EdgeType getEdgeType() {
            return edgeType;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

        public int getObservedValue() {
            return observedValue;
        }

        public void setObservedValue(int observedValue) {
            this.observedValue = observedValue;
        }

    }

}

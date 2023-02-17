package edu.pitt.dbmi.calibration.test;

import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import edu.cmu.tetrad.graph.Node;

/**
 *
 * Feb 16, 2023 7:41:59 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class EdgeProbability {

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

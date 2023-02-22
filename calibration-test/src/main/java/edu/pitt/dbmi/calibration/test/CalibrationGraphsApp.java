package edu.pitt.dbmi.calibration.test;

import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import edu.cmu.tetrad.graph.Graph;
import edu.pitt.dbmi.calibration.test.utils.GraphCalibration;
import edu.pitt.dbmi.calibration.test.utils.ResourceLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * Feb 16, 2023 8:04:02 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class CalibrationGraphsApp {

    private static EdgeType[] edgeTypes = {
        EdgeType.aa, // bidirected
        EdgeType.ac, // partially uncertain
        EdgeType.cc, // uncertain
        EdgeType.ta, // directed
        EdgeType.tt, // undirected
        EdgeType.nil // no edge
    };

    private static void runCalibrationTestEdges(Graph trueGraph, Graph searchGraph, EdgeType edgeType) {
        boolean csv = true;
        switch (edgeType) {
            case ta:
            case at:
                GraphCalibration.examineDirectEdge(searchGraph, trueGraph, System.out, csv);
            default:
        }
    }

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("Calibration Graphs Test");
        System.out.println("================================================================================");
        Path trueGraphFile = Paths.get(args[0]);
        Path searchGraphFile = Paths.get(args[1]);
        try {
            Graph trueGraph = ResourceLoader.loadGraph(trueGraphFile);
            Graph searchGraph = ResourceLoader.loadGraph(searchGraphFile);
            runCalibrationTestEdges(trueGraph, searchGraph, EdgeType.ta);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        System.out.println("================================================================================");
    }

}

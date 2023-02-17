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
        EdgeType.aa,
        EdgeType.ac,
        EdgeType.cc,
        EdgeType.ta,
        EdgeType.tt
    };

    private static void runCalibrationTest(Graph trueGraph, Graph searchGraph) {
        for (EdgeType edgeType : edgeTypes) {
            GraphCalibration.examineGraphs(
                    searchGraph,
                    trueGraph,
                    edgeType,
                    System.out,
                    false);
            System.out.println("--------------------------------------------------------------------------------");
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
            runCalibrationTest(trueGraph, searchGraph);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        System.out.println("================================================================================");
    }

}

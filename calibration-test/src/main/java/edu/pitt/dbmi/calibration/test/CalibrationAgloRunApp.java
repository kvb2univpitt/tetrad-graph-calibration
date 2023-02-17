package edu.pitt.dbmi.calibration.test;

import edu.cmu.tetrad.algcomparison.algorithm.oracle.pag.GFCI;
import edu.cmu.tetrad.algcomparison.independence.GSquare;
import edu.cmu.tetrad.algcomparison.score.BdeuScore;
import edu.cmu.tetrad.data.DataModel;
import edu.cmu.tetrad.graph.EdgeTypeProbability.EdgeType;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.util.Parameters;
import edu.pitt.dbmi.calibration.test.utils.AlgoRunParameters;
import edu.pitt.dbmi.calibration.test.utils.GraphCalibration;
import edu.pitt.dbmi.calibration.test.utils.ResourceLoader;
import edu.pitt.dbmi.data.reader.Delimiter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author kvb2
 */
public class CalibrationAgloRunApp {

    private static void runCalibrationTest(DataModel dataModel, Graph trueGraph) {
        Graph searchGraph = runGfic(dataModel, AlgoRunParameters.getParametersForGfciRun());
        System.out.println("--------------------------------------------------------------------------------");
        GraphCalibration.examineGraphs(
                searchGraph,
                trueGraph,
                EdgeType.at,
                System.out,
                true);
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("Calibration Algorim Run Test");
        System.out.println("================================================================================");
        Path dataFile = Paths.get(args[0]);
        Path graphFile = Paths.get(args[1]);
        try {
            DataModel dataModel = ResourceLoader.readInDataModel(dataFile, Delimiter.TAB);
            Graph trueGraph = ResourceLoader.loadGraph(graphFile);
            runCalibrationTest(dataModel, trueGraph);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        System.out.println("================================================================================");
    }

    private static Graph runGfic(DataModel dataModel, Parameters parameters) {
        return (new GFCI(new GSquare(), new BdeuScore())).search(dataModel, parameters);
    }

}

package edu.pitt.dbmi.calibration.test;

import org.junit.jupiter.api.Test;

/**
 *
 * Feb 16, 2023 8:05:09 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class CalibrationGraphsAppTest {

    /**
     * Test of main method, of class CalibrationGraphsApp.
     */
    @Test
    public void testMain() {
        String trueGraph = CalibrationAgloRunAppTest.class.getResource("/data/graph/true_graph.txt").getFile();
        String searchGraph = CalibrationAgloRunAppTest.class.getResource("/data/graph/pag_sampling/pag_sampling_100_graph.txt").getFile();
        String[] args = {
            trueGraph,
            searchGraph
        };
        CalibrationGraphsApp.main(args);
    }

}

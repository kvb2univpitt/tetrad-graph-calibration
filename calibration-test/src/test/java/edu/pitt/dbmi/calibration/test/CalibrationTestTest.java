package edu.pitt.dbmi.calibration.test;

import org.junit.jupiter.api.Test;

/**
 *
 * Feb 14, 2023 2:50:51 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class CalibrationTestTest {

    /**
     * Test of main method, of class CalibrationTest.
     */
    @Test
    public void testMain() {
        String data = CalibrationTestTest.class.getResource("/data/sim/data/data.1.txt").getFile();
        String graph = CalibrationTestTest.class.getResource("/data/sim/graph/graph.1.txt").getFile();
        String[] args = {
            data,
            graph
        };
        CalibrationTest.main(args);
    }

}

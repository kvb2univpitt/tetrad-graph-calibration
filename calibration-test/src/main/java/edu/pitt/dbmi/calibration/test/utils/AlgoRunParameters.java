package edu.pitt.dbmi.calibration.test.utils;

import edu.cmu.tetrad.util.Parameters;

/**
 *
 * Feb 14, 2023 3:39:21 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class AlgoRunParameters {

    private static Long seed = 1673588774198L;

    private AlgoRunParameters() {
    }

    public static Parameters getParametersForGfciRun() {
        Parameters parameters = new Parameters();

        // test parameters
        parameters.set("alpha", 0.01);

        // score parameters
        parameters.set("priorEquivalentSampleSize", 10.0);
        parameters.set("structurePrior", 1.0);

        // algorithm parameters
        parameters.set("depth", -1);
        parameters.set("maxDegree", 1000);
        parameters.set("maxPathLength", -1);
        parameters.set("completeRuleSetUsed", true);
        parameters.set("doDiscriminatingPathRule", true);
        parameters.set("possibleDsepDone", true);
        parameters.set("timeLag", 0);
        parameters.set("verbose", false);

        // bootstrapping
        parameters.set("numberResampling", 100);
        parameters.set("percentResampleSize", 100);
        parameters.set("resamplingEnsemble", 1);
        parameters.set("seed", seed);
        parameters.set("addOriginalDataset", true);
        parameters.set("resamplingWithReplacement", true);

        return parameters;
    }

}

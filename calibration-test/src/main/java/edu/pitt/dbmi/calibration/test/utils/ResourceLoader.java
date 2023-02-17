package edu.pitt.dbmi.calibration.test.utils;

import edu.cmu.tetrad.data.DataModel;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.GraphPersistence;
import edu.cmu.tetrad.util.DataConvertUtils;
import edu.pitt.dbmi.data.reader.Delimiter;
import edu.pitt.dbmi.data.reader.tabular.VerticalDiscreteTabularDatasetFileReader;
import edu.pitt.dbmi.data.reader.tabular.VerticalDiscreteTabularDatasetReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * Feb 16, 2023 8:07:34 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class ResourceLoader {

    private ResourceLoader() {
    }

    public static Graph loadGraph(Path graphFile) {
        return GraphPersistence.loadGraphTxt(graphFile.toFile());
    }

    public static DataModel readInDataModel(Path dataFile, Delimiter delimiter) throws IOException {
        VerticalDiscreteTabularDatasetReader dataReader = new VerticalDiscreteTabularDatasetFileReader(dataFile, delimiter);

        return DataConvertUtils.toDataModel(dataReader.readInData());
    }

}

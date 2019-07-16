package dynamic.groovy

import com.dynamiko.ml.service.GenericService
import com.dynamiko.ml.util.AbstractML
import com.dynamiko.ml.util.FileObjectExtractor
import net.sf.javaml.core.Instance
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.DenseInstance
import weka.core.Instances
import weka.core.converters.ArffLoader

import java.nio.file.Paths

class SampleML extends AbstractML {
    @Override
    String doML() {
        loadMe();
        return "This is a sample ML";
    }

    /** file names are defined*/
    String TRAINING_DATA_SET_FILENAME="linear-train.arff";
    String TESTING_DATA_SET_FILENAME="linear-test.arff";
    String PREDICTION_DATA_SET_FILENAME="test-confused.arff";

    /**
     * This method is to load the data set.
     * @param fileName
     * @return
     * @throws IOException
     */
    Instances getDataSet(String fileName) throws IOException {
        /**
         * we can set the file i.e., loader.setFile("finename") to load the data
         */
        int classIdx = 1;
        /** the arffloader to load the arff file */
        ArffLoader loader = new ArffLoader();
        /** load the traing data */
        String str = FileObjectExtractor.getPathContent(Paths.get(GenericService.STATIC_BASE+"/"+fileName));
        loader.setSource(new ByteArrayInputStream(str.getBytes()));
        /**
         * we can also set the file like loader3.setFile(new
         * File("test-confused.arff"));
         */
        Instances dataSet = loader.getDataSet();
        /** set the index based on the data given in the arff files */
        dataSet.setClassIndex(classIdx);
        return dataSet;
    }

    /**
     * This method is used to process the input and return the statistics.
     *
     * @throws Exception
     */
    void loadMe() {
        Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
        Instances testingDataSet = getDataSet(TESTING_DATA_SET_FILENAME);
        /** Classifier here is Linear Regression */
        Classifier classifier = new weka.classifiers.functions.LinearRegression();
        /** */
        classifier.buildClassifier(trainingDataSet);
        /**
         * train the alogorithm with the training data and evaluate the
         * algorithm with testing data
         */
        Evaluation eval = new Evaluation(trainingDataSet);
        eval.evaluateModel(classifier, testingDataSet);
        /** Print the algorithm summary */
        System.out.println("** Linear Regression Evaluation with Datasets **");
        System.out.println(eval.toSummaryString());
        System.out.print(" the expression for the input data as per alogorithm is ");
        System.out.println(classifier);

        DenseInstance predicationDataSet = getDataSet(PREDICTION_DATA_SET_FILENAME).lastInstance();
        double value = classifier.classifyInstance(predicationDataSet);
        /** Prediction Output */
        System.out.println(value);
    }
}

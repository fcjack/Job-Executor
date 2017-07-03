package io.datapath.plugins.worker;

import io.datapath.entities.Task;
import io.datapath.entities.parameters.WordCountParameters;
import io.datapath.enums.JobType;
import io.datapath.plugins.util.SparkComponent;
import io.datapath.service.Worker;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.File;
import java.util.Arrays;

/**
 * Created by jackson on 03/07/17.
 */
@Component
public class WordCountWorker implements Worker {

    private SparkComponent sparkComponent;

    @Autowired
    public WordCountWorker(SparkComponent sparkComponent) {
        this.sparkComponent = sparkComponent;
    }

    public JobType getType() {
        return JobType.WORD_COUNT;
    }

    @Override
    public Runnable buildThread(Task task) {
        return () -> {
            Logger logger = Logger.getLogger(getClass());
            String inputFilePath = task.getParameters().get(WordCountParameters.INPUT_FILE).toString();
            String outputFilePath = task.getParameters().get(WordCountParameters.OUTPUT_FILE).toString();

            try {
                JavaSparkContext sparkContext = sparkComponent.getSparkContext();
                // Load our input data.
                JavaRDD<String> input = sparkContext.textFile(inputFilePath);

                // Split up into words.
                JavaRDD<String> words = input.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator());

                // Transform into word and count.
                JavaPairRDD<String, Integer> counts = words.mapToPair((PairFunction<String, String, Integer>) x -> new Tuple2<>(x, 1)).reduceByKey((Function2<Integer, Integer, Integer>) (x, y) -> x + y);

                // Save the word count back out to a text file, causing evaluation.
                File outputDirectory = new File(outputFilePath);
                if (outputDirectory.exists()) {
                    FileUtils.cleanDirectory(outputDirectory);
                    outputDirectory.delete();
                }
                counts.saveAsTextFile(outputFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}

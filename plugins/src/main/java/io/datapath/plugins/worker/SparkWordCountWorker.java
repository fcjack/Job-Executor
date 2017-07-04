package io.datapath.plugins.worker;

import io.datapath.entities.Task;
import io.datapath.entities.parameters.WordCountParameters;
import io.datapath.enums.JobType;
import io.datapath.plugins.util.SparkComponent;
import io.datapath.service.Worker;
import org.apache.commons.io.FileUtils;
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
public class SparkWordCountWorker implements Worker {

    private SparkComponent sparkComponent;

    @Autowired
    public SparkWordCountWorker(SparkComponent sparkComponent) {
        this.sparkComponent = sparkComponent;
    }

    public JobType getType() {
        return JobType.SPARK_WORD_COUNTER;
    }

    @Override
    public Runnable buildThread(Task task) {
        return () -> {
            String inputFilePath = task.getParameters().get(WordCountParameters.INPUT_FILE).toString();

            try {
                JavaSparkContext sparkContext = sparkComponent.getSparkContext();
                // Load our input data.
                JavaRDD<String> input = sparkContext.textFile(inputFilePath);

                // Split up into words.
                JavaRDD<String> words = input.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split("([\\W\\s]+)")).iterator());

                // Transform into word and count.
                JavaPairRDD<String, Integer> counts = words.mapToPair((PairFunction<String, String, Integer>) x -> new Tuple2<>(x, 1)).reduceByKey((Function2<Integer, Integer, Integer>) (x, y) -> x + y);

                // Save the word count back out to a text file, causing evaluation.
                File inputFile = new File(inputFilePath);
                String fileName = inputFile.getName().substring(0, inputFile.getName().indexOf("."));
                File outputDirectory = new File(inputFile.getParent().concat(File.separator).concat(String.format("result_%s", fileName)));
                if (outputDirectory.exists()) {
                    FileUtils.cleanDirectory(outputDirectory);
                    outputDirectory.delete();
                }
                counts.saveAsTextFile(outputDirectory.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}

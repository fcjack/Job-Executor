package io.datapath.plugins.worker;

import io.datapath.entities.Task;
import io.datapath.entities.parameters.WordCountParameters;
import io.datapath.enums.JobType;
import io.datapath.plugins.config.ApplicationProperties;
import io.datapath.service.Worker;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.Arrays;
import java.util.concurrent.FutureTask;

/**
 * Created by jackson on 03/07/17.
 */
@Component
public class WordCountWorker implements Worker {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public WordCountWorker(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public JobType getType() {
        return JobType.WORD_COUNT;
    }

    public FutureTask buildFutureTask(Task task) {
        return new FutureTask<>(() -> {
            String inputFilePath = task.getParameters().get(WordCountParameters.INPUT_FILE).toString();
            String outputFilePath = task.getParameters().get(WordCountParameters.OUTPUT_FILE).toString();
            String sparkHost = applicationProperties.getSparkHost();
            Integer sparkPort = applicationProperties.getSparkPort();


            SparkConf conf = new SparkConf();
            conf.setAppName("wordCount");
            conf.setMaster(String.format("spark://%s:%d", sparkHost, sparkPort));

            JavaSparkContext sc = new JavaSparkContext(conf);
            // Load our input data.
            JavaRDD<String> input = sc.textFile(inputFilePath);

            // Split up into words.
            JavaRDD<String> words = input.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator());

            // Transform into word and count.
            JavaPairRDD<String, Integer> counts = words.mapToPair((PairFunction<String, String, Integer>) x -> new Tuple2<>(x, 1)).reduceByKey((Function2<Integer, Integer, Integer>) (x, y) -> x + y);

            // Save the word count back out to a text file, causing evaluation.
            counts.saveAsTextFile(outputFilePath);

        }, null);
    }
}

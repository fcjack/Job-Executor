package io.datapath.plugins.util;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Component;

/**
 * Created by jackson on 03/07/17.
 */
@Component
public class SparkComponent {

    private JavaSparkContext sparkContext;

    public SparkComponent() {
        SparkConf conf = new SparkConf();
        conf.setAppName("wordCount");
        conf.setMaster("local");

        this.sparkContext = new JavaSparkContext(conf);
    }

    public JavaSparkContext getSparkContext() {
        return sparkContext;
    }
}

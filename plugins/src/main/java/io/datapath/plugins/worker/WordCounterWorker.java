package io.datapath.plugins.worker;

import io.datapath.entities.Task;
import io.datapath.entities.parameters.WordCountParameters;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jackson on 04/07/17.
 */
@Component
public class WordCounterWorker implements Worker {
    @Override
    public JobType getType() {
        return JobType.WORD_COUNTER;
    }

    @Override
    public Runnable buildThread(Task task) {
        return () -> {
            String inputFilePath = task.getParameters().get(WordCountParameters.INPUT_FILE).toString();

            try (Stream<String> stream = Files.lines(Paths.get(inputFilePath))) {
                ConcurrentMap<String, Long> result = stream.map(line -> line.split("([\\W\\s]+)"))
                        .flatMap(Arrays::stream)
                        .parallel()
                        .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));

                File inputFile = new File(inputFilePath);
                File resultFile = new File(inputFile.getParent().concat(File.separator).concat(String.format("result_%s", inputFile.getName())));

                if (!resultFile.exists()) {
                    resultFile.createNewFile();
                } else {
                }

                BufferedWriter writer = Files.newBufferedWriter(resultFile.toPath());
                for (String word : result.keySet()) {
                    writer.write(String.format("%s -> %s", word, result.get(word)));
                    writer.newLine();
                }

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

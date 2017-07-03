package io.datapath.worker;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.FutureTask;

/**
 * Created by jackson on 02/07/17.
 */
@Component
public class CalculatePiWorker implements Worker {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public JobType getType() {
        return JobType.CALCULATE_PI;
    }

    @Override
    public FutureTask buildFutureTask(Task task) {
        return new FutureTask<Void>(() -> {
            double output = 0.0;
            boolean sum = true;

            for (int i = 1; i < 10; i += 2) {
                if (sum) {
                    output += 1 / i;
                    sum = false;
                } else {
                    output -= 1 / i;
                    sum = true;
                }
            }

            double pi = 4 * output;
            String name = Thread.currentThread().getName();
            logger.info(String.format("Current thread with name %s and calculated PI value %d", name, pi));

        }, null);
    }

}

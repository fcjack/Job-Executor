package io.datapath.worker;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by jackson on 02/07/17.
 */
@Component
public class WaitForTwentySecondsWorker implements Worker {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public JobType getType() {
        return JobType.WAIT_FOR_20_SECONDS;
    }

    @Override
    public Runnable buildThread(Task task) {
        return () -> {
            try {
                logger.info("Starting task to wait 20 seconds");
                Thread.sleep(20_000);
                logger.info("Completed task to wait 20 seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

}

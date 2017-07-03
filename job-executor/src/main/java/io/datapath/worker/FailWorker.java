package io.datapath.worker;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by jackson on 02/07/17.
 */
@Component
public class FailWorker implements Worker {

    @Override
    public JobType getType() {
        return JobType.FAILURE;
    }

    @Override
    public FutureTask buildFutureTask(Task task) {
        return new FutureTask<>((Callable<Void>) () -> {
            String name = Thread.currentThread().getName();
            throw new Exception(String.format("The current thread with name %s and Failure task executed", name));
        });
    }
}

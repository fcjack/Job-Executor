package io.datapath.worker;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.springframework.stereotype.Component;

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
    public Runnable buildThread(Task task) {
        return () -> {
            int a = 4, b = 0;
            System.out.println("a and b=" + a + ":" + b);
            System.out.println("a/b:" + (a / b));
            System.out.println("Thread Name in Runnable after divide by zero:" + Thread.currentThread().getName());
        };
    }

}

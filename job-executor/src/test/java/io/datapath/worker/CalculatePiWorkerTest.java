package io.datapath.worker;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.service.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by jackson on 04/07/17.
 */
@RunWith(JUnit4.class)
public class CalculatePiWorkerTest {

    private Worker worker;

    @Test
    public void testCalculatePiWorker() {
        worker = new CalculatePiWorker();
        Task task = new Task();
        task.setJobType(JobType.CALCULATE_PI);
        Runnable runnable = worker.buildThread(task);
        runnable.run();
    }

    @Test(expected = Exception.class)
    public void testFailure() {
        worker = new FailWorker();
        Task task = new Task();
        task.setJobType(JobType.FAILURE);
        Runnable runnable = worker.buildThread(task);
        runnable.run();
    }

    @Test
    public void testWait20Seconds() {
        worker = new WaitForTwentySecondsWorker();
        Task task = new Task();
        task.setJobType(JobType.WAIT_FOR_20_SECONDS);
        Runnable runnable = worker.buildThread(task);
        runnable.run();
    }
}

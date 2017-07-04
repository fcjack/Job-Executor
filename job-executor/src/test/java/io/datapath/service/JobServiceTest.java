package io.datapath.service;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.plugins.worker.WordCounterWorker;
import io.datapath.worker.CalculatePiWorker;
import io.datapath.worker.FailWorker;
import io.datapath.worker.WaitForTwentySecondsWorker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by jackson on 04/07/17.
 */
@RunWith(JUnit4.class)
public class JobServiceTest {

    private JobService jobService;

    @Before
    public void setup() {
        WaitForTwentySecondsWorker waitFor20Seconds = new WaitForTwentySecondsWorker();
        FailWorker failWorker = new FailWorker();
        CalculatePiWorker calculatePiWorker = new CalculatePiWorker();
        WordCounterWorker wordCountParameters = new WordCounterWorker();
        jobService = new JobServiceImpl(Arrays.asList(failWorker, calculatePiWorker, wordCountParameters, waitFor20Seconds));
    }

    @Test
    public void testResizeThreadPool() {
        jobService.resizePoolSize(1);
        Map<String, Object> status = jobService.getStatus();
        Assert.assertEquals(1, status.get("corePoolSize"));
    }

    @Test
    public void testScheduleTask() {
        Task task = new Task();
        task.setJobType(JobType.WAIT_FOR_20_SECONDS);

        jobService.scheduleTask(task);
        Assert.assertEquals(1L, jobService.getQueueSize().longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScheduleTaskException() {
        Task task = new Task();
        jobService.scheduleTask(task);
    }
}

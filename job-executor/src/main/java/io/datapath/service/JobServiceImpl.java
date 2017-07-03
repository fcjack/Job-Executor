package io.datapath.service;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jackson on 02/07/17.
 */
@Service
public class JobServiceImpl implements JobService {

    private ThreadPoolExecutor executorService;

    private Map<JobType, Worker> mapJobWorker;

    @Autowired
    public JobServiceImpl(List<Worker> workers) {
        mapJobWorker = new HashMap<>();
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (Worker worker : workers) {
            mapJobWorker.put(worker.getType(), worker);
        }
    }

    @Override
    public void scheduleTask(Task task) {
        Worker worker = mapJobWorker.get(task.getJobType());
        if (worker != null) {
            FutureTask futureTask = worker.buildFutureTask(task);
            executorService.execute(futureTask);
        } else {
            throw new IllegalArgumentException("Task with Job Type not supported");
        }
    }

    @Override
    public void resizePoolSize(Integer poolSize) {
        executorService.setCorePoolSize(poolSize);
    }
}

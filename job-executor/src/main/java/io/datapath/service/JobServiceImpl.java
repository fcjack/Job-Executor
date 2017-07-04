package io.datapath.service;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import org.spark_project.jetty.util.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jackson on 02/07/17.
 */
@Service
public class JobServiceImpl implements JobService {

    private ThreadPoolExecutor executorService;

    private Map<JobType, Worker> mapJobWorker;

    private Queue<Task> tasksQueue;

    @Autowired
    public JobServiceImpl(List<Worker> workers) {
        mapJobWorker = new HashMap<>();
        tasksQueue = new ArrayQueue<>();
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (Worker worker : workers) {
            mapJobWorker.put(worker.getType(), worker);
        }
    }

    @Override
    public void scheduleTask(Task task) {
        Worker worker = mapJobWorker.get(task.getJobType());
        if (worker != null) {
            tasksQueue.add(task);
        } else {
            throw new IllegalArgumentException("Task with Job Type not supported");
        }
    }

    @Override
    public void resizePoolSize(Integer poolSize) {
        executorService.setCorePoolSize(poolSize);
        executorService.setMaximumPoolSize(poolSize);
    }

    @Override
    public Map<String, Object> getStatus() {
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("poolSize", executorService.getPoolSize());
        statusMap.put("corePoolSize", executorService.getCorePoolSize());
        statusMap.put("maximumPoolSize", executorService.getMaximumPoolSize());
        statusMap.put("largestPoolSize", executorService.getLargestPoolSize());
        statusMap.put("activeCount", executorService.getActiveCount());
        statusMap.put("completedTaskCount", executorService.getCompletedTaskCount());
        statusMap.put("queueSize", executorService.getQueue().size());
        return statusMap;
    }

    @Override
    public Long getQueueSize() {
        return (long) tasksQueue.size();
    }

    @Scheduled(fixedRate = 1000)
    private void executeTasks() {
        while (!tasksQueue.isEmpty()) {
            Task task = tasksQueue.poll();
            Worker worker = mapJobWorker.get(task.getJobType());
            executorService.execute(worker.buildThread(task));
        }
    }

}

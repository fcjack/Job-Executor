package io.datapath.service;

import io.datapath.entities.Task;

/**
 * Created by jackson on 02/07/17.
 */
public interface JobService {

    void scheduleTask(Task task);

    void resizePoolSize(Integer poolSize);
}

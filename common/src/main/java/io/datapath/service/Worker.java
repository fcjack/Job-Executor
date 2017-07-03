package io.datapath.service;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;

import java.util.concurrent.FutureTask;

/**
 * Created by jackson on 02/07/17.
 */
public interface Worker {

    JobType getType();

    Runnable buildThread(Task task);
}

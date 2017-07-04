package io.datapath.controller;

import io.datapath.entities.Task;
import io.datapath.service.JobService;
import io.datapath.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackson on 02/07/17.
 */
@RestController
@RequestMapping("task")
public class RequestJobController {

    private final JobService jobService;

    private ValidationUtil validationUtil;

    @Autowired
    public RequestJobController(JobService jobService, ValidationUtil validationUtil) {
        this.jobService = jobService;
        this.validationUtil = validationUtil;
    }

    @RequestMapping(value = "schedule", method = RequestMethod.POST)
    public void scheduleTask(@RequestBody Task task) {
        validationUtil.validate(task);
        jobService.scheduleTask(task);
    }
}

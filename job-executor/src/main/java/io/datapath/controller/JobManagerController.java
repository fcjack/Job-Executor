package io.datapath.controller;

import io.datapath.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackson on 02/07/17.
 */
@RequestMapping("jobworker")
@RestController
public class JobManagerController {

    private final JobService jobService;

    @Autowired
    public JobManagerController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "resize/{poolSize}", method = RequestMethod.POST)
    public void resizePool(@PathVariable Integer poolSize) {
        if (poolSize > 0) {
            jobService.resizePoolSize(poolSize);
        } else {
            throw new IllegalArgumentException("Pool size has to be more the 0");
        }
    }
}

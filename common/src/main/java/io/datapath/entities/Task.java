package io.datapath.entities;

import io.datapath.enums.JobType;
import io.swagger.annotations.ApiModel;

import java.util.Map;

/**
 * Created by jackson on 02/07/17.
 */
@ApiModel
public class Task {

    private JobType jobType;
    private Map<String, Object> parameters;

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}

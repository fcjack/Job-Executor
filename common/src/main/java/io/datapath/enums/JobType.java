package io.datapath.enums;

import io.datapath.entities.parameters.TaskParameters;
import io.datapath.entities.parameters.WordCountParameters;

/**
 * Created by jackson on 02/07/17.
 */
public enum JobType {

    FAILURE(null, false),
    CALCULATE_PI(null, false),
    WAIT_FOR_20_SECONDS(null, false),
    WORD_COUNT(new WordCountParameters(), true);

    TaskParameters parameters;
    Boolean hasParameters;

    JobType(TaskParameters parameters, boolean hasParameters) {
        this.hasParameters = hasParameters;
        this.parameters = parameters;
    }

    public TaskParameters getParameters() {
        return parameters;
    }

    public void setParameters(TaskParameters parameters) {
        this.parameters = parameters;
    }

    public Boolean hasParameters() {
        return hasParameters;
    }

}

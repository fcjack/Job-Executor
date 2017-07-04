package io.datapath.validation;

import io.datapath.entities.Task;
import io.datapath.entities.parameters.WordCountParameters;
import io.datapath.enums.JobType;
import io.datapath.exception.ValidationException;
import io.datapath.util.ValidationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

/**
 * Created by jackson on 04/07/17.
 */
@RunWith(JUnit4.class)
public class ValidationUtilTest {

    private ValidationUtil validationUtil;

    @Test
    public void validateCompleteTask() {
        validationUtil = new ValidationUtil();
        Task task = new Task();
        task.setJobType(JobType.WORD_COUNTER);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(WordCountParameters.INPUT_FILE, "/home/file/t8.shakespeare.txt");
        task.setParameters(parameters);
        validationUtil.validate(task);
    }

    @Test(expected = ValidationException.class)
    public void validateUncompletedTask() {
        validationUtil = new ValidationUtil();
        Task task = new Task();
        task.setJobType(JobType.SPARK_WORD_COUNTER);
        HashMap<String, Object> parameters = new HashMap<>();
        task.setParameters(parameters);
        validationUtil.validate(task);
    }
}

package io.datapath.util;

import io.datapath.entities.Task;
import io.datapath.enums.JobType;
import io.datapath.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jackson on 02/07/17.
 */
@Component
public class ValidationUtil {

    public void validate(Task task) {
        JobType jobType = task.getJobType();
        if (jobType.hasParameters()) {
            String[] fields = jobType.getParameters().getMandatoryFields();

            if (task.getParameters() == null || task.getParameters().isEmpty()) {
                throw new ValidationException(String.format("These parameters are mandatory: %s", StringUtils.join(fields, ",")));
            } else {
                Map<String, Object> parameters = task.getParameters();
                List<String> mandatoryFields = new ArrayList<>();
                for (String mandatoryField : fields) {
                    if (!parameters.containsKey(mandatoryField)) {
                        mandatoryFields.add(mandatoryField);
                    }
                }

                if (!mandatoryFields.isEmpty()) {
                    throw new ValidationException(String.format("These parameters are mandatory: %s", StringUtils.join(mandatoryFields, ",")));
                }
            }
        }
    }
}

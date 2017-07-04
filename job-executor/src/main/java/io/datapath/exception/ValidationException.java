package io.datapath.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jackson on 03/07/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends IllegalArgumentException {

    public ValidationException(String format) {
        super(format);
    }
}

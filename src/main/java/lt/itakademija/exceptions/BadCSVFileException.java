package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY)
public class BadCSVFileException extends RuntimeException {

    public BadCSVFileException(String message) {
        super(message);
    }

    public BadCSVFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

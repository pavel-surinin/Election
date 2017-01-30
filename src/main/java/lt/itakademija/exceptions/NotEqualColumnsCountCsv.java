package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason="Not Equal Columns Count Csv")
public class NotEqualColumnsCountCsv extends RuntimeException {

    public NotEqualColumnsCountCsv(String message) {
        super(message);
    }

    public NotEqualColumnsCountCsv(String message, Throwable cause) {
        super(message, cause);
    }
}

package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="Candidate From CSV is in party")
public class CandidateIsInCountyException extends RuntimeException {

    public CandidateIsInCountyException(String message) {
        super(message);
    }

    public CandidateIsInCountyException(String message, Throwable cause) {
        super(message, cause);
    }
}

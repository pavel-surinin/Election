package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-03-08.
 */
@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class DeletingCountyWithCandidatesAfterSingleVotingException extends RuntimeException {

    public DeletingCountyWithCandidatesAfterSingleVotingException(String message) {
        super(message);
    }

    public DeletingCountyWithCandidatesAfterSingleVotingException(String message, Throwable cause) {
        super(message, cause);
    }
}


package lt.itakademija.electors.results.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@Service
public class ResultMultiService {

    @Autowired
    ResultMultiRepository repository;

    @Transactional
    public String save(List<ResultMultiEntity> results) {
        results.stream().forEach(res -> repository.save(res));
        return "Votes registered";
    }
}

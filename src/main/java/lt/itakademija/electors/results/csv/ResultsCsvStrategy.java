package lt.itakademija.electors.results.csv;

import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-03-12.
 */
@Service
public interface ResultsCsvStrategy {
    String getReport();
    String getReportName();
}

package lt.itakademija.electors.results.csv;

import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-03-12.
 */
@Service
public class ResultsCsvSingle implements ResultsCsvStrategy {

    private ResultsGeneralReport resultsGeneralReport;
    private String reportName = "single-winners";

    public ResultsCsvSingle() {
    }

    public ResultsCsvSingle(ResultsGeneralReport generalReport) {
        this.resultsGeneralReport = generalReport;
    }

    @Override
    public String getReportName() {
        return reportName;
    }

    @Override
    public String getReport() {
        String report = "vardas pavardė,balsų skaičius,apygarda\n";
        for (CandidateIntDTO dto : resultsGeneralReport.getSingleWinners()) {
            report = report +
                    dto.getCandidate().getName() + " " +
                    dto.getCandidate().getSurname() + "," +
                    dto.getVotes() + "," +
                    dto.getCandidate().getCountyName() + "\n";
        }
        return report;
    }
}

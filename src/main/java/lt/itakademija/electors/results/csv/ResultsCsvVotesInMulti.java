package lt.itakademija.electors.results.csv;

import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;

/**
 * Created by Pavel on 2017-03-13.
 */
public class ResultsCsvVotesInMulti implements ResultsCsvStrategy {

    private ResultsGeneralReport resultsGeneralReport;
    private String reportName = "votes-in-multi";

    public ResultsCsvVotesInMulti(ResultsGeneralReport generalReport) {
        this.resultsGeneralReport = generalReport;
    }

    @Override
    public String getReport() {
        String report = "partija,balsų skaičius\n";
        for (PartyIntDTO dto : resultsGeneralReport.getVotesInMulti()) {
            report = report +
                    dto.getPar().getName() + "," +
                    dto.getVotes() + "\n";
        }
        return report;
    }

    @Override
    public String getReportName() {
        return reportName;
    }
}

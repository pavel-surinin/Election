package lt.itakademija.electors.results.csv;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.results.reports.ResultsGeneralReport;

/**
 * Created by Pavel on 2017-03-13.
 */
public class ResultsCsvMulti implements ResultsCsvStrategy {

    private ResultsGeneralReport resultsGeneralReport;
    private String reportName = "multi-winners";

    public ResultsCsvMulti(ResultsGeneralReport generalReport) {
        this.resultsGeneralReport = generalReport;
    }

    @Override
    public String getReport() {
        String report = "vardas pavardė,partija,numeris partijoje,gavo reitongo balų\n";
        for (CandidateReport candidate : resultsGeneralReport.getMultiWinners()) {
            report = report +
                    candidate.getName() + " " +
                    candidate.getSurname() + "," +
                    candidate.getPartijosPavadinimas() + "," +
                    candidate.getNumberInParty() + "\n";
        }
        return report;
    }

    @Override
    public String getReportName() {
        return reportName;
    }
}

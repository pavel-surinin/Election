package lt.itakademija.electors.results.csv;

import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;

/**
 * Created by Pavel on 2017-03-13.
 */
public class ResultsCsvMandatesPerPartyMulti implements ResultsCsvStrategy {

    private ResultsGeneralReport resultsGeneralReport;
    private String reportName = "mandates-per-party";

    public ResultsCsvMandatesPerPartyMulti(ResultsGeneralReport generalReport) {
        this.resultsGeneralReport = generalReport;
    }

    @Override
    public String getReport() {
        String report = "partijos pavadinimas,mandatų kiekis\n";
        for (PartyIntDTO dto : resultsGeneralReport.getMandatesPerPartyInMulti()) {
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

package lt.itakademija.electors.results.csv;

import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;
import lt.itakademija.electors.results.reports.dto.StringLongDTO;

/**
 * Created by Pavel on 2017-03-13.
 */
public class ResultsCsvMandatesPerPartyGeneral implements ResultsCsvStrategy {

    private ResultsGeneralReport resultsGeneralReport;
    private String reportName = "mandates-per-party-general";

    public ResultsCsvMandatesPerPartyGeneral(ResultsGeneralReport generalReport) {
        this.resultsGeneralReport = generalReport;
    }

    @Override
    public String getReport() {
        String report = "partijos pavadinimas,mandatų kiekis\n";
        for (StringLongDTO dto : resultsGeneralReport.getMandatesPerPartyGeneralLive()) {
            report = report +
                    dto.getName() + "," +
                    dto.getNumber() + "\n";
        }
        return report;
    }

    @Override
    public String getReportName() {
        return reportName;
    }
}

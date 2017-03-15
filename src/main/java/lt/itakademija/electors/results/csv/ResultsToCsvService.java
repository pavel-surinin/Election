package lt.itakademija.electors.results.csv;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Pavel on 2017-03-12.
 */
@Service
public class ResultsToCsvService {

    public ResponseEntity getCsvResults(ResultsCsvStrategy res) {
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new InputStreamResource(getResultsFile(res)));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private FileInputStream getResultsFile(ResultsCsvStrategy res) {
        CSVUtils.writeCsvFile(res.getReportName(), res.getReport());
        try {
            return new FileInputStream(new File("src/main/resources/results-csv/" + res.getReportName() + ".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package lt.itakademija.electors.results.csv;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Pavel on 2017-03-12.
 */
@Service
public class ResultsToCsvService {

    public ResponseEntity gtCsvResults(ResultsCsvStrategy res) {
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
        mapResults(res);
        try {
            return new FileInputStream(new File("src/main/resources/results-csv/" + res.getReportName() + ".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mapResults(ResultsCsvStrategy res) {

        try {
            FileOutputStream fis = new FileOutputStream(new File("src/main/resources/results-csv/" + res.getReportName() + ".csv"));
            IOUtils.write(res.getReport(),fis,"UTF-8");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

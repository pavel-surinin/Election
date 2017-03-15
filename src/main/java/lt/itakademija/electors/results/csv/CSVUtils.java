package lt.itakademija.electors.results.csv;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Pavel on 2017-03-13.
 */
public class CSVUtils {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void modifyResponse(HttpServletResponse response, String name){
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                name + ".csv");
        response.setHeader(headerKey, headerValue);
        response.setContentType("text/csv");
    }

    public static void writeCsvFile(String fileName, String info) {
        String filePath = "src/main/resources/results-csv/" + fileName + ".csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            String[] lines = info.split("\n");
            for (String line : lines) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    try {
                        fileWriter.append("\"");
                        fileWriter.append(values[i]);
                        fileWriter.append("\"");
                        if (i == values.length - 1) {
                            fileWriter.append(NEW_LINE_SEPARATOR);
                        } else {
                            fileWriter.append(COMMA_DELIMITER);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
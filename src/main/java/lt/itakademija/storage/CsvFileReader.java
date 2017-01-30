package lt.itakademija.storage;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.exceptions.NotEqualColumnsCountCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-29.
 */
@Service
public class CsvFileReader {

    private PartyService partyService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PartyRepository partyRepository;

    public List<CandidateEntity> read(InputStream file) {
        List<String> lines = getStringList(file);
        List<String[]> linesSeparatedValues = splitLinesToValues(lines);
        if (checkForEqualColumnSize(linesSeparatedValues)) {
            throw new NotEqualColumnsCountCsv("Not Equal Columns Count *.csv - " + file.toString());
        }
        List<CandidateEntity> candidatesList = getCandidatesList(linesSeparatedValues);
        return candidatesList;
    }

    private List<CandidateEntity> getCandidatesList(List<String[]> linesSeparatedValues) {
        List<CandidateEntity> candidates = linesSeparatedValues
                .stream().map(line -> {
                    CandidateEntity can = new CandidateEntity();
                    can.setName(line[0]);
                    can.setSurname(line[1]);
                    String dateFromCSV = line[2];
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(dateFromCSV);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    can.setBirthDate(date);
                    can.setNumberInParty(Integer.parseInt(line[3]));
                    can.setDescription(line[4]);
                    return can;
                }).collect(Collectors.toList());
        return candidates;
    }

    private List<String> getStringList(InputStream file) {
        Scanner sc = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        return lines;
    }

    private boolean checkForEqualColumnSize(List<String[]> list) {
        int columnsCount = list.get(0).length;
        boolean isMissmatch = list.stream().anyMatch(row -> row.length != columnsCount);
        return isMissmatch;
    }

    private List<String[]> splitLinesToValues(List<String> list) {
        List<String[]> values = new ArrayList<>();
        list.stream().forEach(row -> values.add(row.split("\\s*,\\s*")));
        return values;
    }
}

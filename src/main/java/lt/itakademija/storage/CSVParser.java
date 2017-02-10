package lt.itakademija.storage;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.exceptions.BadCSVFileExceprion;
import lt.itakademija.exceptions.NotEqualColumnsCountCsv;
import lt.itakademija.exceptions.PartyDoesNotExistException;
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
public class CSVParser {

    private String partyOrCounty;

    @Autowired
    private PartyService partyService;

    public List<CandidateEntity> extractCandidates(InputStream file) {
        List<String[]> linesSeparatedValues = splitLinesToValues(getStringList(file));
        checkForEqualColumnSize(linesSeparatedValues);
        linesSeparatedValues.remove(0);
        return getCandidates(partyOrCounty, linesSeparatedValues);
    }

    private List<CandidateEntity> getCandidatesListForCounty(List<String[]> linesSeparatedValues) {
        return linesSeparatedValues
                .stream()
                .map(line -> {
                    try{
                        CandidateEntity can = new CandidateEntity();
                        can.setName(line[0]);
                        can.setSurname(line[1]);
                        can.setBirthDate(parseDate(line[2]));
                        can.setDescription(line[5]);
                        can.setPartyDependencies(parseParty(line[3]));
                        can.setNumberInParty(parseNullOrInteger(line[4]));
                        return can;
                    } catch (Exception t){
                        throw new BadCSVFileExceprion("Not acceptable CSV data for county single-list", t);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<CandidateEntity> getCandidatesListForParty(List<String[]> linesSeparatedValues) {
        return  linesSeparatedValues
                .stream()
                .map(line -> {
                    try {
                        CandidateEntity can = new CandidateEntity();
                        can.setName(line[0]);
                        can.setSurname(line[1]);
                        can.setBirthDate(parseDate(line[2]));
                        can.setNumberInParty(parseNullOrInteger(line[3]));
                        can.setDescription(line[4]);
                        return can;
                    } catch (Throwable pe) {
                        throw new BadCSVFileExceprion("Not acceptable csv data for party-list");
                    }
                }).collect(Collectors.toList());
    }

    private PartyEntity parseParty(String partyId) {
        if (parseNullOrInteger(partyId) == null) {
            return null;
        }
        PartyEntity party = partyService.getPartyByNumber(parseNullOrInteger(partyId));
        if (party == null) {
            throw new PartyDoesNotExistException("Party with number " + partyId + " does not exist");
        }
        return party;
    }

    private Date parseDate(String string) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Long parseNullOrLong(String s) {
        if (s.equals("null")) {
            return null;
        } else {
            return Long.parseLong(s);
        }
    }

    private Integer parseNullOrInteger(String s) {
        if (s.equals("null")) {
            return null;
        } else {
            return Integer.parseInt(s);
        }
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
        if (isMissmatch) {
            throw new NotEqualColumnsCountCsv("Not equal columns count *.csv");
        }
        return isMissmatch;
    }

    private List<String[]> splitLinesToValues(List<String> list) {
        List<String[]> values = new ArrayList<>();
        list.stream().forEach(row -> values.add(row.split("\\s*,\\s*")));
        return values;
    }

    public void setPartyOrCounty(String POC) {
        this.partyOrCounty = POC;
    }

    private List<CandidateEntity> getCandidates(String partyOrCounty, List<String[]> linesSeparatedValues) {
        if (partyOrCounty.equals("Party")) {
            return getCandidatesListForParty(linesSeparatedValues);
        } else {
            return getCandidatesListForCounty(linesSeparatedValues);
        }
    }
}

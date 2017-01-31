package lt.itakademija.storage;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.exceptions.BadCSVFileExceprion;
import lt.itakademija.exceptions.NotEqualColumnsCountCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-29.
 */
@Service
public class CsvFileReader {

    private String partyOrCounty;

    private PartyService partyService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PartyRepository partyRepository;

    public CsvFileReader() {
        super();
    }

    public CsvFileReader(String partyOrCounty) {
        this.partyOrCounty = partyOrCounty;
    }

    public List<CandidateEntity> read(InputStream file) {
        List<String> lines = getStringList(file);
        List<String[]> linesSeparatedValues = splitLinesToValues(lines);
        checkForEqualColumnSize(linesSeparatedValues);
        linesSeparatedValues.remove(0);
        return getCandidates(partyOrCounty, linesSeparatedValues);
    }

    private List<CandidateEntity> getCandidates(String partyOrCounty, List<String[]> linesSeparatedValues) {
        if(partyOrCounty.equals("Party")){
            return getCandidatesListForParty(linesSeparatedValues);
        } else {
            return getCandidatesListForCounty(linesSeparatedValues);
        }
    }

    private List<CandidateEntity> getCandidatesListForCounty(List<String[]> linesSeparatedValues) {
        return linesSeparatedValues
                .stream()
                .map(line -> {
                    try{
                        System.out.println("-------------------------county-------------------------");
                        CandidateEntity can = new CandidateEntity();
                        can.setName(line[0]);
                        can.setSurname(line[1]);
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date date = null;
                        date = format.parse(line[2]);
                        can.setBirthDate(date);
                        can.setDescription(line[5]);
                        can.setPartyDependencies(partyRepository.getById(Long.parseLong(line[3])));
                        can.setNumberInParty(Integer.parseInt(line[4]));
                        return can;
                    }
                    catch(Throwable t){
                        throw new BadCSVFileExceprion("Bad csv data");
                    }

                })
                .collect(Collectors.toList());
    }

    private List<CandidateEntity> getCandidatesListForParty(List<String[]> linesSeparatedValues) {
        List<CandidateEntity> candidates = new ArrayList<>();
            candidates = linesSeparatedValues
                    .stream()
                    .map(line -> {
                        try {
                        CandidateEntity can = new CandidateEntity();
                        can.setName(line[0]);
                        can.setSurname(line[1]);
                        String dateFromCSV = line[2];
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date date = null;
                        date = format.parse(dateFromCSV);
                        can.setBirthDate(date);
                        can.setNumberInParty(Integer.parseInt(line[3]));
                        can.setDescription(line[4]);
                        return can;
                        } catch (Throwable pe) {
                            throw new BadCSVFileExceprion("Bad CSV data exception");
                        }
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
        if (isMissmatch){
            throw new NotEqualColumnsCountCsv("Not equal columns count *.csv");
        }
        return isMissmatch;
    }

    private List<String[]> splitLinesToValues(List<String> list) {
        List<String[]> values = new ArrayList<>();
        list.stream().forEach(row -> values.add(row.split("\\s*,\\s*")));
        return values;
    }
}

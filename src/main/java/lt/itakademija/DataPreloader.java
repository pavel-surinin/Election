package lt.itakademija;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-02-07.
 */
@Service
public class DataPreloader {

    @Autowired
    CandidateService service;
    @Autowired
    CountyService countyService;
    @Autowired
    DistrictService districtService;
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    DistrictRepresentativeService districtRepresentativeService;
    @Autowired
    PartyService partyService;
    @Autowired
    ResultSingleService resultSingleService;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    CandidateService candidateService;

    public void createCounties(){
        String counties = "Naujamiesčio\n" +
                "Senamiessčio\n" +
                "Antakalnio\n" +
                "Žirmūnų\n" +
                "Fabijoniškių\n" +
                "Šeškinės\n" +
                "Justiniškių\n" +
                "Karoliniškių\n" +
                "Lazdynų\n" +
                "Naujosios Vilnios\n" +
                "Panerių\n" +
                "Verkių\n" +
                "Centro–Žaliakalnio\n" +
                "Šilainių\n" +
                "Kalniečių\n" +
                "Dainavos\n" +
                "Petrašiūnų\n" +
                "Panemunės\n" +
                "Aleksoto–Vilijampolės\n" +
                "Baltijos\n" +
                "Marių\n" +
                "Pajūrio\n" +
                "Danės\n" +
                "Saulės\n" +
                "Aušros\n" +
                "Nevėžio\n" +
                "Vakarinė\n" +
                "Aukštaitijos\n" +
                "Marijampolės\n" +
                "Alytaus\n" +
                "Gargždų\n" +
                "Šilutės\n" +
                "Pietų Žemaitijos\n" +
                "Tauragės\n" +
                "Plungės\n" +
                "Kretingos–Palangos\n"+
                "Kuršo\n" +
                "Mažeikių\n" +
                "Akmenės–Mažeikių\n" +
                "Telšių\n" +
                "Kelmės–Šiaulių\n" +
                "Raseinių–Kėdainių\n" +
                "Kėdainių\n" +
                "Radviliškio\n" +
                "Kuršėnų–Dainų\n" +
                "Žiemgalos\n" +
                "Pasvalio–Pakruojo\n" +
                "Biržų–Kupiškio\n" +
                "Anykščių–Panevėžio\n" +
                "Sėlos\n" +
                "Utenos\n" +
                "Visagino–Zarasų\n" +
                "Nalšios\n" +
                "Molėtų–Širvintų\n" +
                "Nemenčinės\n" +
                "Šalčininkų–Vilniaus\n" +
                "Medininkų\n" +
                "Trakų–Vievio\n" +
                "Kaišiadorių–Elektrėnų\n" +
                "Jonavos\n" +
                "Ukmergės\n" +
                "Jurbarko–Pagėgių\n" +
                "Sūduvos\n" +
                "Zanavykų\n" +
                "Raudondvario\n" +
                "Garliavos\n" +
                "Prienų–Birštono\n" +
                "Vilkaviškio\n" +
                "Dzūkijos\n" +
                "Varėnos–Trakų\n" +
                "Lazdijų–Druskininkų";
        final String[] countiesArr = counties.split("\n");
        for (String cou : countiesArr) {
            CountyEntity countyEntity = new CountyEntity();
            countyEntity.setName(cou);
            countyService.save(countyEntity);
        }
    }

    public void loadCandidates(){
        CountyEntity ce = new CountyEntity();
        ce.setName("Stebuklų šalis");
        final CountyEntity ces = countyService.save(ce);
        DistrictEntity de = saveDistrict(ces, "Maža" , "Po tiltu");
        saveRep(de, "Burtininkas", "Antanas");
        DistrictEntity de2 = saveDistrict(ces, "Raudona" , "Nosies g. 3");
        saveRep(de2, "Jiezus", "Marija");
        DistrictEntity de3 = saveDistrict(ces, "Mėlyna" , "Pijokų g. 40");
        saveRep(de3, "Piktas", "Žmogėnas");


        createCandidate(ces,"Vardas","Pavarde","Lirika");
        createCandidate(ces,"Osvaldas","Rimkus","Lirika");
        createCandidate(ces,"Marekas","Testeris","Lirika");
        createCandidate(ces,"Vytautas","Linkus","Lirika");
        createCandidate(ces,"Pavel","Surinin","Lirika");
        createCandidate(ces,"Gabriele","Seliunaite","Lirika");

        districtRepository.findAll().stream().forEach(d-> votesInDistrict(d));
    }

    private void votesInDistrict(DistrictEntity de3) {
        final List<CandidateEntity> candidatesList = candidateRepository.getCandidatesList();
        List<ResultSingleEntity> resultSingleEntities = new ArrayList<>();
        ResultSingleEntity spoiled = new ResultSingleEntity();
        CandidateEntity spc = new CandidateEntity();
        spc.setId(-1991L);
        spoiled.setCandidate(spc);
        spoiled.setVotes(10L);
        spoiled.setDistrict(de3);
        resultSingleEntities.add(spoiled);
        for (int i = 0; i < 10; i++) {
            final int nextInt = new Random().nextInt(candidatesList.size() - 1);
            ResultSingleEntity res = new ResultSingleEntity();
            res.setCandidate(candidatesList.get(nextInt));
            res.setDatePublished(new Date());
            res.setDistrict(de3);
            Integer round = Math.abs(Math.round(new Random().nextInt(500) * new Random().nextFloat() * new Random().nextFloat()));
            res.setVotes(round.longValue());
            resultSingleEntities.add(res);
        }
        resultSingleService.save(resultSingleEntities);
        resultSingleService.approve(de3.getId());
    }

    public void createRepresentatives(){
        try {
            FileInputStream fis = new FileInputStream(new File("test-csv/big/names.txt"));
            InputStreamReader isr = new InputStreamReader(fis);
            final BufferedReader bufferedReader = new BufferedReader(isr);
            final List<String> names = bufferedReader.lines().collect(Collectors.toList());
            final List<DistrictEntity> districtRepositoryAll = districtRepository.findAll();
            for (int i = 0; i < districtRepositoryAll.size(); i++) {
                String name = names.get(new Random().nextInt(998)+1);
                String surname = names.get(new Random().nextInt(998)+1) + "as";
                saveRep(districtRepositoryAll.get(i),name,surname);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private DistrictRepresentativeEntity saveRep(DistrictEntity des, String name, String surname) {
        DistrictRepresentativeEntity rep = new DistrictRepresentativeEntity();
        rep.setName(name);
        rep.setSurname(surname);
        rep.setDistrict(des);
        districtRepresentativeService.save(rep);
        return rep;
    }

    private DistrictEntity saveDistrict(CountyEntity ces, String name, String adress ) {
        DistrictEntity de = new DistrictEntity();
        de.setName(name);
        de.setCounty(ces);
        de.setAdress(adress);
        de.setNumberOfElectors(5000L);
        return districtService.save(de);
    }

    public void loadParties(){
        final MultipartFile file1 = parseToMultiPart("test-csv/big/big-party-100.csv");
        partyService.save("Balvanu Partija", 1, file1);

        final MultipartFile file2 = parseToMultiPart("test-csv/big/p50-1.csv");
        partyService.save("Ereliu Partija", 2, file2);

        final MultipartFile file3 = parseToMultiPart("test-csv/big/p50-2.csv");
        partyService.save("IT Partija", 3, file3);

        final MultipartFile file4 = parseToMultiPart("test-csv/big/p50-3.csv");
        partyService.save("Feministai", 4, file4);

        final MultipartFile file5 = parseToMultiPart("test-csv/big/p50-4.csv");
        partyService.save("Monopolistai", 5, file5);

        final MultipartFile file6 = parseToMultiPart("test-csv/big/p50-5.csv");
        partyService.save("Kedainių", 6, file6);

        final MultipartFile file7 = parseToMultiPart("test-csv/big/p50-6.csv");
        partyService.save("Harley Davidson", 7, file7);

        final MultipartFile file8 = parseToMultiPart("test-csv/big/p50-7.csv");
        partyService.save("Kolūkininkai", 8, file8);

        final MultipartFile file9 = parseToMultiPart("test-csv/big/p50-8.csv");
        partyService.save("Šventųjų Matronų partija", 9, file9);

        final MultipartFile file10 = parseToMultiPart("test-csv/big/p50-9.csv");
        partyService.save("Laisvės Partija", 10, file10);



    }

    private void createCandidate(CountyEntity ces, String name, String surname, String desc) {
        CandidateEntity can = new CandidateEntity();
        can.setBirthDate(new Date());
        can.setName(name);
        can.setSurname(surname);
        can.setDescription(desc);
        can.setCounty(ces);
        can.setMultiList(false);
        service.save(can);
    }

    private MultipartFile parseToMultiPart(String pathToFile) {
        Path path = Paths.get(pathToFile);
        String name = path.getFileName().toFile().getName();
        String originalFileName = path.getFileName().toFile().getName();
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    public void createDistricts() {
        try {
            FileInputStream fis = new FileInputStream(new File("test-csv/big/districts.txt"));
            InputStreamReader isr = new InputStreamReader(fis);
            final BufferedReader bufferedReader = new BufferedReader(isr);
            final List<String> districtsFromFile = bufferedReader.lines().collect(Collectors.toList());
            for (int i = 0; i < districtsFromFile.size(); i++) {
                DistrictEntity districtEntity = new DistrictEntity();
                List<CountyEntity> allCounties = countyRepository.findAll();

                Long index = (long)((i / 28.22));
                if (index >= 71) {
                    index = ((long) new Random().nextInt(70));
                }
                districtEntity.setName(districtsFromFile.get(i));
                districtEntity.setCounty(allCounties.get(index.intValue()));
                districtEntity.setNumberOfElectors(new Random().nextInt(5000)+1000L);
                districtEntity.setAdress("Adresas g. 1");
                districtService.save(districtEntity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void votesSingle() {
        List<CandidateEntity> candidatesList = candidateRepository.getCandidatesList();
        List<DistrictEntity> all = districtRepository.findAll();
        all.stream().forEach(dis->{
            if (dis.getId() != 5L){
                List<ResultSingleEntity> resultSingleEntities = new ArrayList<>();
                CandidateEntity spc = new CandidateEntity();
                spc.setId(-1991L);
                ResultSingleEntity spoiled = new ResultSingleEntity();
                spoiled.setCandidate(spc);
                spoiled.setVotes(10L);
                spoiled.setDistrict(dis);
                resultSingleEntities.add(spoiled);
                for (int i = 0; i < 10; i++) {
                    CandidateEntity candidateEntity = candidatesList.get(new Random().nextInt(candidatesList.size()));
                    ResultSingleEntity res = new ResultSingleEntity();
                    res.setDistrict(dis);
                    res.setCandidate(candidateEntity);
                    Integer round = Math.abs(Math.round(new Random().nextInt(500) * new Random().nextFloat() * new Random().nextFloat()));
                    res.setVotes(round.longValue());
                    res.setApproved(true);
                    resultSingleEntities.add(res);
                    res.setDatePublished(new Date());
                }
                resultSingleService.save(resultSingleEntities);
            } else {
                for (int i = 0; i < 10; i++) {
                    CandidateEntity candidateEntity = candidatesList.get(new Random().nextInt(candidatesList.size()));
                    candidateEntity.setCounty(dis.getCounty());
                    candidateService.save(candidateEntity);
                }
            }

        });

    }
}

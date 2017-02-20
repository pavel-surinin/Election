package lt.itakademija;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.CountyEntity;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    public void loadCandidates(){
        CountyEntity ce = new CountyEntity();
        ce.setName("Stebuklų šalis");
        final CountyEntity ces = countyService.save(ce);
        DistrictEntity de = saveDistrict(ces, "Maža" , "Po tiltu");
        createRepresentative(de, "Burtininkas", "Antanas");
        DistrictEntity de2 = saveDistrict(ces, "Raudona" , "Nosies g. 3");
        createRepresentative(de2, "Jiezus", "Marija");
        DistrictEntity de3 = saveDistrict(ces, "Mėlyna" , "Pijokų g. 40");
        createRepresentative(de3, "Piktas", "Žmogėnas");


        createCandidate(ces,"Vardas","Pavarde","Lirika");
        createCandidate(ces,"Osvaldas","Rimkus","Lirika");
        createCandidate(ces,"Marekas","Testeris","Lirika");
        createCandidate(ces,"Vytautas","Linkus","Lirika");
        createCandidate(ces,"Pavel","Surinin","Lirika");
        createCandidate(ces,"Gabriele","Seliunaite","Lirika");

//        districtRepository.findAll().stream().forEach(d-> votesInDistrict(d));
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
            ResultSingleEntity res = new ResultSingleEntity();
            res.setCandidate(candidatesList.get(i));
            res.setDatePublished(new Date());
            res.setDistrict(de3);
            Integer round = Math.abs(Math.round(new Random().nextInt(500) * new Random().nextFloat() * new Random().nextFloat()));
            res.setVotes(round.longValue());
            resultSingleEntities.add(res);
        }
        resultSingleService.save(resultSingleEntities);
        resultSingleService.approve(de3.getId());
    }

    private DistrictRepresentativeEntity createRepresentative(DistrictEntity des, String name, String surname) {
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
        final MultipartFile file1 = parseToMultiPart("test-csv/data-party-1.csv");
        partyService.save("Balvanu Partija", 1, file1);

        final MultipartFile file2 = parseToMultiPart("test-csv/data-party-2.csv");
        partyService.save("Ereliu Partija", 2, file2);

        final MultipartFile file3 = parseToMultiPart("test-csv/data-party-3.csv");
        partyService.save("IT Partija", 3, file3);

        final MultipartFile file4 = parseToMultiPart("test-csv/data-party-4.csv");
        partyService.save("Feministai", 4, file4);

        final MultipartFile file5 = parseToMultiPart("test-csv/big-party-100.csv");
        partyService.save("Monopolistai", 5, file5);
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
}

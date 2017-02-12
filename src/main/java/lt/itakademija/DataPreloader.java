package lt.itakademija;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

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

    public void loadCandidates(){
        CountyEntity ce = new CountyEntity();
        ce.setName("Stebuklų šalis");
        final CountyEntity ces = countyService.save(ce);
        DistrictEntity de = new DistrictEntity();
        de.setName("Gerosios Vilties");
        de.setCounty(ces);
        de.setAdress("Tilto g. 9");
        de.setNumberOfElectors(5000L);
        final DistrictEntity des = districtService.save(de);
        createCandidate(ces,"Vardas","Pavarde","Lirika");
        createCandidate(ces,"Osvaldas","Rimkus","Lirika");
        createCandidate(ces,"Marekas","Testeris","Lirika");
        createCandidate(ces,"Vytautas","Linkus","Lirika");
        createCandidate(ces,"Pavel","Surinin","Lirika");
        createCandidate(ces,"Gabriele","Seliunaite","Lirika");
        DistrictRepresentativeEntity rep = new DistrictRepresentativeEntity();
        rep.setName("Gerasisis");
        rep.setSurname("Burtininkas");
        rep.setDistrict(des);
        districtRepresentativeService.save(rep);
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
}

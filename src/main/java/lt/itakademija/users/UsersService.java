package lt.itakademija.users;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyReport;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeReport;
import lt.itakademija.electors.representative.DistrictRepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-10.
 */
@Service
public class UsersService {

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictRepresentativeRepository districtRepresentativeRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    UserAuthentification auth;

    @Autowired
    UsersRepository repository;

    public String checkWhoIsLogged() {
        return auth.getUserLogged();
    }

    public UsersEntity findUserByUsername(UsersEntity user) {
        List<UsersEntity> userToFind = repository.findByUsername(user);
        if (userToFind == null) {
            return null;
        } else {
            return userToFind.iterator().next();
        }
    }

    public String login(UsersEntity user) {
        List<UsersEntity> resp = repository.checkUserLoginPassword(user);
        if (resp.size() == 0) {
            return "none";
        } else {
            auth.setUserLogged(resp.get(0).getUsername());
            auth.setUserDistrictId(resp.get(0).getDistrictId());
            return user.getUsername();
        }
    }

    public void logout() {
        auth.setUserLogged("none");
    }

    @Transactional
    public void saveUser(UsersEntity user) {
        repository.save(user);
    }

    public List<Long> getAdminPanelInfo() {
        List<Long> list = new ArrayList<>();
        list.add(countyRepository.getCountiesCount());
        list.add(districtRepository.getDistrictsCount());
        list.add(districtRepresentativeRepository.getDistrictRepsCount());
        list.add(partyRepository.getPartiesCount());
        list.add(candidateRepository.getCandidatesCount());
        return list;
    }

    public int checkWhoIsDistrict() {
        return auth.getUserDistrictId();
    }

    public List search(String string) {
        List<List> list = new ArrayList<>();
        List<CountyEntity> searchCounties = countyRepository.search(string);
        list.add(searchCounties.stream().map(CountyReport::new).collect(Collectors.toList()));
        List<DistrictEntity> searchDistricts = districtRepository.search(string);
        list.add(searchDistricts.stream().map(DistrictReport::new).collect(Collectors.toList()));
        List<DistrictRepresentativeEntity> searchDistrictRepresentatives = districtRepresentativeRepository.search(string);
        list.add(searchDistrictRepresentatives.stream().map(r->new DistrictRepresentativeReport(r,true)).collect(Collectors.toList()));
        List<CandidateEntity> searchCandidates = candidateRepository.search(string);
        list.add(searchCandidates.stream().map(CandidateReport::new).collect(Collectors.toList()));
        return list;
    }

    public FileInputStream getCsvResults() {
        try {
            FileInputStream file = new FileInputStream(new File("test-csv/big/names.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    return null;
    }
}

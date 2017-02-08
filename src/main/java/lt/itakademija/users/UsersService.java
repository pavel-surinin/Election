package lt.itakademija.users;

import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.representative.DistrictRepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    UsersRepository rep;

    public String checkWhoIsLogged() {
        return auth.getUserLogged();
    }

    public UsersEntity findUserByUsername(UsersEntity user){
        List<UsersEntity> userToFind = rep.findByUsername(user);
        if (userToFind == null) {
            return null;
        } else {
            return userToFind.iterator().next();
        }
    }

    public boolean login(UsersEntity user) {
        List<UsersEntity> resp = rep.checkUserLoginPassword(user);
        if (resp.size() == 0) {
            return false;
        } else {
            auth.setUserLogged(resp.get(0).getUsername());
            return true;
        }
    }

    public void logout() {
        auth.setUserLogged("none");
    }

    @Transactional
    public void saveUser(UsersEntity user){
        String hashword = Md5.generate(user.getPassword());
        user.setPassword(hashword);
        rep.save(user);
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
}

package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.exceptions.PartyNameCloneException;
import lt.itakademija.exceptions.PartyNumberCloneException;
import lt.itakademija.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class PartyService {

    @Autowired
    PartyRepository repository;

    @Autowired
    StorageService storageService;

    @Autowired
    CandidateService candidateService;

    @Transactional
    public PartyEntity save(PartyEntity party) {
        return repository.save(party);
    }

    @Transactional
    public void save(String partyName, Integer partyNumber, MultipartFile file) {
        List<CandidateEntity> candidatesFromCsv = storageService.store(file);
        PartyEntity party = new PartyEntity();
        party.setName(partyName);
        final List<PartyEntity> parties = repository.findAll();
        boolean isNameExists = parties.stream().anyMatch(par -> par.getName().equals(partyName));
        throwIf(isNameExists, new PartyNameCloneException("Party exists with name " + partyName));
        boolean isNumExists = parties.stream().anyMatch(par -> par.getPartyNumber() == partyNumber);
        throwIf(isNumExists, new PartyNumberCloneException("Party exists with number " + partyNumber));
        party.setPartyNumber(partyNumber);
        PartyEntity createPartyResponse = repository.save(party);
        candidatesFromCsv.stream()
                .map(can -> {
                    can.setPartyDependencies(createPartyResponse);
                    return can;
                }).forEach(can -> candidateService.save(can));
    }

    public List<PartyReport> getPartyList() {
        List<PartyReport> list = new ArrayList<>();
        for (PartyEntity party : repository.findAll()) {
            PartyReport report = new PartyReport();
            report.setId(party.getId());
            report.setName(party.getName());
            report.setPartyNumber(party.getPartyNumber());
            List<PartyEntity> narysIdList = new ArrayList<>();
            list.add(report);
        }
        return list;
    }


    public PartyReport getPartyById(Long id) {
        PartyEntity party = repository.getById(id);
        PartyReport pr = new PartyReport();
        pr.setId(party.getId());
        pr.setName(party.getName());
        pr.setPartyNumber(party.getPartyNumber());
        List<CandidateReport> members = party.getMembers()
                .stream()
                .map(c -> {
                    CandidateReport cr = new CandidateReport();
                    cr.setId(c.getId());
                    cr.setName(c.getName());
                    cr.setSurname(c.getSurname());
                    cr.setBirthDate(c.getBirthDate());
                    cr.setDescription(c.getDescription());
                    cr.setNumberInParty(c.getNumberInParty());
                    return cr;
                })
                .collect(Collectors.toList());
        pr.setMembers(members);
        return pr;
    }

    public PartyEntity getPartyEntityById(Long id) {
        return repository.getById(id);
    }

    public void detach(PartyEntity partyEntity) {
        repository.detach(partyEntity);
    }

    private void throwIf(boolean condition, RuntimeException exception) {
        if (condition) {
            throw exception;
        }
    }
}

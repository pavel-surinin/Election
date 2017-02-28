package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.exceptions.PartyNameCloneException;
import lt.itakademija.exceptions.PartyNumberCloneException;
import lt.itakademija.storage.CSVParser;
import lt.itakademija.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class PartyService {

    @Autowired
    CSVParser reader;

    @Autowired
    PartyRepository repository;

    @Autowired
    StorageService storageService;

    @Autowired
    CandidateService candidateService;

    @Transactional
    public PartyEntity save(PartyEntity party) {
        final PartyEntity partyBeforeUpdate = repository.getById(party.getId());
        partyBeforeUpdate.setName(party.getName());
        partyBeforeUpdate.setPartyNumber(party.getPartyNumber());
        return repository.save(partyBeforeUpdate);
    }

    @Transactional
    public void save(Long partyId, String partyName, Integer partyNumber, MultipartFile file) {
        List<CandidateEntity> candidatesFromCsv = storageService.store("Party", file);
        PartyEntity party = repository.getById(partyId);
        final List<PartyEntity> allParties = repository.findAll();
        throwIfNameExists(partyName, party, allParties);
        throwIfNumberExists(partyNumber, party, allParties);
        party.setName(partyName);
        party.setPartyNumber(partyNumber);
        final PartyEntity saveParty = repository.save(party);
        candidatesFromCsv
                .stream()
                .map(c -> {
                    c.setPartyDependencies(saveParty);
                    return c;
                }).forEach(c -> candidateService.save(c));

    }

    @Transactional
    public void save(String partyName, Integer partyNumber, MultipartFile file) {
        List<CandidateEntity> candidatesFromCsv = storageService.store("Party", file);
        PartyEntity party = new PartyEntity();
        throwIf(repository.isNameExists(partyName), new PartyNameCloneException("Party exists with name " + partyName));
        throwIf(repository.isNumberExists(partyNumber), new PartyNumberCloneException("Party exists with number " + partyNumber));
        party.setName(partyName);
        party.setPartyNumber(partyNumber);
        PartyEntity createPartyResponse = repository.save(party);
        candidatesFromCsv.stream()
                .map(can -> {
                    can.setPartyDependencies(createPartyResponse);
                    return can;
                }).forEach(can -> candidateService.save(can));
    }

    public PartyEntity getPartyByNumber(Integer number) {
        return repository.getPartyByNumber(number);
    }

    public List<PartyReport> getPartyList() {
        return repository.findAll().stream().map(PartyReport::new).collect(Collectors.toList());
    }

    public PartyReport getPartyById(Long id) {
        PartyEntity party = repository.getById(id);
        return new PartyReport(party);
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

    private boolean throwIfNumberExists(Integer partyNumber, PartyEntity party, List<PartyEntity> allParties) {
        for (PartyEntity par : allParties) {
            if (par.getPartyNumber() == partyNumber) {
                if ((party.getId() != par.getId())) {
                    throw new PartyNumberCloneException("Party exists with number " + partyNumber);
                }
            }
        }
        return false;
    }

    private boolean throwIfNameExists(String partyName, PartyEntity party, List<PartyEntity> allParties) {
        for (PartyEntity par : allParties) {
            if (par.getName().equals(partyName)) {
                if ((party.getId() != par.getId())) {
                    throw new PartyNameCloneException("Party exists with name " + partyName);
                }
            }
        }
        return false;
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    public String getNameById(Long id) {
        return repository.getById(id).getName();
    }
}

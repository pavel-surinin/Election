package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateReport;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Pavel on 2017-01-12.
 */
public class PartyReport {

    private PartyEntity pe;

    private Long id;

    private String name;

    private List<CandidateReport> members;

    private Integer partyNumber;

    public PartyReport(PartyEntity pe) {
        this.id = pe.getId();
        this.name = pe.getName();
        if (pe.getMembers() != null) {
            this.members = pe.getMembers().stream().map(CandidateReport::new).collect(Collectors.toList());
        }
        this.partyNumber = pe.getPartyNumber();
    }

    public PartyReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(Integer partyNumber) {
        this.partyNumber = partyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CandidateReport> getMembers() {
        return members;
    }

    public void setMembers(List<CandidateReport> members) {
        this.members = members;
    }
}

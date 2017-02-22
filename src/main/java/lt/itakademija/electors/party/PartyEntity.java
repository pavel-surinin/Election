package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Entity
public class PartyEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "PARTY_ID")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy="partyDependencies", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CandidateEntity> members;

    @NotNull
    @Column(unique = true)
    private Integer partyNumber;

    @OneToMany(mappedBy="party", cascade = CascadeType.REMOVE)
    private List<ResultMultiEntity> results;

    public List<ResultMultiEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultMultiEntity> results) {
        this.results = results;
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

    public List<CandidateEntity> getMembers() {
        return members;
    }

    public void setMembers(List<CandidateEntity> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "PartyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ",num of members=" + members.size() +
                '}';
    }
}

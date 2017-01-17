package lt.itakademija.electors.party;

import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
public class PartyReport {

    private Long id;

    private String name;

    private List<Long> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }
}

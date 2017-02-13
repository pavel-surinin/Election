package lt.itakademija.electors.results.reports.dto;

import lt.itakademija.electors.party.PartyEntity;

/**
 * Created by Pavel on 2017-02-13.
 */
public class PartyIntDTO {
    private PartyEntity par;
    private Integer count;

    public PartyIntDTO() {
    }

    public PartyIntDTO(PartyEntity par, Integer count) {
        this.par = par;
        this.count = count;
    }

    public PartyEntity getPar() {
        return par;
    }

    public void setPar(PartyEntity par) {
        this.par = par;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

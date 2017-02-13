package lt.itakademija.electors.results.reports.dto;

import lt.itakademija.electors.party.PartyEntity;

/**
 * Created by Pavel on 2017-02-13.
 */
public class PartyFloatDTO {
    private PartyEntity party;
    private Float percent;

    public PartyFloatDTO(PartyEntity party, Float percent) {
        this.party = party;
        this.percent = percent;
    }

    public PartyFloatDTO() {
    }

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}

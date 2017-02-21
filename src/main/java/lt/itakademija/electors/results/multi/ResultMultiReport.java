package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.results.multi.rating.RatingEntity;
import lt.itakademija.electors.results.multi.rating.RatingReport;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultMultiReport {

    private ResultMultiEntity result;
    private Long id;
    private Integer partyNumber;
    private String party;
    private Long votes;
    private List<RatingReport> rating;

    public ResultMultiReport(ResultMultiEntity result) {
        this.id = result.getId();
        this.partyNumber = result.getParty().getPartyNumber();
        this.party = result.getParty().getName();
        this.votes = result.getVotes();
        this.rating = result.getRating()
                .stream()
                .map(r -> new RatingReport(r))
                .sorted((r1,r2) -> r2.getPoints().compareTo(r1.getPoints()))
                .collect(Collectors.toList());
    }

    public ResultMultiReport() {
    }

    public Integer getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(Integer partyNumber) {
        this.partyNumber = partyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public List<RatingReport> getRating() {
        return rating;
    }

    public void setRating(List<RatingReport> rating) {
        this.rating = rating;
    }
}

package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;

import java.util.List;

/**
 * Created by Pavel on 2017-02-21.
 */
public class ResultsGeneralReport {

    private List<CandidateIntDTO> singleWinners;
    private List<CandidateReport> multiWinners;
    private List<PartyIntDTO> votesInMulti;
    private List<PartyIntDTO> mandatesPerParty;
    private List<PartyIntDTO> partiesOverMinimumLine;
    private Integer votersCount;
    private Integer votesCount;
    private Integer spoiledMulti;
    private Integer spoiledSingle;
    private Integer districtsCount;
    private Integer districtsVoted;

    public Integer getVotersCount() {
        return votersCount;
    }

    public Integer getDistrictsCount() {
        return districtsCount;
    }

    public void setDistrictsCount(Integer districtsCount) {
        this.districtsCount = districtsCount;
    }

    public Integer getDistrictsVoted() {
        return districtsVoted;
    }

    public void setDistrictsVoted(Integer districtsVoted) {
        this.districtsVoted = districtsVoted;
    }

    public void setVotersCount(Integer votersCount) {
        this.votersCount = votersCount;
    }

    public List<PartyIntDTO> getPartiesOverMinimumLine() {
        return partiesOverMinimumLine;
    }

    public void setPartiesOverMinimumLine(List<PartyIntDTO> partiesOverMinimumLine) {
        this.partiesOverMinimumLine = partiesOverMinimumLine;
    }

    public Integer getSpoiledMulti() {
        return spoiledMulti;
    }

    public void setSpoiledMulti(Integer spoiledMulti) {
        this.spoiledMulti = spoiledMulti;
    }

    public Integer getSpoiledSingle() {
        return spoiledSingle;
    }

    public void setSpoiledSingle(Integer spoiledSingle) {
        this.spoiledSingle = spoiledSingle;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public List<CandidateIntDTO> getSingleWinners() {
        return singleWinners;
    }

    public void setSingleWinners(List<CandidateIntDTO> singleWinners) {
        this.singleWinners = singleWinners;
    }

    public List<CandidateReport> getMultiWinners() {
        return multiWinners;
    }

    public void setMultiWinners(List<CandidateReport> multiWinners) {
        this.multiWinners = multiWinners;
    }

    public List<PartyIntDTO> getVotesInMulti() {
        return votesInMulti;
    }

    public void setVotesInMulti(List<PartyIntDTO> votesInMulti) {
        this.votesInMulti = votesInMulti;
    }

    public List<PartyIntDTO> getMandatesPerParty() {
        return mandatesPerParty;

    }

    public void setMandatesPerParty(List<PartyIntDTO> mandatesPerParty) {
        this.mandatesPerParty = mandatesPerParty;
    }
}

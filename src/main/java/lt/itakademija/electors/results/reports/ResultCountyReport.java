package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.county.CountyReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;

import java.util.List;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultCountyReport {

    private Integer votersCount;
    private Integer validCount;
    private CountyReport county;
    private CandidateReport singleMandateWinner;
    private List<CandidateIntDTO> votesByCandidate;
    private List<PartyIntDTO> votesByParty;
    private Integer spoiledSingle;
    private Integer spoiledMulti;
    private Integer districtsVotedSingle;
    private Integer districtsVotedMulti;
    private Integer districtsCount;
    private List<PartyIntDTO> reorderedPartyMembersParties;
    public ResultCountyReport() {
    }

    public Integer getValidCount() {
        return validCount;
    }

    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
    }

    public Integer getVotersCount() {
        return votersCount;
    }

    public void setVotersCount(Integer votersCount) {
        this.votersCount = votersCount;
    }

    public CountyReport getCounty() {
        return county;
    }

    public List<PartyIntDTO> getReorderedPartyMembersParties() {
        return reorderedPartyMembersParties;
    }

    public void setReorderedPartyMembersParties(List<PartyIntDTO> reorderedPartyMembersParties) {
        this.reorderedPartyMembersParties = reorderedPartyMembersParties;
    }

    public void setCounty(CountyReport county) {
        this.county = county;
    }

    public Integer getDistrictsCount() {
        return districtsCount;
    }

    public void setDistrictsCount(Integer districtsCount) {
        this.districtsCount = districtsCount;
    }

    public Integer getDistrictsVotedSingle() {
        return districtsVotedSingle;
    }

    public void setDistrictsVotedSingle(Integer districtsVotedSingle) {
        this.districtsVotedSingle = districtsVotedSingle;
    }

    public Integer getDistrictsVotedMulti() {
        return districtsVotedMulti;
    }

    public void setDistrictsVotedMulti(Integer districtsVotedMulti) {
        this.districtsVotedMulti = districtsVotedMulti;
    }

    public List<PartyIntDTO> getVotesByParty() {
        return votesByParty;
    }

    public void setVotesByParty(List<PartyIntDTO> votesByParty) {
        this.votesByParty = votesByParty;
    }

    public CandidateReport getSingleMandateWinner() {
        return singleMandateWinner;
    }

    public void setSingleMandateWinner(CandidateReport singleMandateWinner) {
        this.singleMandateWinner = singleMandateWinner;
    }

    public List<CandidateIntDTO> getVotesByCandidate() {
        return votesByCandidate;
    }

    public void setVotesByCandidate(List<CandidateIntDTO> votesByCandidate) {
        this.votesByCandidate = votesByCandidate;
    }

    public Integer getSpoiledSingle() {
        return spoiledSingle;
    }

    public void setSpoiledSingle(Integer spoiledSingle) {
        this.spoiledSingle = spoiledSingle;
    }

    public Integer getSpoiledMulti() {
        return spoiledMulti;
    }

    public void setSpoiledMulti(Integer spoiledMulti) {
        this.spoiledMulti = spoiledMulti;
    }
}

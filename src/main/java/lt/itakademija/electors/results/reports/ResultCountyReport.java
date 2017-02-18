package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyReport;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.party.PartyReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;
import lt.itakademija.electors.results.single.ResultSingleEntity;

import java.util.List;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultCountyReport {

    private CountyReport county;
    private List<CandidateIntDTO> candidatesVotesSummary;
    private CandidateReport singleMandateWinner;
    private List<PartyIntDTO> partiesVotesSummary;
    private Integer spoiledSingle;
    private Integer spoiledMulti;
    private Integer districtsVotedSingle;
    private Integer districtsVotedMulti;
    private Integer districtsCount;
    private List<PartyIntDTO> reorderedPartyMembersParties;
    public ResultCountyReport() {
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

    public List<PartyIntDTO> getPartiesVotesSummary() {
        return partiesVotesSummary;
    }

    public void setPartiesVotesSummary(List<PartyIntDTO> partiesVotesSummary) {
        this.partiesVotesSummary = partiesVotesSummary;
    }

    public CandidateReport getSingleMandateWinner() {
        return singleMandateWinner;
    }

    public void setSingleMandateWinner(CandidateReport singleMandateWinner) {
        this.singleMandateWinner = singleMandateWinner;
    }

    public List<CandidateIntDTO> getCandidatesVotesSummary() {
        return candidatesVotesSummary;
    }

    public void setCandidatesVotesSummary(List<CandidateIntDTO> candidatesVotesSummary) {
        this.candidatesVotesSummary = candidatesVotesSummary;
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

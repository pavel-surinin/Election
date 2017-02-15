package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;
import lt.itakademija.electors.results.single.ResultSingleEntity;

import java.util.List;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultCountyReport {

    private List<CandidateIntDTO> candidatesVotesSummary;
    private CandidateReport singleMandateWinner;
    private List<PartyIntDTO> partiesVotesSummary;
    private Integer spoiledSingle;
    private Integer spoiledMulti;

    public ResultCountyReport() {
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

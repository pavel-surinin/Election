package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import lt.itakademija.electors.results.multi.ResultMultiReport;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultDistrictReport {

    private Integer voters;
    private Integer valid;
    private List<ResultMultiReport> votesByParty;
    private List<ResultSingleReport> votesByCandidate;
    private Integer spoiledSingle;
    private Integer spoiledMulti;
    private String registrationSingle;
    private String registrationMulti;

    public ResultDistrictReport(final DistrictEntity district) {
        final List<ResultMultiEntity> resultsMulti = district.getResultMultiEntity();
        final List<ResultSingleEntity> resultSingle = district.getResultSingleEntity();
        this.voters = district.getNumberOfElectors().intValue();
        if (resultsMulti.size() != 0) {
            this.votesByParty = resultsMulti
                    .stream()
                    .map(r -> new ResultMultiReport(r))
                    .sorted((r1,r2) -> r2.getVotes().compareTo(r1.getVotes()))
                    .collect(Collectors.toList());
            this.valid = resultsMulti.stream().mapToInt(r -> r.getVotes().intValue()).sum() + district.getSpoiledMulti();
            this.registrationMulti = formatDateToString(resultsMulti.get(0).getDatePublished());
        }
        if (resultSingle.size() != 0) {
            this.votesByCandidate = resultSingle
                .stream()
                .map(r -> new ResultSingleReport(r))
                .sorted((r1,r2) -> r2.getVotes().compareTo(r1.getVotes()))
                .collect(Collectors.toList());
            this.registrationSingle = formatDateToString(resultSingle.get(0).getDatePublished());
        }
        this.spoiledMulti = district.getSpoiledMulti();
        this.spoiledSingle = district.getSpoiledSingle();
    }

    public ResultDistrictReport() {
    }

    public String getRegistrationSingle() {
        return registrationSingle;
    }

    public void setRegistrationSingle(String registrationSingle) {
        this.registrationSingle = registrationSingle;
    }

    public String getRegistrationMulti() {
        return registrationMulti;
    }

    public void setRegistrationMulti(String registrationMulti) {
        this.registrationMulti = registrationMulti;
    }

    public Integer getSpoiledMulti() {
        return spoiledMulti;
    }

    public void setSpoiledMulti(Integer spoiledMulti) {
        this.spoiledMulti = spoiledMulti;
    }

    public Integer getVoters() {
        return voters;
    }

    public void setVoters(Integer voters) {
        this.voters = voters;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public List<ResultMultiReport> getVotesByParty() {
        return votesByParty;
    }

    public void setVotesByParty(List<ResultMultiReport> votesByParty) {
        this.votesByParty = votesByParty;
    }

    public List<ResultSingleReport> getVotesByCandidate() {
        return votesByCandidate;
    }

    public void setVotesByCandidate(List<ResultSingleReport> votesByCandidate) {
        this.votesByCandidate = votesByCandidate;
    }

    public Integer getSpoiledSingle() {
        return spoiledSingle;
    }

    public void setSpoiledSingle(Integer spoiledSingle) {
        this.spoiledSingle = spoiledSingle;
    }

    private String formatDateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
}

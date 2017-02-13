package lt.itakademija.electors.results.reports;

import lt.itakademija.electors.results.reports.dto.CandidateFloatDTO;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyFloatDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;

import java.util.List;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultGeneralReport {


    private Integer usedBallots;
    private Float votersActivity;
    private List<PartyIntDTO> numberValidByParty;
    private List<PartyFloatDTO>  percentsByPartyOffValid;
    private List<PartyFloatDTO> percentsByPartyOffAll;
    private List<CandidateIntDTO> numberValidByCandidate;
    private List<CandidateFloatDTO> percentsByCandidateOffValid;
    private List<CandidateFloatDTO> numberByCandidateOffValid;
    private Integer spoiled;
}

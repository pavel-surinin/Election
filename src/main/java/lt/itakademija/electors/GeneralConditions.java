package lt.itakademija.electors;

import lt.itakademija.users.UsersEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 2017-02-22.
 */
public class GeneralConditions {
    //delete before production
    private static Float minimumPercentInMulti = 5.0F;
    private static Integer numberOfCandidatesInParlament = 141;
    private static boolean isAllCountiesVoted = false;
    private static Integer multiGetMandates = 70;

    public static void setIsAllCountiesVoted(boolean isAllCountiesVoted) {
        GeneralConditions.isAllCountiesVoted = isAllCountiesVoted;
    }

    public static Integer getMultiGetMandates() {
        return multiGetMandates;
    }

    public static Integer countMandates(Float percent){
        return Math.round(percent * multiGetMandates);
    }

    public static Float getMinimumPercentInMulti() {
        return minimumPercentInMulti;
    }

    public static Integer getNumberOfCandidatesInParlament() {
        return numberOfCandidatesInParlament;
    }

    public static boolean isIsAllCountiesVoted() {
        return isAllCountiesVoted;
    }
}

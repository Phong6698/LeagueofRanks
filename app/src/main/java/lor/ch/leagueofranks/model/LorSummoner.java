package lor.ch.leagueofranks.model;

import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.Stats.PlayerStatsSummaryList;
import net.rithms.riot.dto.Summoner.Summoner;

import java.util.List;

/**
 * Created by phong on 13.06.2017.
 */

public class LorSummoner {

    private Summoner summoner;
    private PlayerStatsSummaryList playerStatsSummaryList;
    private List<League> leagues;

    /**
     *  For sorting
     */
    private int rankPoints = 0;//there are 28 Division whereUnranked is 0 and Challanger is 27 for example Silver 3 is 8
    private int leaguePoints = 0;
    private int wins = 0;
    private int winRate = 0;
    private int games = 0;

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public PlayerStatsSummaryList getPlayerStatsSummaryList() {
        return playerStatsSummaryList;
    }

    public void setPlayerStatsSummaryList(PlayerStatsSummaryList playerStatsSummaryList) {
        this.playerStatsSummaryList = playerStatsSummaryList;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWinRate() {
        return winRate;
    }

    public void setWinRate(int winRate) {
        this.winRate = winRate;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }
}

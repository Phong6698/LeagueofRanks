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

}

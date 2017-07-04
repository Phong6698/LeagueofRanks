package lor.ch.leagueofranks.task;

import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 30.06.2017.
 */

public class SummonerSorter {

    private static final String LOG_TAG = SummonerSorter.class.getCanonicalName();
    private Boolean isFlex;
    private HashMap ranks;

    public SummonerSorter(Boolean isFlex) {
        this.isFlex = isFlex;
        ranks = new HashMap();
        ranks.put("Unranked", 0);
        ranks.put("BRONZE V", 1);
        ranks.put("BRONZE IV", 2);
        ranks.put("BRONZE III", 3);
        ranks.put("BRONZE II", 4);
        ranks.put("BRONZE I", 5);
        ranks.put("SILVER V", 6);
        ranks.put("SILVER IV", 7);
        ranks.put("SILVER III", 8);
        ranks.put("SILVER II", 9);
        ranks.put("SILVER I", 10);
        ranks.put("GOLD V", 11);
        ranks.put("GOLD IV", 12);
        ranks.put("GOLD III", 13);
        ranks.put("GOLD II", 14);
        ranks.put("GOLD I", 15);
        ranks.put("PLATINUM V", 16);
        ranks.put("PLATINUM IV", 17);
        ranks.put("PLATINUM III", 18);
        ranks.put("PLATINUM II", 19);
        ranks.put("PLATINUM I", 20);
        ranks.put("DIAMOND V", 21);
        ranks.put("DIAMOND IV", 22);
        ranks.put("DIAMOND III", 23);
        ranks.put("DIAMOND II", 24);
        ranks.put("DIAMOND I", 25);
        ranks.put("MASTER I", 26);
        ranks.put("CHALLENGER", 27);
    }

    public SummonerSorter() {    }

    public LeagueEntry getLeagueEntry(LorSummoner lorSummoner, League league){
        LeagueEntry leagueEntry = null;
        if (this.isFlex) {
            if (league.getQueue().equals("RANKED_FLEX_SR")) {
                for (LeagueEntry leagueEntryItem : league.getEntries()) {
                    if (leagueEntryItem.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                        leagueEntry = leagueEntryItem;
                        break;
                    }
                }
            }
        } else {
            if (league.getQueue().equals("RANKED_SOLO_5x5")) {
                for (LeagueEntry leagueEntryItem : league.getEntries()) {
                    if (leagueEntryItem.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                        leagueEntry = leagueEntryItem;
                        break;
                    }
                }
            }
        }

        return leagueEntry;
    }

    private int getNormalWins(LorSummoner lorSummoner) {
        int wins = 0;
        for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){
            if(playerStatsSummary.getPlayerStatSummaryType().equals("Unranked")){
                wins = playerStatsSummary.getWins();
                break;
            }
        }
        return wins;
    }

    private ArrayList<Integer> getRankPoints(LorSummoner lorSummoner){
        ArrayList<Integer> points = new ArrayList<>();
        int rankPoints = 0;
        int leaguePoints = 0;
        LeagueEntry leagueEntry;
        //If Leagues is not emtpy
        if(lorSummoner.getLeagues() != null) {
            for (League league : lorSummoner.getLeagues()) {
                leagueEntry = getLeagueEntry(lorSummoner, league);
                if (leagueEntry != null) {
                    String division = league.getTier() + " " + leagueEntry.getDivision();
                    rankPoints = (int)ranks.get(division);
                    leaguePoints = leagueEntry.getLeaguePoints();
                    break;
                }
            }
        }
        points.add(rankPoints);
        points.add(leaguePoints);
        return points;
    }

    private ArrayList<Integer> getRankedWinsLosses(LorSummoner lorSummoner){
        ArrayList<Integer> rankedWinsLosses = new ArrayList<>();
        int wins = 0;
        int losses = 0;
        LeagueEntry leagueEntry;
        //If Leagues is not emtpy
        if(lorSummoner.getLeagues() != null) {
            for (League league : lorSummoner.getLeagues()) {
                leagueEntry = getLeagueEntry(lorSummoner, league);
                if (leagueEntry != null) {
                    wins = leagueEntry.getWins();
                    losses = leagueEntry.getLosses();
                    break;
                }else{
                    for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()) {
                        if(isFlex){
                            if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedFlexSR")) {
                                wins = playerStatsSummary.getWins();
                                losses = playerStatsSummary.getLosses();
                                break;
                            }
                        }else{
                            if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                                wins = playerStatsSummary.getWins();
                                losses = playerStatsSummary.getLosses();
                                break;
                            }
                        }
                    }
                }
            }
        }else{
            for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()) {
                if(isFlex){
                    if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedFlexSR")) {
                        wins = playerStatsSummary.getWins();
                        losses = playerStatsSummary.getLosses();
                        break;
                    }
                }else{
                    if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                        wins = playerStatsSummary.getWins();
                        losses = playerStatsSummary.getLosses();
                        break;
                    }
                }

            }
        }
        rankedWinsLosses.add(wins);
        rankedWinsLosses.add(losses);
        return rankedWinsLosses;
    }

    public ArrayList<LorSummoner> sortRankedRank(ArrayList<LorSummoner> lorSummoners){
        for(LorSummoner lorSummoner : lorSummoners){
            lorSummoner.setRankPoints(getRankPoints(lorSummoner).get(0));
            lorSummoner.setLeaguePoints(getRankPoints(lorSummoner).get(1));
        }
        Collections.sort(lorSummoners, new Comparator<LorSummoner>() {
            @Override
            public int compare(LorSummoner lorSummoner1, LorSummoner lorSummoner2) {
                int summonerComparison = new Integer(lorSummoner1.getRankPoints()).compareTo(lorSummoner2.getRankPoints());
                return summonerComparison == 0 ? new Integer(lorSummoner1.getLeaguePoints()).compareTo(lorSummoner2.getLeaguePoints()) : summonerComparison;
            }
        });
        Collections.reverse(lorSummoners);
        return lorSummoners;
    }

    public ArrayList<LorSummoner> sortRankedWins(ArrayList<LorSummoner> lorSummoners){
        for(LorSummoner lorSummoner : lorSummoners){
            lorSummoner.setWins(getRankedWinsLosses(lorSummoner).get(0));
        }
        Collections.sort(lorSummoners, new Comparator<LorSummoner>() {
            @Override
            public int compare(LorSummoner lorSummoner1, LorSummoner lorSummoner2) {
                return new Integer(lorSummoner1.getWins()).compareTo(lorSummoner2.getWins());
            }
        });
        Collections.reverse(lorSummoners);
        return lorSummoners;
    }

    public ArrayList<LorSummoner> sortRankedWinRate(ArrayList<LorSummoner> lorSummoners){
        for(LorSummoner lorSummoner : lorSummoners){

            double wins = getRankedWinsLosses(lorSummoner).get(0);
            double losses = getRankedWinsLosses(lorSummoner).get(1);
            double winRate = (wins / (wins + losses)) * 100;
            lorSummoner.setWinRate((int)Math.round(winRate));
        }
        Collections.sort(lorSummoners, new Comparator<LorSummoner>() {
            @Override
            public int compare(LorSummoner lorSummoner1, LorSummoner lorSummoner2) {
                return new Integer(lorSummoner1.getWinRate()).compareTo(lorSummoner2.getWinRate());
            }
        });
        Collections.reverse(lorSummoners);
        return lorSummoners;
    }

    public ArrayList<LorSummoner> sortRankedGames(ArrayList<LorSummoner> lorSummoners){
        for(LorSummoner lorSummoner : lorSummoners){
            lorSummoner.setGames(getRankedWinsLosses(lorSummoner).get(0)+ getRankedWinsLosses(lorSummoner).get(1));
        }
        Collections.sort(lorSummoners, new Comparator<LorSummoner>() {
            @Override
            public int compare(LorSummoner lorSummoner1, LorSummoner lorSummoner2) {
                return new Integer(lorSummoner1.getGames()).compareTo(lorSummoner2.getGames());
            }
        });
        Collections.reverse(lorSummoners);
        return lorSummoners;
    }

    public ArrayList<LorSummoner> sortNormalWins(ArrayList<LorSummoner> lorSummoners){
        for(LorSummoner lorSummoner : lorSummoners){
            lorSummoner.setWins(getNormalWins(lorSummoner));
        }
        Collections.sort(lorSummoners, new Comparator<LorSummoner>() {
            @Override
            public int compare(LorSummoner lorSummoner1, LorSummoner lorSummoner2) {
                return new Integer(lorSummoner1.getWins()).compareTo(lorSummoner2.getWins());
            }
        });
        Collections.reverse(lorSummoners);
        return lorSummoners;
    }
}

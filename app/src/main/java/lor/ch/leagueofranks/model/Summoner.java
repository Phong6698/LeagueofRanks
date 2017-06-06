package lor.ch.leagueofranks.model;

import java.util.ArrayList;


public class Summoner {
    private String name;
    private int summonerId;
    private int accountId;
    private int summonerLevel;
    private int profileIconId;
    private String region = "euw";
    private int wins;
    private ArrayList<SummonerRanked> summonerRankeds = new ArrayList<>();



    public ArrayList<SummonerRanked> getSummonerRankeds() {
        return summonerRankeds;
    }

    public void setSummonerRankeds(ArrayList<SummonerRanked> summonerRankeds) {
        this.summonerRankeds = summonerRankeds;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(int summonerId) {
        this.summonerId = summonerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

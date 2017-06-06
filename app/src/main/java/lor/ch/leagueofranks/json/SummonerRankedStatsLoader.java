package lor.ch.leagueofranks.json;

import android.app.Activity;
import android.app.ProgressDialog;

import java.net.MalformedURLException;
import java.net.URL;

import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.model.Summoner;

public class SummonerRankedStatsLoader extends JsonLoadingTask{

    private Summoner summoner;
    private SummonerProfileActivity summonerProfileActivity;

    public SummonerRankedStatsLoader(Activity activity, ProgressDialog mDialog, Summoner summoner) {
        super(activity, mDialog);
        this.summoner = summoner;
        this.summonerProfileActivity = (SummonerProfileActivity) activity;
    }

    @Override
    protected void onCostumPostExecute(String jsonString) {

        summoner = jsonParser.getSummonerRankedStats(jsonString, summoner);

        summonerProfileActivity.onData(summoner);


    }

    @Override
    protected URL createURL(String... params) {
        String summonerId = params[0];
        String region = params[1] + 1; //+1 = EUW1
        URL url = null;
        try {
            url = new URL("https://"+region.toLowerCase()+".api.riotgames.com/lol/league/v3/positions/by-summoner/" + summonerId + "/entry?api_key=58453580-a12b-497a-bdde-d1255bd0fda3");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}

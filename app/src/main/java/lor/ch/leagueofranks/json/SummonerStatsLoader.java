package lor.ch.leagueofranks.json;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.model.Summoner;

public class SummonerStatsLoader extends JsonLoadingTask {

    private URL url;

    private Summoner summoner;
    private SummonerProfileActivity summonerProfileActivity;
    private String region;

    public SummonerStatsLoader(Activity activity, ProgressDialog mDialog, Summoner summoner) {
        super(activity, mDialog);
        this.summoner = summoner;
        this.summonerProfileActivity = (SummonerProfileActivity) activity;
    }

    @Override
    protected void onCostumPostExecute(String jsonString) {
        if(jsonString==null){
            mDialog.dismiss();
            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show();
        }
        summoner = jsonParser.getSummonerWins(jsonString, this.summoner);

        int summonerId = summoner.getSummonerId();
        if(summoner.getSummonerLevel() == 30) {

            SummonerRankedStatsLoader summonerRankedStatsLoader = new SummonerRankedStatsLoader(activity, mDialog, summoner);
            summonerRankedStatsLoader.execute(""+summonerId, region);
        }else if(summoner.getSummonerLevel() != 30){
            summonerProfileActivity.onData(summoner);
        }


    }

    @Override
    protected URL createURL(String... params) {
        String summonerId = params[1];
        String region = params[0];
        this.region = region;
        URL url = null;
        try {
            url = new URL("https://"+region.toLowerCase()+".api.riotgames.com/api/lol/"+region.toLowerCase()+"/v1.3/stats/by-summoner/"+summonerId+"/summary?season=SEASON2017&api_key=58453580-a12b-497a-bdde-d1255bd0fda3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}

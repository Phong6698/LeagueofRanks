package lor.ch.leagueofranks.task;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;
import net.rithms.riot.dto.Summoner.Summoner;

import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.model.LorSummoner;

public class LoadingSummonerTask extends AsyncTask<String, Void, LorSummoner> {

    private static final String LOG_TAG = LoadingSummonerTask.class.getCanonicalName();

    private ConnectivityManager connectivityManager;
    protected SummonerProfileActivity summonerProfileActivity;


    public LoadingSummonerTask(SummonerProfileActivity summonerProfileActivity) {
        this.summonerProfileActivity = summonerProfileActivity;
    }

    @Override
    protected LorSummoner doInBackground(String... params) {

        LorSummoner lorSummoner = new LorSummoner();

        if(isNetworkConnectionAvailable()) {
            try {
                RiotApi api = new RiotApi("");

                api.setRegion(Region.EUW);
                lorSummoner.setSummoner(api.getSummonerByName(params[0]));
                long id = lorSummoner.getSummoner().getId();
                lorSummoner.setPlayerStatsSummaryList(api.getPlayerStatsSummary(id));
                lorSummoner.setLeagues(api.getLeagueBySummoner(id));


            } catch (RiotApiException e){
                e.getStackTrace();
            }
        }


        return lorSummoner;
    }



    @Override
    protected void onPostExecute(LorSummoner lorSummoner) {
        summonerProfileActivity.onData(lorSummoner);
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) summonerProfileActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();

        return null != networkInfo && networkInfo.isConnected();
    }


    public ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public void setConnectivityManager(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

}

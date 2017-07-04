package lor.ch.leagueofranks.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;
import net.rithms.riot.dto.Summoner.Summoner;

import lor.ch.leagueofranks.SearchSummonerActivity;
import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.model.LorSummoner;

public class LoadingSummonerTask extends AsyncTask<String, Void, LorSummoner> {

    private static final String LOG_TAG = LoadingSummonerTask.class.getCanonicalName();

    private ConnectivityManager connectivityManager;
    protected SummonerProfileActivity summonerProfileActivity;
    protected SearchSummonerActivity searchSummonerActivity;


    public LoadingSummonerTask(SearchSummonerActivity searchSummonerActivity, SummonerProfileActivity summonerProfileActivity) {
        this.summonerProfileActivity = summonerProfileActivity;
        this.searchSummonerActivity = searchSummonerActivity;
    }

    @Override
    protected LorSummoner doInBackground(String... params) {

        LorSummoner lorSummoner = new LorSummoner();
        RiotApi api = new RiotApi("RGAPI-10164e1d-0ecf-46ae-bfac-f61149503b89");
        api.setRegion(Region.EUW);

        if(isNetworkConnectionAvailable()) {
            try {
                Summoner summoner = api.getSummonerByName(params[0]);
                lorSummoner.setSummoner(summoner);
            }catch(RiotApiException e){
                return null;
            }

            try {
                lorSummoner.setSummoner(api.getSummonerByName(params[0]));
                long id = lorSummoner.getSummoner().getId();
                lorSummoner.setPlayerStatsSummaryList(api.getPlayerStatsSummary(id));
                lorSummoner.setLeagues(api.getLeagueBySummoner(id));



            } catch (RiotApiException e){
               e.printStackTrace();
            }
        }


        return lorSummoner;
    }



    @Override
    protected void onPostExecute(LorSummoner lorSummoner) {
        if (lorSummoner == null) {
            Log.e(LOG_TAG, "Summoner not Found");
            Intent intent = new Intent(summonerProfileActivity, SearchSummonerActivity.class);
            Toast toast = Toast.makeText(summonerProfileActivity, "Summoner not Found", Toast.LENGTH_SHORT);
            toast.show();
            summonerProfileActivity.startActivity(intent);

        }else{
            summonerProfileActivity.onData(lorSummoner);
        }


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

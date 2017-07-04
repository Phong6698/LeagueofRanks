package lor.ch.leagueofranks.task;

import android.app.Activity;
import android.app.Dialog;
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

import lor.ch.leagueofranks.R;
import lor.ch.leagueofranks.SearchSummonerActivity;
import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.SummonersActivity;
import lor.ch.leagueofranks.model.LorSummoner;

public class LoadingSummonerTask extends AsyncTask<String, Void, LorSummoner> {

    private static final String LOG_TAG = LoadingSummonerTask.class.getCanonicalName();

    private ConnectivityManager connectivityManager;
    protected SummonerProfileActivity summonerProfileActivity;
    protected SummonersActivity summonersActivity;
    protected Dialog mDialog;
    protected int countFavSum;

    public LoadingSummonerTask(SummonerProfileActivity summonerProfileActivity, Dialog mDialog) {
        this.summonerProfileActivity = summonerProfileActivity;
        this.mDialog = mDialog;
    }

    public LoadingSummonerTask(SummonersActivity summonersActivity, int countFavSum) {
        this.summonersActivity = summonersActivity;
        this.countFavSum = countFavSum;
    }

    @Override
    protected LorSummoner doInBackground(String... params) {
        int timePause = 1;
        if(countFavSum > 2){
            timePause = 3;
        }

        LorSummoner lorSummoner = new LorSummoner();
        String apiKey = "RGAPI-10164e1d-0ecf-46ae-bfac-f61149503b89";
        if(summonerProfileActivity!=null){
            apiKey = summonerProfileActivity.getResources().getString(R.string.api_key);
        }else if(summonersActivity!=null){
            apiKey = summonersActivity.getResources().getString(R.string.api_key);
        }

        RiotApi api = new RiotApi(apiKey);
        api.setRegion(Region.EUW);


        if(isNetworkConnectionAvailable()) {
            try {
                Log.e(LOG_TAG, "VERSION: "+api.getDataVersions().get(0));
                lorSummoner.setDataVersion(api.getDataVersions().get(0));
                if(summonerProfileActivity!=null){
                    Summoner summoner = api.getSummonerByName(params[0]);
                    lorSummoner.setSummoner(summoner);
                }else if(summonersActivity!=null){
                    Summoner summoner = api.getSummonerById(params[0]);
                    lorSummoner.setSummoner(summoner);
                }
                Log.e(LOG_TAG, lorSummoner.getSummoner().getName());

            }catch(RiotApiException e){
                return null;
            }

            try {
                long id = lorSummoner.getSummoner().getId();

                //Pause for API Request Limit
                try {
                    Thread.sleep(timePause * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //League
                boolean hasLeague = true;
                try{

                    lorSummoner.setLeagues(api.getLeagueBySummoner(id));
                }catch(RiotApiException e){
                    hasLeague = false;
                    Log.e(LOG_TAG, "NO LEAGUES");

                }

                if(hasLeague) {
                    //Pause for API Request Limit
                    try {
                        Thread.sleep(timePause * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //Summary
                lorSummoner.setPlayerStatsSummaryList(api.getPlayerStatsSummary(id));

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
            if(summonerProfileActivity != null){
                mDialog.dismiss();
                Intent intent = new Intent(summonerProfileActivity, SearchSummonerActivity.class);
                Toast toast = Toast.makeText(summonerProfileActivity, "Summoner not Found", Toast.LENGTH_SHORT);
                toast.show();
                summonerProfileActivity.startActivity(intent);
            }else if(summonersActivity != null){
                Toast toast = Toast.makeText(summonersActivity, "Summoner not Found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            if(summonerProfileActivity != null){
                summonerProfileActivity.onData(lorSummoner);
            }else if(summonersActivity != null){
                summonersActivity.onData(lorSummoner);
            }
        }
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = null;
        if(summonerProfileActivity != null){
            connectivityService = (ConnectivityManager) summonerProfileActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        }else if(summonersActivity != null){
            connectivityService = (ConnectivityManager) summonersActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

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

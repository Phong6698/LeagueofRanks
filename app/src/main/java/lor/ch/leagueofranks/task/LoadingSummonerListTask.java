package lor.ch.leagueofranks.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;

import java.util.ArrayList;

import lor.ch.leagueofranks.SummonersActivity;
import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 20.06.2017.
 */

public class LoadingSummonerListTask extends AsyncTask<ArrayList<Long>, Void, ArrayList<LorSummoner>> {


    private ConnectivityManager connectivityManager;
    protected SummonersActivity summonersActivity;

    public LoadingSummonerListTask(SummonersActivity summonersActivity) {
        this.summonersActivity = summonersActivity;
    }

    @Override
    protected ArrayList<LorSummoner> doInBackground(ArrayList<Long>... params) {

        ArrayList<LorSummoner> lorSummoners = new ArrayList<>();

        if(isNetworkConnectionAvailable()) {
            try {
                RiotApi api = new RiotApi("");

                api.setRegion(Region.EUW);



                for(Long summonerId : params[0]){
                    LorSummoner lorSummoner = new LorSummoner();
                    lorSummoner.setSummoner(api.getSummonerById(summonerId));
                    lorSummoner.setPlayerStatsSummaryList(api.getPlayerStatsSummary(summonerId));
                    lorSummoner.setLeagues(api.getLeagueBySummoner(summonerId));
                    lorSummoners.add(lorSummoner);
                }




            } catch (RiotApiException e){
                e.getStackTrace();
            }
        }
        return lorSummoners;
    }



    @Override
    protected void onPostExecute(ArrayList<LorSummoner> lorSummoners) {
        summonersActivity.onData(lorSummoners);
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) summonersActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
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



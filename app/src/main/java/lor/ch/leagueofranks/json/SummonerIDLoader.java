package lor.ch.leagueofranks.json;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import lor.ch.leagueofranks.model.Summoner;
import lor.ch.leagueofranks.SearchSummonerActivity;

/**
 * Created by zpengc on 18.06.2015.
 */
public class SummonerIDLoader extends JsonLoadingTask {

    private String region;
    private SearchSummonerActivity searchSummonerActivity;

    public SummonerIDLoader(Activity activity, ProgressDialog mDialog) {
        super(activity, mDialog);
        searchSummonerActivity = (SearchSummonerActivity)activity;
    }

    @Override
    protected void onCostumPostExecute(String jsonString) {
        if (null == jsonString) {
            mDialog.dismiss();
            Toast.makeText(activity, "Summoner not found", Toast.LENGTH_SHORT).show();
        } else {
            Summoner summoner = jsonParser.getSummoner(jsonString);
            summoner.setRegion(region);
            searchSummonerActivity.onData(summoner);
        }
    }

    @Override
    protected URL createURL(String... params) {
        String summonername = params[1];
        String region = params[0] + 1; //+1 = EUW1
        this.region = region;
        URL url = null;
        try {
            url = new URL("https://"+region.toLowerCase()+".api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonername.replaceAll("\\s+", "%20")+"?api_key=58453580-a12b-497a-bdde-d1255bd0fda3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}

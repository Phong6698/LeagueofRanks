package lor.ch.leagueofranks.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import lor.ch.leagueofranks.model.Summoner;
import lor.ch.leagueofranks.model.SummonerRanked;

public class JSONParser {

    private static final String LOG_TAG = JSONParser.class.getCanonicalName();

    protected static Summoner getSummoner(String jsonString) {
        Summoner summoner = new Summoner();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject subObject = jsonObject.getJSONObject(key);
                int id = subObject.getInt("id");
                int acc = subObject.getInt("accountId");
                String name = subObject.getString("name");
                int summonerLevel = subObject.getInt("summonerLevel");
                int profileIconId = subObject.getInt("profileIconId");

                summoner.setSummonerId(id);
                summoner.setAccountId(acc);
                summoner.setName(name);
                summoner.setSummonerLevel(summonerLevel);
                summoner.setProfileIconId(profileIconId);

                Log.e(LOG_TAG, "id: " + id);
                Log.e(LOG_TAG, "account id: " + acc);
                Log.e(LOG_TAG, "name: " + name);
                Log.e(LOG_TAG, "summonerLevel: " + summonerLevel);
                Log.e(LOG_TAG, "profileIconId: " + profileIconId);

            }
        } catch (JSONException e) {
            Log.v("JSONParser", e.toString());
        }
        return summoner;
    }


    protected static Summoner getSummonerWins(String jsonString, Summoner sum){
        Summoner summoner = sum;
        try {
            JSONObject jsonObj = new JSONObject(jsonString);



            ArrayList<String> listdata = new ArrayList<String>();
            JSONArray jArray = (JSONArray)jsonObj.getJSONArray("playerStatSummaries");

            int wins = 0;

            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    JSONObject unrankedObj = new JSONObject(jArray.get(i).toString());
                    if(unrankedObj.getString("playerStatSummaryType").equals("Unranked")){
                        wins = unrankedObj.getInt("wins");
                    }
                }
            }

            Log.e(LOG_TAG, "wins: " + wins);

            summoner.setWins(wins);

        } catch (JSONException e) {
            Log.v("JSONParser", e.toString());
        }
        return summoner;
    }

    protected static Summoner getSummonerRankedStats(String jsonString, Summoner summoner) {


        try {
            JSONObject jsonObj = new JSONObject(jsonString);


            JSONArray jArray = (JSONArray)jsonObj.getJSONArray(""+summoner.getSummonerId());
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    JSONObject rankedObj = new JSONObject(jArray.get(i).toString());
                    SummonerRanked summonerRanked = new SummonerRanked();

                    summonerRanked.setQueueType(rankedObj.getString("queueType"));
                    summonerRanked.setWins(rankedObj.getInt("wins"));
                    summonerRanked.setLosses(rankedObj.getInt("losses"));
                    summonerRanked.setTier(rankedObj.getString("tier"));
                    summonerRanked.setRank(rankedObj.getString("rank"));
                    summonerRanked.setLeaguePoints(rankedObj.getInt("leaguePoints"));

                    summoner.getSummonerRankeds().add(summonerRanked);

                }
            }

        } catch (JSONException e) {
            Log.v("JSONParser", e.toString());
        }
        return summoner;
    }
}

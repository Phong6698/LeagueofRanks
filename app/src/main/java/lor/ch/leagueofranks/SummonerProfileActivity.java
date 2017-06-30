package lor.ch.leagueofranks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;

import java.util.Map;

import lor.ch.leagueofranks.model.LorSummoner;
import lor.ch.leagueofranks.task.LoadingSummonerTask;

public class SummonerProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = SummonerProfileActivity.class.getCanonicalName();
    private LorSummoner lorSummoner;
    private ProgressDialog mDialog;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        mDialog = ProgressDialog.show(this, "Please wait", "Loading "+intent.getStringExtra("summonerName") +"...");

        LoadingSummonerTask loadingSummonerTask = new LoadingSummonerTask(this, mDialog);
        loadingSummonerTask.execute(intent.getStringExtra("summonerName"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    public void onData(LorSummoner lorSummoner){
        Log.e(LOG_TAG, "hello");
        this.lorSummoner = lorSummoner;

        setTitle(lorSummoner.getSummoner().getName());

        TextView normalwins = (TextView)findViewById(R.id.normalwins);
        TextView level = (TextView)findViewById(R.id.level);

        TextView solowins = (TextView)findViewById(R.id.solowins);
        TextView solorate = (TextView)findViewById(R.id.solorate);

        TextView flexwins = (TextView)findViewById(R.id.flexwins);
        TextView flexrate = (TextView)findViewById(R.id.flexrate);

        ImageView summonerIcon = (ImageView)findViewById(R.id.summonerIcon);
        ImageView soloduoIcon = (ImageView)findViewById(R.id.soloduoIcon);
        ImageView flexIcon = (ImageView)findViewById(R.id.flexIcon);

        int normalwin = 0;
        for(PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){
            Log.e(LOG_TAG, playerStatsSummary.getPlayerStatSummaryType() + " : " + playerStatsSummary.getWins());
            if(playerStatsSummary.getPlayerStatSummaryType().equals("Unranked")){
                normalwin = playerStatsSummary.getWins();
                break;
            }
        }

        String solorank = "";
        double solowin = 0;
        double sololoses = 0;
        String flexrank = "";
        double flexwin = 0;
        double flexloses = 0;

        LeagueEntry leagueEntrySolo = null;
        League leagueSolo = null;
        LeagueEntry leagueEntryFlex = null;
        League leagueFlex = null;

        if(lorSummoner.getLeagues() != null) {
            for (League league : lorSummoner.getLeagues()) {
                Log.e(LOG_TAG, league.getQueue() + ": ");
                if (league.getQueue().equals("RANKED_SOLO_5x5")) {
                    for (LeagueEntry leagueEntry : league.getEntries()) {
                        if (leagueEntry.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                            leagueEntrySolo = leagueEntry;
                            leagueSolo = league;
                        }
                    }
                } else if (league.getQueue().equals("RANKED_FLEX_SR")) {
                    for (LeagueEntry leagueEntry : league.getEntries()) {
                        if (leagueEntry.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                            leagueEntryFlex = leagueEntry;
                            leagueFlex = league;
                        }

                    }
                }
            }

            //Solo/Duo
            if(leagueEntrySolo != null){
                Log.e(LOG_TAG, leagueEntrySolo.getPlayerOrTeamName() + ": " + leagueSolo.getTier() + leagueEntrySolo.getDivision() + " " + leagueEntrySolo.getLeaguePoints());
                solorank = leagueSolo.getTier() + " " + leagueEntrySolo.getDivision() + " " + leagueEntrySolo.getLeaguePoints();
                solowin = leagueEntrySolo.getWins();
                sololoses = leagueEntrySolo.getLosses();
                String tier = leagueSolo.getTier() + "_" +leagueEntrySolo.getDivision();
                int id = this.getResources().getIdentifier(tier.toLowerCase(), "drawable", this.getPackageName());
                soloduoIcon.setImageResource(id);
            }else{
                soloduoIcon.setImageResource(R.drawable.unranked);
                for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()) {
                    if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                        solorank = "Unranked";
                        solowin = playerStatsSummary.getWins();
                        sololoses = playerStatsSummary.getLosses();
                    }
                }
            }

            //Flex
            if(leagueEntryFlex != null) {
                Log.e(LOG_TAG, leagueEntryFlex.getPlayerOrTeamName() + ": " + leagueFlex.getTier() + leagueEntryFlex.getDivision() + " " + leagueEntryFlex.getLeaguePoints());
                flexrank = leagueFlex.getTier() + " " + leagueEntryFlex.getDivision() + " " + leagueEntryFlex.getLeaguePoints();
                flexwin = leagueEntryFlex.getWins();
                flexloses = leagueEntryFlex.getLosses();
                String tier = leagueFlex.getTier() + "_" +leagueEntryFlex.getDivision();
                int id = this.getResources().getIdentifier(tier.toLowerCase(), "drawable", this.getPackageName());
                flexIcon.setImageResource(id);
            }else{
                flexIcon.setImageResource(R.drawable.unranked);
                for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()) {
                    if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedFlexSR")) {
                        flexrank = "Unranked";
                        flexwin = playerStatsSummary.getWins();
                        flexloses = playerStatsSummary.getLosses();
                    }
                }
            }
        }else{
            soloduoIcon.setImageResource(R.drawable.unranked);
            flexIcon.setImageResource(R.drawable.unranked);
            for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()) {
                if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedFlexSR")) {
                    flexrank = "Unranked";
                    flexwin = playerStatsSummary.getWins();
                    flexloses = playerStatsSummary.getLosses();
                } else if (playerStatsSummary.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                    solorank = "Unranked";
                    solowin = playerStatsSummary.getWins();
                    sololoses = playerStatsSummary.getLosses();
                }
            }
        }

        //NORMAL
        normalwins.setText("Wins: " + normalwin);
        level.setText("Level: " + lorSummoner.getSummoner().getSummonerLevel());

        //SOLO
        double solorates;
        double sologames = solowin + sololoses;
        solorates = solowin / sologames;
        double solovalue = Math.round(100.0 * (solorates * 100)) / 100.0;
        solowins.setText("Wins: " + solowin);
        solorate.setText("Winrate: " + solovalue + "%");

        //FLEX
        double flexrates;
        double flexgames = flexwin + flexloses;
        flexrates = flexwin / flexgames;
        double flexvalue = Math.round(100.0 * (flexrates * 100)) / 100.0;
        flexwins.setText("Wins: " + flexwin);
        flexrate.setText("Level: " + flexvalue + "%");

        lorSummoner.setSummonerIcon(summonerIcon);

        MenuItem favoriting = (MenuItem)menu.findItem(R.id.action_favorit_summoner);
        boolean favorit = checkFavorit();

        if (favorit){
            favoriting.setIcon(R.drawable.ic_favorite_white_48px);
        }else if(!favorit){
            favoriting.setIcon(R.drawable.ic_favorite_border_white_48px);
        }

        mDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean favorit = checkFavorit();
        if(item.getItemId() == R.id.action_favorit_summoner){
            if(!favorit){
                addFavorit();
                item.setIcon(R.drawable.ic_favorite_white_48px);
                Toast toast = Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT);
                toast.show();
            } else if(favorit){
                removeFavorit();
                item.setIcon(R.drawable.ic_favorite_border_white_48px);
                Toast toast = Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkFavorit(){
        boolean checkFavorit = false;
        SharedPreferences favoritSummoners = getSharedPreferences("FavoritSummoners", 0);
        Map<String, ?> allEntries = favoritSummoners.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(!checkFavorit && ((Integer) entry.getValue()) == lorSummoner.getSummoner().getId()) {
                checkFavorit = true;
            }else if(!checkFavorit && ((Integer) entry.getValue()) != lorSummoner.getSummoner().getId()){
                checkFavorit = false;
            }
        }
        return checkFavorit;
    }

    public void addFavorit(){
        SharedPreferences favoritSummoners = getSharedPreferences("FavoritSummoners", 0);
        SharedPreferences.Editor editor = favoritSummoners.edit();
        int i = 1;
        while (favoritSummoners.contains("FavoritSummoner" + i)){
            i++;
        }
        editor.putInt("FavoritSummoner" + i, (int)this.lorSummoner.getSummoner().getId());
        editor.commit();
    }

    public void removeFavorit(){
        SharedPreferences favoritSummoners = getSharedPreferences("FavoritSummoners", 0);
        SharedPreferences.Editor editor = favoritSummoners.edit();
        Map<String, ?> allEntries = favoritSummoners.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if ((Integer)entry.getValue() == lorSummoner.getSummoner().getId()){
                editor.remove(entry.getKey());
                editor.commit();
            }
        }
    }
}

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

        LoadingSummonerTask loadingSummonerTask = new LoadingSummonerTask(this);
        loadingSummonerTask.execute(intent.getStringExtra("summonerName"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        int normalwin = 0;
        for(PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){
            Log.e(LOG_TAG, playerStatsSummary.getPlayerStatSummaryType() + " : " + playerStatsSummary.getWins());
            if(playerStatsSummary.getPlayerStatSummaryType().equals("Unranked")){
                normalwin = playerStatsSummary.getWins();
                break;
            }
        }
        String solorank = " ";
        double solowin = 0;
        double sololoses = 0;
        String flexrank = " ";
        double flexwin = 0;
        double flexloses = 0;


        for(League league : lorSummoner.getLeagues()){
            Log.e(LOG_TAG, league.getQueue() +": ");
            if(league.getQueue().equals("RANKED_SOLO_5x5")) {
                for (LeagueEntry leagueEntry : league.getEntries()) {
                    if (leagueEntry.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                        Log.e(LOG_TAG, leagueEntry.getPlayerOrTeamName() + ": " + league.getTier() + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints());
                        solorank = league.getTier() + " " + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints();
                        solowin = leagueEntry.getWins();
                        sololoses = leagueEntry.getLosses();
                    }
                }
            }else if(league.getQueue().equals("RANKED_FLEX_SR")){
                for (LeagueEntry leagueEntry : league.getEntries()) {
                    if (leagueEntry.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                        Log.e(LOG_TAG, leagueEntry.getPlayerOrTeamName() + ": " + league.getTier() + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints());
                        flexrank = league.getTier() + " " + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints();
                        flexwin = leagueEntry.getWins();
                        flexloses = leagueEntry.getLosses();
                    }

                }
            }
        }

        setTitle(lorSummoner.getSummoner().getName());

        TextView normalwins = (TextView)findViewById(R.id.normalwins);
        TextView level = (TextView)findViewById(R.id.level);

        TextView solowins = (TextView)findViewById(R.id.solowins);
        TextView solorate = (TextView)findViewById(R.id.solorate);

        TextView flexwins = (TextView)findViewById(R.id.flexwins);
        TextView flexrate = (TextView)findViewById(R.id.flexrate);

        //in your OnCreate() method
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



        MenuItem favoriting = (MenuItem)menu.findItem(R.id.action_favorit_summoner);
        boolean favorit = checkFavorit();

        if (favorit){
            favoriting.setIcon(R.drawable.ic_favorite_white_48px);
        }else if(!favorit){
            favoriting.setIcon(R.drawable.ic_favorite_border_white_48px);
        }
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
                Toast toast = Toast.makeText(getApplicationContext(), "Add to Favorites", Toast.LENGTH_SHORT);
                toast.show();
            } else if(favorit){
                removeFavorit();
                item.setIcon(R.drawable.ic_favorite_border_white_48px);
                Toast toast = Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_SHORT);
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

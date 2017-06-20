package lor.ch.leagueofranks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;

import lor.ch.leagueofranks.model.LorSummoner;
import lor.ch.leagueofranks.task.LoadingSummonerTask;

public class SummonerProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = SummonerProfileActivity.class.getCanonicalName();

    private ProgressDialog mDialog;

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

    public void onData(LorSummoner lorSummoner){
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




    }
}

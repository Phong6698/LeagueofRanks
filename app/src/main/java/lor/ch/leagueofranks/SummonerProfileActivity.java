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
        for(PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){
            Log.e(LOG_TAG, playerStatsSummary.getPlayerStatSummaryType() + " : " + playerStatsSummary.getWins());
        }
        for(League league : lorSummoner.getLeagues()){
            Log.e(LOG_TAG, league.getQueue() +": ");
            for(LeagueEntry leagueEntry : league.getEntries()){
                if(leagueEntry.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())){
                    Log.e(LOG_TAG, leagueEntry.getPlayerOrTeamName() + ": " + league.getTier()+ leagueEntry.getDivision() + " "+ leagueEntry.getLeaguePoints());
                }

            }
        }

        setTitle(lorSummoner.getSummoner().getName());


    }
}

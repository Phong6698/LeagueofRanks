package lor.ch.leagueofranks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lor.ch.leagueofranks.model.Summoner;
import lor.ch.leagueofranks.json.SummonerStatsLoader;

public class SummonerProfileActivity extends AppCompatActivity {

    private Summoner summoner;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();

        summoner = new Summoner();
        summoner.setName(intent.getStringExtra("summonerName"));
        summoner.setSummonerId(intent.getIntExtra("summonerId", 0));
        summoner.setAccountId(intent.getIntExtra("accountId", 0));
        summoner.setSummonerLevel(intent.getIntExtra("summonerLevel", 0));
        summoner.setProfileIconId(intent.getIntExtra("profileIconId",0));
        summoner.setRegion(intent.getStringExtra("region"));

        mDialog = ProgressDialog.show(this, "Please wait", "Loading Summoner...");

        SummonerStatsLoader summonerStatsLoader = new SummonerStatsLoader(this, mDialog, summoner);
        summonerStatsLoader.execute(summoner.getRegion(), ""+summoner.getSummonerId());

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

    public void onData(Summoner summoner){
        setTitle(summoner.getName());
    }

}

package lor.ch.leagueofranks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import lor.ch.leagueofranks.listener.SearchSummonerListener;
import lor.ch.leagueofranks.model.Summoner;
import lor.ch.leagueofranks.json.SummonerIDLoader;

public class SearchSummonerActivity extends AppCompatActivity {

    private static final String LOG_TAG = SearchSummonerActivity.class.getCanonicalName();
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_summoner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button searchbutton = (Button) findViewById(R.id.searchSummonerButton);
        searchbutton.setOnClickListener(new SearchSummonerListener(this, (EditText)findViewById(R.id.searchSummonerName), (Spinner)findViewById(R.id.searchSummonerSpinner)));

        Spinner regionSpinner = (Spinner) findViewById(R.id.searchSummonerSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.region, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        regionSpinner.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onData(Summoner summoner){

        Intent intent = new Intent(this, SummonerProfileActivity.class);
        intent.putExtra("summonerName", summoner.getName());
        intent.putExtra("summonerId", summoner.getSummonerId());
        intent.putExtra("accountId", summoner.getAccountId());
        intent.putExtra("summonerLevel", summoner.getSummonerLevel());
        intent.putExtra("profileIconId", summoner.getProfileIconId());
        intent.putExtra("region", summoner.getRegion());

        startActivity(intent);
        mDialog.dismiss();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

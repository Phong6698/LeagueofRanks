package lor.ch.leagueofranks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import lor.ch.leagueofranks.model.LorSummoner;
import lor.ch.leagueofranks.task.LoadingSummonerTask;

public class SummonersActivity extends AppCompatActivity {

    private static final String LOG_TAG = SummonersActivity.class.getCanonicalName();
    private ProgressDialog mDialog;

    private ArrayList<LorSummoner> lorSummoners;
    private int countFavSummoner = 0;



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    /**
     * The {@link ViewPager} that will host the section contents.
     */


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoners);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lorSummoners = new ArrayList<>();
        loadingLists();

    }

    private void setupTabbedActivity(){
        mViewPager = (ViewPager) findViewById(R.id.container);
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        Tab1Normal tab1Normal = new Tab1Normal();
        Tab2SoloDuo tab2SoloDuo = new Tab2SoloDuo();
        Tab3Flex tab3Flex = new Tab3Flex();

        tab1Normal.setLorSummoner(this, new ArrayList<LorSummoner>(lorSummoners));
        tab2SoloDuo.setLorSummoner(this, new ArrayList<LorSummoner>(lorSummoners));
        tab3Flex.setLorSummoner(this, new ArrayList<LorSummoner>(lorSummoners));

        adapter.addFragment(tab1Normal, "Normal");
        adapter.addFragment(tab2SoloDuo, "Solo/Duo");
        adapter.addFragment(tab3Flex, "Flex");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    private void loadingLists(){
        mDialog = ProgressDialog.show(this, "Please wait", "Loading Summoners...");
        SharedPreferences favoritSummoners = getSharedPreferences("FavoritSummoners", 0);
        Map<String, ?> allEntries = favoritSummoners.getAll();
        countFavSummoner = allEntries.size();

        if(allEntries.isEmpty()) {
            setupTabbedActivity();
            mDialog.dismiss();
        }else{
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                LoadingSummonerTask loadingSummonerTask = new LoadingSummonerTask(this, countFavSummoner);
                loadingSummonerTask.execute("" + entry.getValue());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

        if(item.getItemId() == R.id.action_search_summoner) {
            Intent intent = new Intent(this, SearchSummonerActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onData(LorSummoner lorSummoner) {

        this.lorSummoners.add(lorSummoner);
        if (this.lorSummoners.size() == countFavSummoner) {
            setupTabbedActivity();
            mDialog.dismiss();
        }
    }
}

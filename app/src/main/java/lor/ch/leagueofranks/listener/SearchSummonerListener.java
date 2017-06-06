package lor.ch.leagueofranks.listener;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import lor.ch.leagueofranks.R;
import lor.ch.leagueofranks.SearchSummonerActivity;
import lor.ch.leagueofranks.json.SummonerIDLoader;

/**
 * Created by phong on 06.06.2017.
 */

public class SearchSummonerListener  implements View.OnClickListener {

    private static final String LOG_TAG = SearchSummonerListener.class.getCanonicalName();

    private SearchSummonerActivity searchSummonerActivity;
    private EditText summonerTextField;
    private Spinner regionSpinner;
    private ProgressDialog mDialog;

    public SearchSummonerListener(SearchSummonerActivity searchSummonerActivity, EditText summonerTextField, Spinner regionSpinner) {
        this.searchSummonerActivity = searchSummonerActivity;
        this.summonerTextField = summonerTextField;
        this.regionSpinner = regionSpinner;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchSummonerButton) {

            String summonerName = this.summonerTextField.getText().toString();
            String region = this.regionSpinner.getSelectedItem().toString();

            Log.e(LOG_TAG, "summoner: " + summonerName);
            Log.e(LOG_TAG, "region: " + region);

            mDialog = ProgressDialog.show(searchSummonerActivity, "Please wait", "Searching Summoner...");

            SummonerIDLoader summonerIDLoader = new SummonerIDLoader(searchSummonerActivity, mDialog);
            summonerIDLoader.execute(region, summonerName);
        }
    }
}

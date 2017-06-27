package lor.ch.leagueofranks.listener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import lor.ch.leagueofranks.R;
import lor.ch.leagueofranks.SearchSummonerActivity;
import lor.ch.leagueofranks.SummonerProfileActivity;
import lor.ch.leagueofranks.task.LoadingSummonerTask;

/**
 * Created by phong on 06.06.2017.
 */

public class SearchSummonerListener  implements View.OnClickListener {

    private static final String LOG_TAG = SearchSummonerListener.class.getCanonicalName();

    private SearchSummonerActivity searchSummonerActivity;
    private EditText summonerTextField;
    private Spinner regionSpinner;


    public SearchSummonerListener(SearchSummonerActivity searchSummonerActivity, EditText summonerTextField, Spinner regionSpinner) {
        this.searchSummonerActivity = searchSummonerActivity;
        this.summonerTextField = summonerTextField;
        this.regionSpinner = regionSpinner;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchSummonerButton) {

            Intent intent = new Intent(searchSummonerActivity, SummonerProfileActivity.class);
            intent.putExtra("summonerName", summonerTextField.getText().toString());

            searchSummonerActivity.startActivity(intent);

        }
    }
}

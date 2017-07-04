package lor.ch.leagueofranks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;
import lor.ch.leagueofranks.task.SummonerSorter;

/**
 * Created by phong on 30.05.2017.
 */

public class Tab2SoloDuo extends Fragment {

    private static final String LOG_TAG = Tab2SoloDuo.class.getCanonicalName();
    private SummonersActivity summonersActivity;
    private ArrayList<LorSummoner> lorSummoners;
    private View rootView;
    private SummonerSorter summonerSorter;

    public void setLorSummoner(SummonersActivity summonersActivity, ArrayList<LorSummoner> lorSummoners){
        this.lorSummoners = lorSummoners;
        this.summonersActivity = summonersActivity;
        this.summonerSorter = new SummonerSorter(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.tab2soloduo, container, false);

        Spinner sortSpinner = (Spinner) rootView.findViewById(R.id.soloduoSortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(summonersActivity, R.array.sort_ranks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getAdapter().getItem(position).toString();
                if(selectedItem.equals("Elo")){
                    Log.e(LOG_TAG, "ELO");
                    lorSummoners = summonerSorter.sortRankedRank(lorSummoners);
                }else if(selectedItem.equals("Wins")){
                    Log.e(LOG_TAG, "WINS");
                    lorSummoners = summonerSorter.sortRankedWins(lorSummoners);
                }else if(selectedItem.equals("Win Rate")){
                    Log.e(LOG_TAG, "WIN RATE");
                    lorSummoners = summonerSorter.sortRankedWinRate(lorSummoners);
                }else if(selectedItem.equals("Games")){
                    Log.e(LOG_TAG, "GAMES");
                    lorSummoners = summonerSorter.sortRankedGames(lorSummoners);
                }
                setListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        setListView();
        return rootView;
    }

    public void setListView() {
        TextView noFavText = (TextView) rootView.findViewById(R.id.soloduoNoFavText);
        if(lorSummoners.isEmpty()){
            noFavText.setVisibility(View.VISIBLE);
        }else {
            noFavText.setVisibility(View.INVISIBLE);
            AdapterRankedList adapterRankedList = new AdapterRankedList(summonersActivity, R.id.ranked_list, lorSummoners, false);
            ListView soloduoListView = (ListView) rootView.findViewById(R.id.soloduoListView);
            soloduoListView.setAdapter(adapterRankedList);
            soloduoListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){
                    Intent intent = new Intent(summonersActivity, SummonerProfileActivity.class);
                    intent.putExtra("summonerName", lorSummoners.get(position).getSummoner().getName());
                    summonersActivity.startActivity(intent);
                }
            });
        }
    }
}

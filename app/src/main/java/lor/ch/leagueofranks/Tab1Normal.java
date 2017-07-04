package lor.ch.leagueofranks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 30.05.2017.
 */

public class Tab1Normal extends Fragment {

    private SummonersActivity summonersActivity;
    private ArrayList<LorSummoner> lorSummoners;
    private View rootView;
    private SummonerSorter summonerSorter;


    public void setLorSummoner(SummonersActivity summonersActivity, ArrayList<LorSummoner> lorSummoners){
        this.lorSummoners = lorSummoners;
        this.summonersActivity = summonersActivity;
        this.summonerSorter = new SummonerSorter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.tab1normal, container, false);

        setListView();

        return rootView;
    }

    public void setListView(){
        TextView noFavText = (TextView) rootView.findViewById(R.id.normalNoFavText);
        this.lorSummoners = summonerSorter.sortNormalWins(lorSummoners);
        if(lorSummoners.isEmpty()){
            noFavText.setVisibility(View.VISIBLE);
        }else{
            noFavText.setVisibility(View.INVISIBLE);
            AdapterNormalList adapterNormalList = new AdapterNormalList(summonersActivity, R.id.normal_list, lorSummoners);
            ListView normalListView = (ListView) rootView.findViewById(R.id.normalListView);
            normalListView.setAdapter(adapterNormalList);
            normalListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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

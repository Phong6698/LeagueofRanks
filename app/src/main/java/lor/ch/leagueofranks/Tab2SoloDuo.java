package lor.ch.leagueofranks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 30.05.2017.
 */

public class Tab2SoloDuo extends Fragment {

    private SummonersActivity summonersActivity;
    private ArrayList<LorSummoner> lorSummoners;

    public void setLorSummoner(SummonersActivity summonersActivity, ArrayList<LorSummoner> lorSummoners){
        this.lorSummoners = lorSummoners;
        this.summonersActivity = summonersActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2soloduo, container, false);

        TextView noFavText = (TextView) rootView.findViewById(R.id.soloduoNoFavText);
        if(lorSummoners.isEmpty()){
            noFavText.setVisibility(View.VISIBLE);
        }else {
            noFavText.setVisibility(View.INVISIBLE);
            AdapterRankedList adapterRankedList = new AdapterRankedList(summonersActivity, R.id.ranked_list, lorSummoners, false);
            ListView soloduoListView = (ListView) rootView.findViewById(R.id.soloduoListView);
            soloduoListView.setAdapter(adapterRankedList);
        }
        return rootView;
    }
}

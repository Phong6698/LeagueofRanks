package lor.ch.leagueofranks;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 30.05.2017.
 */

public class Tab1Normal extends Fragment {

    private SummonersActivity summonersActivity;
    private ArrayList<LorSummoner> lorSummoners;


    public void setLorSummoner(SummonersActivity summonersActivity, ArrayList<LorSummoner> lorSummoners){
        this.lorSummoners = lorSummoners;
        this.summonersActivity = summonersActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1normal, container, false);
        AdapterNormalList adapterNormalList = new AdapterNormalList(summonersActivity, R.id.normal_list, lorSummoners);
        ListView normalListView = (ListView) rootView.findViewById(R.id.normalListView);
        normalListView.setAdapter(adapterNormalList);
        return rootView;
    }

}

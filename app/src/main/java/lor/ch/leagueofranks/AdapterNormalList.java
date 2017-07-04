package lor.ch.leagueofranks;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.rithms.riot.dto.Stats.PlayerStatsSummary;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 20.06.2017.
 */

public class AdapterNormalList extends ArrayAdapter<LorSummoner> {

    private static final String LOG_TAG = AdapterNormalList.class.getCanonicalName();
    private Context context;

    public AdapterNormalList(@NonNull Context context, int resource, ArrayList<LorSummoner> lorSummoners) {
        super(context, resource, lorSummoners);
        this.context = context;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_normal_list, parent, false);

        //Getting View
        TextView normalSummonerName = (TextView) rowView.findViewById(R.id.normalSummonerName);
        TextView normalWins = (TextView) rowView.findViewById(R.id.normalWins);
        ImageView normalSummonerIcon = (ImageView) rowView.findViewById((R.id.normalSummonerIcon));

        LorSummoner lorSummoner = this.getItem(position);

        normalSummonerName.setText(lorSummoner.getSummoner().getName());

        for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){
            Log.e(LOG_TAG,  lorSummoner.getSummoner().getName()+": "+ playerStatsSummary.getPlayerStatSummaryType());
            if(playerStatsSummary.getPlayerStatSummaryType().equals("Unranked")){

                normalWins.setText(playerStatsSummary.getWins()+" Wins");
                break;
            }
        }

        lorSummoner.setSummonerIcon(normalSummonerIcon);

        return rowView;
    }

}

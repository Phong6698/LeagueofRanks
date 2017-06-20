package lor.ch.leagueofranks;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    private Context context;

    public AdapterNormalList(@NonNull Context context, int resource, ArrayList<LorSummoner> lorSummoners) {
        super(context, resource, lorSummoners);
        this.context = context;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_normal_list, parent, false);

        TextView summonerName = (TextView) rowView.findViewById(R.id.normalSummonerName);
        TextView summonerWins = (TextView) rowView.findViewById(R.id.normalWins);
        ImageView summonerIcon = (ImageView) rowView.findViewById((R.id.normalSummonerIcon));

        int wins = 0;
        summonerName.setText(this.getItem(position).getSummoner().getName());
        for (PlayerStatsSummary playerStatsSummary : this.getItem(position).getPlayerStatsSummaryList().getPlayerStatSummaries()){
            if(playerStatsSummary.getPlayerStatSummaryType().equals("Unranked")){
              wins = playerStatsSummary.getWins();
                break;
            }
        }
        summonerWins.setText(wins);

        //TODO summoner icon

        return rowView;
    }

}

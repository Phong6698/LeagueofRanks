package lor.ch.leagueofranks;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;

import java.util.ArrayList;

import lor.ch.leagueofranks.model.LorSummoner;

/**
 * Created by phong on 20.06.2017.
 */

public class AdapterRankedList extends ArrayAdapter<LorSummoner> {

    private static final String LOG_TAG = AdapterRankedList.class.getCanonicalName();
    private Context context;
    private boolean isFlex;

    public AdapterRankedList(@NonNull Context context, int resource, ArrayList<LorSummoner> lorSummoners, boolean isFlex) {
        super(context, resource, lorSummoners);
        this.context = context;
        this.isFlex = isFlex;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_ranked_list, parent, false);

        TextView rankedSummonerName = (TextView) rowView.findViewById(R.id.rankedSummonerName);
        TextView rankedElo = (TextView) rowView.findViewById(R.id.rankedElo);
        TextView rankedWinRate = (TextView) rowView.findViewById(R.id.rankedWinRate);
        TextView rankedWins = (TextView) rowView.findViewById(R.id.rankedWins);
        TextView rankedGames = (TextView) rowView.findViewById(R.id.rankedGames);
        ImageView rankedEloIcon = (ImageView) rowView.findViewById((R.id.rankedEloIcon));

        LorSummoner lorSummoner = this.getItem(position);

        rankedSummonerName.setText(lorSummoner.getSummoner().getName());
        //rankedElo.setText();
        LeagueEntry leagueEntry = null;

        for(League league : lorSummoner.getLeagues()) {
            Log.e(LOG_TAG,  lorSummoner.getSummoner().getName()+": "+ league.getQueue());
            if (isFlex) {
                if (league.getQueue().equals("RANKED_FLEX_SR")) {
                    for (LeagueEntry leagueEntryItem : league.getEntries()) {
                        if (leagueEntryItem.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                            leagueEntry  = leagueEntryItem;
                            break;
                        }

                    }
                }
            } else {
                if (league.getQueue().equals("RANKED_SOLO_5x5")) {
                    for (LeagueEntry leagueEntryItem : league.getEntries()) {
                        if (leagueEntryItem.getPlayerOrTeamName().equals(lorSummoner.getSummoner().getName())) {
                            leagueEntry  = leagueEntryItem;
                            break;
                        }
                    }
                }
            }

            if(leagueEntry != null){
                rankedElo.setText(league.getTier() + " " + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints() +"LP");

                double wins = leagueEntry.getWins();
                double losses = leagueEntry.getLosses();
                double winRate = (wins / (wins + losses)) * 100;

                rankedWinRate.setText(Math.round(winRate) + "% Win Rate");
                rankedWins.setText(leagueEntry.getWins() + " Wins");
                rankedGames.setText((leagueEntry.getWins() + leagueEntry.getLosses() + " Games"));
            }else{
                rankedElo.setText("Unranked");

                for (PlayerStatsSummary playerStatsSummary : lorSummoner.getPlayerStatsSummaryList().getPlayerStatSummaries()){

                    if(playerStatsSummary.getPlayerStatSummaryType().equals("RankedFlexSR")){

                        double wins = playerStatsSummary.getWins();
                        double losses = playerStatsSummary.getLosses();
                        double winRate = (wins / (wins + losses)) * 100;

                        rankedWinRate.setText(Math.round(winRate) + "% Win Rate");
                        rankedWins.setText(playerStatsSummary.getWins() + " Wins");
                        rankedGames.setText((playerStatsSummary.getWins() + playerStatsSummary.getLosses() + " Games"));

                        break;
                    }
                }
            }


        }


        //TODO summoner icon
        //TODO ranked icon

        return rowView;
    }

}

package sonu.finds.ipl2019.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Model.DrawerFullModel;
import sonu.finds.ipl2019.R;

public class DrawerFullAdapter extends RecyclerView.Adapter<DrawerFullAdapter.ViewHolder> {
    private int bool = 0;
    private List<DrawerFullModel> list;
    private Context context;

    public DrawerFullAdapter(int bool, List<DrawerFullModel> list, Context context) {
        this.bool = bool;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (bool == 2) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_results_layout, viewGroup, false));

        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_schdule_layout, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DrawerFullModel model = list.get(i);
        if (bool ==1) {
            //schedule
            Glide.with(context).load(model.getTeam1image()).into(viewHolder.team1);
            Glide.with(context).load(model.getTeam2image()).into(viewHolder.team2);
            viewHolder.match_no.setText(model.getMatch_no());
            viewHolder.date.setText(model.getDate());
            viewHolder.venu.setText(model.getVenu());
        }
        if (bool ==2){
            //highlists
            viewHolder.match_no.setText(model.getMatch_no());
            viewHolder.date.setText(model.getDate());
            viewHolder.team1Name.setText(model.getTeam1_Name());
            viewHolder.team2Name.setText(model.getTeam2_Name());
            viewHolder.score_team1_score.setText(model.getScore_team1_score());
            viewHolder.over_team1_over.setText(model.getOver_team1_over());
            viewHolder.score_team2_score.setText(model.getScore_team2_score());
            viewHolder.over_team2_over.setText(model.getOver_team2_over());
            viewHolder.result_declare.setText(model.getResult_declare());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView team1, team2;
        TextView match_no, date, venu,result_declare,team1Name,team2Name,highlights,
                score_team1_score,over_team1_over,score_team2_score,over_team2_over;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (bool == 1) {
                team1 = itemView.findViewById(R.id.schdule_match_team1);
                team2 = itemView.findViewById(R.id.schdule_match_team2);
                match_no = itemView.findViewById(R.id.schdule_match_no);
                date = itemView.findViewById(R.id.schdule_date_time);
                venu = itemView.findViewById(R.id.schdeult_stadium);

            }
            if (bool == 2){
                match_no =itemView.findViewById(R.id.resutl_match_no);
                date =itemView.findViewById(R.id.resutls_match_date);
                team1Name =itemView.findViewById(R.id.result_team1_name);
                team2Name =itemView.findViewById(R.id.result_team2_name);
                score_team1_score =itemView.findViewById(R.id.result_team1_score);
                score_team2_score =itemView.findViewById(R.id.result_team2_score);
                over_team1_over =itemView.findViewById(R.id.result_team1_overs_wickets);
                over_team2_over =itemView.findViewById(R.id.result_team2_overs_wickets);
                  result_declare =itemView.findViewById(R.id.result_result);
                  highlights =itemView.findViewById(R.id.result_watch_highlight);

            }
            if (bool ==2){
                highlights.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "watch highlits cliked"+list.get(getAdapterPosition()).getHighlits(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}

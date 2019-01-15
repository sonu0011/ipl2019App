package sonu.finds.ipl2019.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sonu.finds.ipl2019.Model.TeamItemModel;
import sonu.finds.ipl2019.R;

public class TeamItemAdapter extends RecyclerView.Adapter<TeamItemAdapter.ViewHolder> {
    private static final String TAG = "TeamItemAdapter";
    List<TeamItemModel> list;
    Context context;
    int bool = 0;
    int team_id;

    public TeamItemAdapter(List<TeamItemModel> list, Context context, int bool,int team_id) {
        this.list = list;
        this.context = context;
        this.bool = bool;
        this.team_id =team_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_team_items_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: "+i);
        TeamItemModel model =  list.get(i);
        if (model.getTeam_item_title().equals("Squad")||
                model.getTeam_item_title().equals("Schedule")||
                model.getTeam_item_title().equals("Highlights")){
            viewHolder.title.setText(model.getTeam_item_title());
            viewHolder.tit_value.setText("");
        }
            if (model.getTeam_item_title().equals("Highest Score")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getHigh_score());
                viewHolder.button.setVisibility(View.INVISIBLE);
            }
            if (model.getTeam_item_title().equals("Lowest Score")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getLow_socre());
            }
            if (model.getTeam_item_title().equals("Total Fours")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_fours());
            }
            if (model.getTeam_item_title().equals("Total Sixes")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_sixes());
            }
            if (model.getTeam_item_title().equals("Most Fours / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_most_four_inng());
            }
            if (model.getTeam_item_title().equals("Most Sixes / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_most_sixes_inng());
            }
            if (model.getTeam_item_title().equals("Total Wickets")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_total_wickets());
            }
            if (model.getTeam_item_title().equals("Most Wickets / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_most_wickets_inng());
            }






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,tit_value;
        Button button;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (bool == 1){
                tit_value = itemView.findViewById(R.id.team_title_value);
                title =  itemView.findViewById(R.id.team_iteam_title);
                button = itemView.findViewById(R.id.team_item_btn);
                imageView =itemView.findViewById(R.id.team_item_image);
            }
        }
    }
}

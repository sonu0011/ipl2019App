package sonu.finds.ipl2019.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Activity.ExtraTeamItemActivity;
import sonu.finds.ipl2019.Model.TeamItemModel;
import sonu.finds.ipl2019.R;

public class TeamItemAdapter extends RecyclerView.Adapter<TeamItemAdapter.ViewHolder> {

    private static final String TAG = "TeamItemAdapter";
    List<TeamItemModel> list;
    Context context;
    int bool = 0;//2 squade  3 schdeule  4 //highlights  /  1 team_itesm
    int team_id;
    private int lastPosition =-1;

    public TeamItemAdapter(List<TeamItemModel> list, Context context, int bool,int team_id) {
        this.list = list;
        this.context = context;
        this.bool = bool;
        this.team_id =team_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (bool == 1) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_team_items_layout, viewGroup, false));
        }
        if (bool == 2) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_squade_layout, viewGroup, false));
        }
        if (bool == 3) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_schdule_layout, viewGroup, false));
        }
        else
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_results_layout, viewGroup, false));



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: "+i);
        TeamItemModel model =  list.get(i);
        if (bool ==1) {
            if (model.getTeam_item_title().equals("Squad") ||
                    model.getTeam_item_title().equals("Schedule") ||
                    model.getTeam_item_title().equals("Highlights")) {
                viewHolder.button.setVisibility(View.VISIBLE);
                viewHolder.button.setText(model.getButton_value());
                viewHolder.tit_value.setText("");
                viewHolder.title.setText(model.getTeam_item_title());
            }
            if (model.getTeam_item_title().equals("Highest Score")) {

                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getHigh_score());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getHigh_score().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Lowest Score")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getLow_socre());
                if (model.getLow_socre().equals("")){
                    viewHolder.tit_value.setText("------");
                }
            }
            if (model.getTeam_item_title().equals("Total Fours")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_fours());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTotal_fours().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Total Sixes")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_sixes());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTotal_sixes().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Most Fours / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_most_four_inng());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTeam_most_four_inng().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Most Sixes / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTotal_most_sixes_inng());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTotal_most_sixes_inng().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Total Wickets")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_total_wickets());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTeam_total_wickets().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            if (model.getTeam_item_title().equals("Most Wickets / Innings")) {
                viewHolder.title.setText(model.getTeam_item_title());
                viewHolder.tit_value.setText(model.getTeam_most_wickets_inng());
                viewHolder.button.setVisibility(View.INVISIBLE);
                if (model.getTeam_most_wickets_inng().equals("")){
                    viewHolder.tit_value.setText("------");
                }

            }
            switch (team_id) {
                case 1:
                    viewHolder.imageView.setImageResource(R.drawable.csk_bg);
                    break;
                case 2:
                    viewHolder.imageView.setImageResource(R.drawable.dc_bg);
                    break;
                case 3:
                    viewHolder.imageView.setImageResource(R.drawable.kxip_bg);
                    break;
                case 4:
                    viewHolder.imageView.setImageResource(R.drawable.kkr_bg);
                    break;
                case 5:
                    viewHolder.imageView.setImageResource(R.drawable.mi_bg);
                    break;
                case 6:
                    viewHolder.imageView.setImageResource(R.drawable.rr_bg);
                    break;
                case 7:
                    viewHolder.imageView.setImageResource(R.drawable.rcb_bg);
                    break;
                case 8:
                    viewHolder.imageView.setImageResource(R.drawable.srh_bg);
                    break;



            }
            if(i >lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.down_to_top);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }
        }
        if (bool == 2){
            Glide.with(context).load(model.getPlayer_image()).into(viewHolder.player_image);
            viewHolder.player_name.setText(model.getPlayer_name());
            if(i >lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.top_down);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }
            if(i <lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.down_to_top);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }

        }
        if (bool == 3){
            //schedule
            Glide.with(context).load(model.getTeam1_image()).into(viewHolder.team1);
            Glide.with(context).load(model.getTeam_2_image()).into(viewHolder.team2);
            viewHolder.match_no.setText(model.getMatch_no());
            viewHolder.date.setText(model.getDate_time());
            viewHolder.venu.setText(model.getVenu());
            if(i >lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.top_down);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }
            if(i <lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.down_to_top);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }

        }
        if (bool == 4){
            //highlights
            viewHolder.match_no.setText(model.getMatch_no());
            viewHolder.date.setText(model.getDate_time());
            viewHolder.team1Name.setText(model.getTeam1_name());
            viewHolder.team2Name.setText(model.getTeam2_name());
            viewHolder.score_team1_score.setText(model.getScore_team1_score());
            viewHolder.over_team1_over.setText(model.getOver_team1_over());
            viewHolder.score_team2_score.setText(model.getScore_team2_score());
            viewHolder.over_team2_over.setText(model.getOver_team2_over());
            viewHolder.result_declare.setText(model.getResult_declare());
            if(i >lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.top_down);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }
            if(i <lastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.down_to_top);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = i;
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,tit_value,player_name;
        Button button;
        ImageView imageView,player_image;
        ImageView team1, team2;
        TextView match_no, date, venu,result_declare,team1Name,team2Name,highlights,
                score_team1_score,over_team1_over,score_team2_score,over_team2_over;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (bool == 1){
                tit_value = itemView.findViewById(R.id.team_title_value);
                title =  itemView.findViewById(R.id.team_iteam_title);
                button = itemView.findViewById(R.id.team_item_btn);
                imageView =itemView.findViewById(R.id.team_item_image);
            }
            if (bool == 2){
                //squad
                player_image = itemView.findViewById(R.id.team_squad_image);
                player_name = itemView.findViewById(R.id.team_squad_name);

            }
            if (bool ==3){
                //schedule
                team1 = itemView.findViewById(R.id.schdule_match_team1);
                team2 = itemView.findViewById(R.id.schdule_match_team2);
                match_no = itemView.findViewById(R.id.schdule_match_no);
                date = itemView.findViewById(R.id.schdule_date_time);
                venu = itemView.findViewById(R.id.schdeult_stadium);

            }
            if (bool == 4){
                //highlists
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
            if (bool ==1){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,ExtraTeamItemActivity.class);
                        intent.putExtra("team_id",team_id);
                        intent.putExtra("request_type",list.get(getAdapterPosition()).getTeam_item_title());
                        context.startActivity(intent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.left_to_right,
                                R.anim.right_to_left);
                    }
                });
            }
        }
    }
}

package sonu.finds.ipl2019.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Activity.BattingLeaderActivity;
import sonu.finds.ipl2019.Activity.BowlingLeaderActivity;
import sonu.finds.ipl2019.Activity.HomeActivity;
import sonu.finds.ipl2019.Activity.TeamActivity;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<HomeModel> list;
    private static final String TAG = "HomeAdapter";
    private int value =0;

    public HomeAdapter(Context context, List<HomeModel> list) {
        this.context = context;
        this.list = list;
        Log.d(TAG, "HomeAdapter:  first constructor");
    }

    public HomeAdapter(Context context, List<HomeModel> modelList, int value) {
        this.list =modelList;
        this.context =context;
        this.value =value;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (value == 1) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_team_layout, viewGroup, false));

        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.batting_bowling_leaders_layout, viewGroup, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (value == 1) {
            HomeModel model = list.get(i);
            Glide.with(context).load(model.getTeamimage_url()).into(viewHolder.imageView1);
        } else {
            HomeModel model = list.get(i);

            Log.d(TAG, "onBindViewHolder: first bind");

            Glide.with(context).load(model.getImage_url()).into(viewHolder.imageView);
            String s = model.getHeading();

            String res = s.replaceFirst("\\s", "\n");

            viewHolder.textView.setText(res);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,imageView1;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            if (value == 1) {
                imageView1 =itemView.findViewById(R.id.team_image);

            } else {
                imageView = itemView.findViewById(R.id.leader_image);
                textView = itemView.findViewById(R.id.leader_heading);


            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (value ==1){
                        //team
                        Intent intent =new Intent(context,TeamActivity.class);
                        intent.putExtra("team_id",list.get(getAdapterPosition()).getTeam_id());
                        context.startActivity(intent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.left_to_right,
                                R.anim.right_to_left);
                    }
                    if (value ==2){
                        //batsman
                        Intent intent =new Intent(context,BattingLeaderActivity.class);
                        intent.putExtra("batting_heading",list.get(getAdapterPosition()).getHeading());
                        context.startActivity(intent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.left_to_right,
                                R.anim.right_to_left);
                    }
                    if (value == 3){
                        //bowler
                        Intent intent =new Intent(context,BowlingLeaderActivity.class);
                        intent.putExtra("bowling_heading",list.get(getAdapterPosition()).getHeading());
                        context.startActivity(intent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.left_to_right,
                                R.anim.right_to_left);
                    }
                }
            });
        }
    }
}

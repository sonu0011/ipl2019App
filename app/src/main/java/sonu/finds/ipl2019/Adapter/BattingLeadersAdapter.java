package sonu.finds.ipl2019.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Model.BattingLeadersModel;
import sonu.finds.ipl2019.R;

public class BattingLeadersAdapter extends RecyclerView.Adapter<BattingLeadersAdapter.ViewHolder> {
    Context context;
    List<BattingLeadersModel> leadersModels;
    int bool =0;
    private static final String TAG = "BattingLeadersAdapter";
    private int lastPosition = -1;

    public BattingLeadersAdapter(Context context, List<BattingLeadersModel> leadersModels) {
        this.context = context;
        this.leadersModels = leadersModels;
    }

    public BattingLeadersAdapter(Context context, List<BattingLeadersModel> list, int i) {

        this.context =context;
        this.leadersModels =list;
        this.bool =i;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
if (bool ==0){
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_most_50_100,viewGroup,false));
}
else if (bool ==2){
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_most_four_sixes,viewGroup,false));

}
else if (bool == 3){
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_longest_six,viewGroup,false));

}
else {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_fastest_50_100,viewGroup,false));

}
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BattingLeadersModel model = leadersModels.get(i);
        Log.d(TAG, "onBindViewHolder: "+model.toString());
        Glide.with(context).load(model.getImage_url()).into(viewHolder.image);
        viewHolder.posotion.setText(String.valueOf(model.getPosotion()));
        viewHolder.name.setText(model.getName());
        viewHolder.facility.setText(model.getFacility());
        if(i >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.top_down);
            viewHolder.itemView.startAnimation(animation);
            lastPosition = i;
        }

    }

    @Override
    public int getItemCount() {
        return leadersModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView posotion,name,facility;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (bool == 0) {
                posotion = itemView.findViewById(R.id.kohli_position);
                name = itemView.findViewById(R.id.kohli_name);
                facility = itemView.findViewById(R.id.kohli_specality);
                image = itemView.findViewById(R.id.kohli_image);
            }
            if (bool ==1){
                posotion = itemView.findViewById(R.id.four_sixes_count);
                name = itemView.findViewById(R.id.four_sixes_name);
                facility = itemView.findViewById(R.id.four_sixes_feat);
                image = itemView.findViewById(R.id.four_sixes_image);

            }
            if (bool ==2){
                posotion = itemView.findViewById(R.id.most_four_sixes_position);
                name = itemView.findViewById(R.id.most_four_sixes_name);
                facility = itemView.findViewById(R.id.most_four_sixes_feat);
                image = itemView.findViewById(R.id.most_four_sixes_image);

            }
            if (bool ==3){
                posotion = itemView.findViewById(R.id.longest_six_position);
                name = itemView.findViewById(R.id.longest_six_name);
                facility = itemView.findViewById(R.id.longest_six_feat);
                image = itemView.findViewById(R.id.longest_six_image);

            }
        }

    }
}

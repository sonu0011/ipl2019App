package sonu.finds.ipl2019.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Model.BowlingLeaderModel;
import sonu.finds.ipl2019.R;

public class BowlingLeaderAdapter extends RecyclerView.Adapter<BowlingLeaderAdapter.ViewHolder> {
    List<BowlingLeaderModel> list;
    Context context;
    int pos =0;

    public BowlingLeaderAdapter(List<BowlingLeaderModel> list, Context context, int pos) {
        this.list = list;
        this.context = context;
        this.pos = pos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_bowling_leader_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BowlingLeaderModel model = list.get(i);
        Glide.with(context).load(model.getImage()).into(viewHolder.image);
        viewHolder.name.setText(model.getName());
        viewHolder.positon.setText(String.valueOf(model.getPositon()));
        viewHolder.feat.setText(model.getFeat());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView positon,name,feat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.bowling_image);
            positon =itemView.findViewById(R.id.bowling_position);
            name =itemView.findViewById(R.id.bowling_name);
            feat =itemView.findViewById(R.id.bowling_feat);
        }
    }
}

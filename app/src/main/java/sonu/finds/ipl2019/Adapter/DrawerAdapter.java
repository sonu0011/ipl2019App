package sonu.finds.ipl2019.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sonu.finds.ipl2019.Activity.BowlingLeaderActivity;
import sonu.finds.ipl2019.Activity.DrawerFullActivity;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    List<HomeModel> list ;
    Context context;

    public DrawerAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_drawer_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       HomeModel model = list.get(i);
        Glide.with(context).load(model.getDrawer_bg()).into(viewHolder.bg);
        Glide.with(context).load(model.getDrawer_icon()).into(viewHolder.icon);
        viewHolder.title.setText(model.getDrawer_heading());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView bg,icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.drawer_text);
            bg =itemView.findViewById(R.id.drawer_bg);
            icon =itemView.findViewById(R.id.drawer_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context,DrawerFullActivity.class);
                    intent.putExtra("drawer_heading",list.get(getAdapterPosition()).getDrawer_heading());
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition(R.anim.left_to_right,
                            R.anim.right_to_left);
                }
            });

        }

    }
}

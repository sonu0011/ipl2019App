package sonu.finds.ipl2019.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import sonu.finds.ipl2019.Model.TeamModel;
import sonu.finds.ipl2019.R;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<String>> mStringListHashMap;
    private String[] mListHeaderGroup;
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<TeamModel>> expandableListDetail;
    private static final String TAG = "MyExpandableListAdapter";
    // 4 Child types
    private static final int squad_layout =R.layout.team_squade_layout;
    private static final int result_layout = R.layout.custom_results_layout;
    private static final int schdeule_layout = R.layout.custom_schdule_layout;

    // 3 Group types
    private static final int GROUP_TYPE_1 = 0;
    private static final int GROUP_TYPE_2 = 1;
    private static final int  GROUP_TYPE_3 = 2;

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return super.getCombinedChildId(groupId, childId);
    }

    public MyExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<TeamModel>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }
//
//    public MyExpandableListAdapter(HashMap<String, List<String>> stringListHashMap) {
//        mStringListHashMap = stringListHashMap;
//        mListHeaderGroup = mStringListHashMap.keySet().toArray(new String[0]);
//    }


    @Override
    public int getGroupCount() {

        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    return expandableListDetail.get(expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandableListTitle.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        TeamModel model = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
         return  model.getPlayer_name();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_group, parent, false);

        }
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(String.valueOf(getGroup(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
         String incoming_text = (String) getChild(groupPosition, childPosition);
        Log.d(TAG, "getChildView: incoming text"+incoming_text);
        if (convertView == null) {
//            if (childPosition ==0 && groupPosition == 0){
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_results_layout, parent, false);
//
//            }
//            if (childPosition==0 && groupPosition ==1){
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_schdule_layout, parent, false);
//
//            }
//            if (childPosition==0 && groupPosition == 2){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_squade_layout, parent, false);


//            }

        }

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}

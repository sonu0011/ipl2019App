package sonu.finds.ipl2019.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sonu.finds.ipl2019.Adapter.MyExpandableListAdapter;
import sonu.finds.ipl2019.R;

public class TeamActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        expandableListView =findViewById(R.id.expandible_listview);
        HashMap<String, List<String>> item = new HashMap<>();

        ArrayList<String> linuxGroups = new ArrayList<>();
        linuxGroups.add("Ubuntu");
        item.put("SQUAD", linuxGroups);
        item.put("SCHDULE", linuxGroups);
        item.put("HIGHEST SCORE", linuxGroups);
        item.put("LOWEST SCORE", linuxGroups);
        item.put("TOTAL FOURS", linuxGroups);
        item.put("TOTAL SIXES", linuxGroups);
        item.put("RESULTS", linuxGroups);
        item.put("MOST FOURS / INNING", linuxGroups);
        item.put("MOST SIXES / INNING", linuxGroups);
        item.put("TOTAL WICKETS", linuxGroups);
        item.put("MOST WICKETS / INNING", linuxGroups);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(item);
        expandableListView.setAdapter(adapter);

    }
}

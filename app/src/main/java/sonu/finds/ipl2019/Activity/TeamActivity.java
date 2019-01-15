package sonu.finds.ipl2019.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sonu.finds.ipl2019.API.Constants;
import sonu.finds.ipl2019.API.MySingletonClass;
import sonu.finds.ipl2019.Adapter.DrawerAdapter;
import sonu.finds.ipl2019.Adapter.TeamItemAdapter;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.Model.TeamItemModel;
import sonu.finds.ipl2019.R;

public class TeamActivity extends AppCompatActivity {
    private static final String TAG = "TeamActivity";
    int team_id;
    private RecyclerView recyclerView;
    private String titel, coach, captain, bg_image;
    private TextView title_t, coach_t, captain_t, captain_title, coach_title;
    ImageView imageView;
    TeamItemAdapter adapter;
    List<TeamItemModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        team_id = getIntent().getIntExtra("team_id", 0);
        title_t = findViewById(R.id.team_toolbar_title);
        coach_t = findViewById(R.id.team_coach_title_value);
        imageView = findViewById(R.id.team_bg_image);
        captain_t = findViewById(R.id.team_captain_title_value);
        titel = getIntent().getStringExtra("title");
        coach = getIntent().getStringExtra("coach");
        captain = getIntent().getStringExtra("captain");
        captain_title = findViewById(R.id.team_captain_title);
        coach_title = findViewById(R.id.team_coach_title);

        title_t.setText(titel);
        coach_t.setText(coach);
        captain_t.setText(captain);
        switch (team_id) {
            case 1:
                imageView.setImageResource(R.drawable.csk_bg);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dc_bg);
                break;
            case 3:
                imageView.setImageResource(R.drawable.kxip_bg);
                break;
            case 4:
                imageView.setImageResource(R.drawable.kkr_bg);
                break;
            case 5:
                imageView.setImageResource(R.drawable.mi_bg);
                coach_t.setTextColor(Color.BLACK);
                captain_t.setTextColor(Color.BLACK);
                captain_title.setTextColor(Color.BLACK);
                coach_title.setTextColor(Color.BLACK);
                break;
            case 6:
                imageView.setImageResource(R.drawable.rr_bg);
                break;
            case 7:
                imageView.setImageResource(R.drawable.rcb_bg);
                break;
            case 8:
                imageView.setImageResource(R.drawable.srh_bg);
                break;


        }
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.team_recycleview);
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(TeamActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String team_highest_scorer = object.getString("team_highest_scorer");
                                String team_lowest_score = object.getString("team_lowest_score");
                                String team_total_fours = object.getString("team_total_fours");
                                String team_total_sixes = object.getString("team_total_sixes");
                                String team_most_four_inning = object.getString("team_most_four_inning");
                                String team_most_sixes_inning = object.getString("team_most_sixes_inning");
                                String team_most_wickets_inning = object.getString("team_most_wickets_inning");
                                String team_total_wickets = object.getString("team_total_wickets");
                                String iteam_title = object.getString("iteam_title");
                                list.add(new TeamItemModel(team_highest_scorer, team_lowest_score,
                                        team_total_fours, team_total_sixes, team_most_sixes_inning,
                                        team_most_four_inning, team_most_wickets_inning, team_total_wickets,
                                        iteam_title));

                            }
                            adapter = new TeamItemAdapter(list, TeamActivity.this, 1,team_id);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("getTeamItemDetails", "yes");
                map.put("team_id", String.valueOf(team_id));
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(TeamActivity.this).addToRequestQuee(stringRequest);


    }

    public static class Utility {
        static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            return (int) (dpWidth / 180);
        }
    }
}

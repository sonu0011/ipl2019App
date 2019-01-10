package sonu.finds.ipl2019.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

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
import sonu.finds.ipl2019.Adapter.MyExpandableListAdapter;
import sonu.finds.ipl2019.Model.DrawerFullModel;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.Model.TeamModel;
import sonu.finds.ipl2019.R;

public class TeamActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    HashMap<String, List<TeamModel>> squad,schdule,team,result;;
    ArrayList<TeamModel> squadlist,scheulelist,resultslist;
    private static final String TAG = "TeamActivity";
    int team_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        team_id =getIntent().getIntExtra("team_id",0);
        Log.d(TAG, "onCreate:  team_id is "+team_id);
        expandableListView = findViewById(R.id.expandible_listview);
        squad = new HashMap<>();
        scheulelist =new ArrayList<>();
        squadlist =new ArrayList<>();
        resultslist =new ArrayList<>();
        LoadTeamDetails("SQUAD","squad_request",squadlist);
        LoadTeamDetails("SCHEDULE","schdule_request",scheulelist);
        LoadTeamDetails("RESULTS","results_request",resultslist);


    }

    private void LoadTeamDetails(final String grouptitle, final String request_type, final List<TeamModel>list) {
        StringRequest team_request = new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                if (grouptitle.equals("SQUAD")) {
                                    String player_name = object.getString("player_name");
                                    String player_image = object.getString("player_image");
                                    list.add(new TeamModel(player_name, player_image));


                                }

                                if (grouptitle.equals("SCHEDULE")) {
                                    String match_No = object.getString("match_no");
                                    String team1_Image = object.getString("team1_image");
                                    String team2_Image = object.getString("team2_image");
                                    String date_Time = object.getString("date_time");
                                    String Venu = object.getString("venue");
                                    list.add(new TeamModel(match_No, team1_Image, team2_Image, date_Time, Venu));


                                }
                                if (grouptitle.equals("RESULTS")) {
                                    String match_No = object.getString("match_no");
                                    String results_Declare = object.getString("result_declare");
                                    String team1_Name = object.getString("team1Name");
                                    String team2_Name = object.getString("team2Name");
                                    String highlights_link = object.getString("highlights_link");
                                    String date_time = object.getString("date_time");
                                    String score_team1_score = object.getString("score_team1_score");
                                    String over_team1_over = object.getString("over_team1_over");
                                    String score_team2_score = object.getString("score_team2_score");
                                    String over_team2_over = object.getString("over_team2_over");

                                    list.add(new TeamModel(match_No, results_Declare, team1_Name, team2_Name,
                                            highlights_link, date_time, score_team1_score, over_team1_over,
                                            score_team2_score, over_team2_over));
                                }
                                    squad.put(grouptitle, list);
                                    List<String> childval = new ArrayList<String>(squad.keySet());
                                    MyExpandableListAdapter adapter = new MyExpandableListAdapter(TeamActivity.this, childval, squad);
                                    expandableListView.setAdapter(adapter);
//                                    squad.put("HIGHEST SCORE", list);
//                                    squad.put("LOWEST SCORE", list);
//                                    squad.put("TOTAL FOURS", list);
//                                    squad.put("TOTAL SIXES", list);
//                                    squad.put("MOST FOUR / INNING", list);
//                                    squad.put("MOST SIXES / INNING", list);
//                                    squad.put("TOTAL WICKETS", list);
//                                    squad.put("MOST WICKETS / INNING", list);
                            }
                        }catch (JSONException e) {
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
                map.put("full_team_details", "yes");
                map.put("request_type",request_type);
                map.put("team_id",String.valueOf(team_id));
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(TeamActivity.this).addToRequestQuee(team_request);

    }


}

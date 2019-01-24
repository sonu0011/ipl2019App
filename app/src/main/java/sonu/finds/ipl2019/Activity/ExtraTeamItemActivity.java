package sonu.finds.ipl2019.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import sonu.finds.ipl2019.Adapter.TeamItemAdapter;
import sonu.finds.ipl2019.Model.TeamItemModel;
import sonu.finds.ipl2019.R;
import sonu.finds.ipl2019.Utills.CustomToast;

public class ExtraTeamItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  String request_type;
    private List<TeamItemModel> list;
    private TeamItemAdapter adapter;
    int team_id;
    private static final String TAG = "ExtraTeamItemActivity";
    private BroadcastReceiver broadcastReceiver;
    private static boolean aBoolean;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager manager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info =  manager.getActiveNetworkInfo();
                if(info == null || !info.isConnected())
                {
                    aBoolean = false;
                    Log.d(TAG, "onReceive: no interner connection");
                    setContentView(R.layout.home_activity_no_internet_connection);
                    TextView button = findViewById(R.id.no_internet_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    });


                }
                else {
                    if (!aBoolean || count == 0) {
                        aBoolean = true;
                        count = count + 1;

                        InitAndFetchData();


                    }



                }

            }
        };
        registerReceiver(broadcastReceiver,intentFilter);


    }

    private void InitAndFetchData() {
        setContentView(R.layout.activity_extra_team_item);
        recyclerView = findViewById(R.id.extra_item_recycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExtraTeamItemActivity.this);
        team_id = getIntent().getIntExtra("team_id",0);
        request_type = getIntent().getStringExtra("request_type");
        if (request_type.equals("Squad")){
            recyclerView.setLayoutManager(new GridLayoutManager(ExtraTeamItemActivity.this,3));
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager(ExtraTeamItemActivity.this));
        }
        list = new ArrayList<>();
        Log.d(TAG, "onCreate: team id is "+team_id);
        Log.d(TAG, "onCreate: request type is "+request_type);
        getSupportActionBar().setTitle(request_type);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StringRequest team_request = new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);

                        if (response.equals("[]")){
                            new CustomToast(ExtraTeamItemActivity.this).showDialog();

                            return;
                        }
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                if (request_type.equals("Squad")) {
                                    String player_name = object.getString("player_name");
                                    String player_image = object.getString("player_image");
                                    list.add(new TeamItemModel(player_name, player_image));


                                }

                                if (request_type.equals("Schedule")) {
                                    String match_No = object.getString("match_no");
                                    String team1_Image = object.getString("team1_image");
                                    String team2_Image = object.getString("team2_image");
                                    String date_Time = object.getString("date_time");
                                    String Venu = object.getString("venue");
                                    list.add(new TeamItemModel(match_No, team1_Image, team2_Image, date_Time, Venu));


                                }
                                if (request_type.equals("Highlights")) {
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

                                    list.add(new TeamItemModel(match_No, results_Declare, team1_Name, team2_Name,
                                            highlights_link, date_time, score_team1_score, over_team1_over,
                                            score_team2_score, over_team2_over, 1));
                                }

                            }
                            if (request_type.equals("Squad")) {
                                adapter = new TeamItemAdapter(list, ExtraTeamItemActivity.this, 2, team_id);
                                recyclerView.setAdapter(adapter);
                            }
                            if (request_type.equals("Schedule")) {
                                adapter = new TeamItemAdapter(list, ExtraTeamItemActivity.this, 3, team_id);
                                recyclerView.setAdapter(adapter);
                            }
                            if (request_type.equals("Highlights")) {
                                adapter = new TeamItemAdapter(list, ExtraTeamItemActivity.this, 4, team_id);
                                recyclerView.setAdapter(adapter);
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
        MySingletonClass.getMySingletonClass(ExtraTeamItemActivity.this).addToRequestQuee(team_request);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

}

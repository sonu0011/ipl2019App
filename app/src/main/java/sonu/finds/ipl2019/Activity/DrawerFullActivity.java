package sonu.finds.ipl2019.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sonu.finds.ipl2019.API.Constants;
import sonu.finds.ipl2019.API.MySingletonClass;
import sonu.finds.ipl2019.Adapter.DrawerFullAdapter;
import sonu.finds.ipl2019.Model.DrawerFullModel;
import sonu.finds.ipl2019.Model.DrawerFullTableModel;
import sonu.finds.ipl2019.R;

public class DrawerFullActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<DrawerFullModel> list;
    List<DrawerFullTableModel> list1;
    DrawerFullAdapter adapter;
    String toolbartext;
    TextView toolbar;
    TableLayout tableLayout;
    private static final String TAG = "DrawerFullActivity";
    RelativeLayout relativeLayout;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void InitAndFetchData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbartext = getIntent().getStringExtra("drawer_heading");
        if (toolbartext.equals("SCHEDULE") || toolbartext.equals("HIGHLIGHTS")) {
            setContentView(R.layout.activity_drawer_full);
            init();
        } else {
            setContentView(R.layout.activity_drawer_full1);

            toolbartext = getIntent().getStringExtra("drawer_heading");

            init1();
            addHeaders();
        }


        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (list1 == null) {
                                list.clear();
                            } else {
                                list1.clear();
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (toolbartext.equals("SCHEDULE")) {
                                    String match_No = object.getString("match_no");
                                    String team1_Image = object.getString("team1_image");
                                    String team2_Image = object.getString("team2_image");
                                    String date_Time = object.getString("date_time");
                                    String Venu = object.getString("venue");
                                    list.add(new DrawerFullModel(match_No, team1_Image, team2_Image, date_Time, Venu));
                                }
                                if (toolbartext.equals("HIGHLIGHTS")) {
                                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.LightBlue));
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

                                    list.add(new DrawerFullModel(match_No, results_Declare, team1_Name, team2_Name,
                                            highlights_link, date_time, score_team1_score, over_team1_over,
                                            score_team2_score, over_team2_over));


                                }
                                if (toolbartext.equals("POINTS TABLE")) {
                                    String team_Name = object.getString("team_Name");
                                    String match_Played = object.getString("match_Played");
                                    String match_Won = object.getString("match_Won");
                                    String match_Lost = object.getString("match_Lost");
                                    String match_Tied = object.getString("match_Tied");
                                    String no_Result = object.getString("no_Result");
                                    String points = object.getString("points");
                                    String net_run_rate = object.getString("net_run_rate");
                                    list1.add(new DrawerFullTableModel(String.valueOf(jsonArray
                                            .length() - i), team_Name, match_Played, match_Won, match_Lost,
                                            match_Tied, no_Result, points, net_run_rate));


                                }
                                if (toolbartext.equals("MOST VALUABLE PLAYER")) {
                                    String player_name = object.getString("player_name");
                                    String team_name = object.getString("team_name");
                                    String val_points = object.getString("val_points");
                                    String matches_played = object.getString("matches_played");
                                    String val_wickets = object.getString("val_wickets");
                                    String val_dots = object.getString("val_dots");
                                    String val_fours = object.getString("val_fours");
                                    String val_sixes = object.getString("val_sixes");
                                    String val_catches = object.getString("val_catches");
                                    String val_stumping = object.getString("val_stumping");
                                    list1.add(new DrawerFullTableModel(String.valueOf(i + 1), player_name, team_name,
                                            val_points, matches_played, val_wickets, val_dots, val_fours, val_sixes,
                                            val_catches, val_stumping));

                                }
                                if (toolbartext.equals("FAIR PLAY AWARD")) {
                                    String team_name = object.getString("team_name");
                                    String matches_played = object.getString("matches_played");
                                    String points = object.getString("points");
                                    String fp_team_avg = object.getString("fp_team_avg");
                                    list1.add(new DrawerFullTableModel(String.valueOf(i + 1),
                                            team_name, matches_played, points, fp_team_avg));


                                }
                                if (toolbartext.equals("MAN OF THE MATCH  AWARDS")) {
                                    String player_name = object.getString("player_name");
                                    String team_logo = object.getString("team_logo");
                                    String matches_played = object.getString("matches_played");
                                    String awards_count = object.getString("awards_count");
                                    list1.add(new DrawerFullTableModel(String.valueOf(i + 1), player_name,
                                            team_logo, matches_played, awards_count, 1));


                                }


                            }
                            if (toolbartext.equals("HIGHLIGHTS")) {
                                adapter = new DrawerFullAdapter(2, list, DrawerFullActivity.this);
                                recyclerView.setAdapter(adapter);

                            }
                            if (toolbartext.equals("SCHEDULE")) {
                                adapter = new DrawerFullAdapter(1, list, DrawerFullActivity.this);
                                recyclerView.setAdapter(adapter);

                            }
                            if (!toolbartext.equals("HIGHLIGHTS") && !toolbartext.equals("SCHEDULE")){
                                addData();
                            }


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
                Log.d(TAG, "getParams: "+toolbartext);
                map.put("get_drawer_full_details", "yes");
                map.put("drawer_item", toolbartext);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(DrawerFullActivity.this).addToRequestQuee(stringRequest);

    }

    private void addData() {
        Log.d(TAG, "addData: " + list1.size());
        int numCompanies = list1.size();
        TableLayout tl = findViewById(R.id.tablelayout1);

        if (toolbartext.equals("POINTS TABLE")) {
            for (int i = list1.size() - 1; i >= 0; i--) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());

                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPosition()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getTeam_nme()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_won()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_lost()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_tied()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getNo_results()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPoints()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getNet_run_rate()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tl.addView(tr, getTblLayoutParams());

            }
        } else {

            for (int i = 0; i < numCompanies; i++) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());

                if (toolbartext.equals("MOST VALUABLE PLAYER")) {
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPosition()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPlayer_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getTeam_nme()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPoints()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getWckts()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getDots()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getFours()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getSixes()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getCatches()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getStump()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tl.addView(tr, getTblLayoutParams());
                }
                if (toolbartext.equals("FAIR PLAY AWARD")) {
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPosition()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getTeam_nme()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPoints()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getFair_play_avg()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tl.addView(tr, getTblLayoutParams());
                }
                if (toolbartext.equals("MAN OF THE MATCH  AWARDS")) {
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPosition()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getPlayer_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                   tr.addView(getImageView(String.valueOf(list1.get(i).getTeam_logo())));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getMatch_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tr.addView(getTextView(i + 1, String.valueOf(list1.get(i).getAwards_count()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                    tl.addView(tr, getTblLayoutParams());

                }

            }

        }
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setTextSize(11);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setGravity(Gravity.CENTER);

        return tv;
    }
    private ImageView getImageView(String url){
        ImageView imageView =new ImageView(this);
        //imageView.setPadding(5,5,5,5);
        Glide.with(this).load(url).into(imageView);

        return imageView;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }


    private void addHeaders() {

        toolbartext = getIntent().getStringExtra("drawer_heading");
        TableLayout tl = findViewById(R.id.tablelayout1);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
       // tr.addView(getTextView(0, "TEAM", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        if (toolbartext.equals("POINTS TABLE")) {
            tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "WON", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "LOST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "TIED", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "NR", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "PTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "NRR", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        }
        if (toolbartext.equals("MOST VALUABLE PLAYER")) {
            tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));

            tr.addView(getTextView(0, "TEAM", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "PTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "WKTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "DOTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "4S", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "6S", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "CATCH", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "STMP", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (toolbartext.equals("FAIR PLAY AWARD")) {
            Log.d(TAG, "addHeaders: "+toolbartext);
            tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "TEAM", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "PTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "AVG PTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (toolbartext.equals("MAN OF THE MATCH  AWARDS")) {
            tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
           tr.addView(getTextView(0, "TEAM", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "AWARDS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        }

        tl.addView(tr, getTblLayoutParams());

    }

    private void init1() {
        tableLayout = findViewById(R.id.tablelayout1);
        toolbar = findViewById(R.id.full_drawer_table_toolbar);
        toolbar.setText(toolbartext);
        list1 = new ArrayList<>();
    }

    private void init() {
        relativeLayout = findViewById(R.id.full_drawer_relative_layout);
        toolbartext = getIntent().getStringExtra("drawer_heading");
        list = new ArrayList<>();
        toolbar = findViewById(R.id.full_drawer_toolbar_text);
        toolbar.setText(toolbartext);
        recyclerView = findViewById(R.id.full_drawer_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    public void backpressed(View view) {
        onBackPressed();
    }

    public void GoToBack(View view) {
        onBackPressed();
    }
}

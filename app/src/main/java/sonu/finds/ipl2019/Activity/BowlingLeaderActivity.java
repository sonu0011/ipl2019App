package sonu.finds.ipl2019.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import sonu.finds.ipl2019.Adapter.BowlingLeaderAdapter;
import sonu.finds.ipl2019.Model.BowlingLeaderModel;
import sonu.finds.ipl2019.R;

public class BowlingLeaderActivity extends AppCompatActivity {
    String batting_heading, team;
    private RecyclerView recyclerView;
    private static final String TAG = "BowlingLeaderActivity";
    TextView back, title;
    BowlingLeaderAdapter adapter;
    GridLayoutManager staggeredGridLayoutManager;
    List<BowlingLeaderModel> modelList, modelList1;
    String teamname, player_name, match, over_bowled, runs_given, total_wickets, avg, economy, strike_rate, ball_bowled, four_overs;
    int innings, four_wick, five_wick;
    private BroadcastReceiver broadcastReceiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                    InitAndFetchData();


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

    @SuppressLint("NewApi")
    private void InitAndFetchData() {
        batting_heading = getIntent().getStringExtra("bowling_heading");
        Log.d(TAG, "onCreate: "+batting_heading);

        if (batting_heading.equals("Most Maiden Overs") || batting_heading.equals("Most Dot Balls")
                || batting_heading.equals("Fastest Ball")) {
            setContentView(R.layout.activity_bowling_leader);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


            init();
            // fetch data
            fetchData();


        } else {
            setContentView(R.layout.activity_bowling_leader1);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            addHeaders();
            fetchTableData();

            return;

        }


    }

    private void fetchTableData() {

        batting_heading = getIntent().getStringExtra("batting_heading");
        StringRequest sr = new StringRequest(StringRequest.Method.POST,
                Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null){
                            Toast.makeText(BowlingLeaderActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            modelList1.clear();
                           for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                int team_id = object.getInt("team_id");
////                                player_name = object.getString("name");
////                                teamname = setTeamName(team_id);
////                                match = object.getString("matches_played");
////                                innings = object.getInt("innings");
////                                over_bowled = object.getString("over_bowled");
////                                runs_given = object.getString("runs_given");
////                                total_wickets = object.getString("total_wickets");
////                                avg = object.getString("average");
////                                economy = object.getString("economy");
////                                strike_rate = object.getString("strike_rate");
////                                four_wick = object.getInt("four_wickets_count");
////                                five_wick = object.getInt("five_wickets_count");
////                                ball_bowled = object.getString("ball_bowled");
////                                four_overs = object.getString("four_overs");

                                if (batting_heading.equals("Best Strike Rate")) {
                                    player_name = object.getString("name");
                                     setTeamName(team_id);
                                    ball_bowled = object.getString("ball_bowled");
                                    total_wickets = object.getString("total_wickets");
                                    strike_rate = object.getString("strike_rate");
                                    modelList1.add(new BowlingLeaderModel(i + 1, player_name, team
                                            , Integer.valueOf(ball_bowled), total_wickets, strike_rate));
                                    Log.d(TAG, "onResponse: best strike rate"+modelList1.size());

                                }
                                if (batting_heading.equals("Best Economy Rate")) {
                                    player_name = object.getString("name");
                                     setTeamName(team_id);
                                    runs_given = object.getString("runs_given");
                                    over_bowled = object.getString("over_bowled");
                                    economy = object.getString("economy");
                                    modelList1.add(new BowlingLeaderModel(player_name, i + 1, team,
                                            Integer.valueOf(over_bowled), runs_given, economy));

                                }
                                if (batting_heading.equals("Best Bowling Average")) {
                                    player_name = object.getString("name");
                                      setTeamName(team_id);
                                    runs_given = object.getString("runs_given");
                                    total_wickets = object.getString("total_wickets");
                                    avg = object.getString("average");
                                    modelList1.add(new BowlingLeaderModel(player_name, i + 1, team,
                                            total_wickets, runs_given, avg));
                                }
                                if (batting_heading.equals("Best Bowling Innings")) {
                                    player_name = object.getString("name");
                                     setTeamName(team_id);
                                    match = object.getString("matches_played");
                                    innings = object.getInt("innings");
                                    over_bowled = object.getString("over_bowled");
                                    runs_given = object.getString("runs_given");
                                    total_wickets = object.getString("total_wickets");
                                    avg = object.getString("average");
                                    economy = object.getString("economy");
                                    strike_rate = object.getString("strike_rate");
                                    modelList1.add(new BowlingLeaderModel(i + 1, player_name, team,
                                            match, innings, 4, runs_given,
                                            total_wickets, avg, economy, strike_rate));


                                }
                                if (batting_heading.equals("Purple Cap")) {
                                    player_name = object.getString("name");
                                    setTeamName(team_id);
                                    match = object.getString("matches_played");
                                    innings = object.getInt("innings");
                                    over_bowled = object.getString("over_bowled");
                                    runs_given = object.getString("runs_given");
                                    total_wickets = object.getString("total_wickets");
                                    avg = object.getString("average");
                                    economy = object.getString("economy");
                                    strike_rate = object.getString("strike_rate");
                                    four_wick = object.getInt("four_wickets_count");
                                    five_wick = object.getInt("five_wickets_count");
                                    Log.d(TAG, "onResponse: purple cap +"+four_wick +five_wick);
                                    modelList1.add(new BowlingLeaderModel(i + 1, player_name, team, match,
                                            innings, Integer.valueOf(over_bowled), runs_given, total_wickets,
                                            economy, avg, strike_rate, String.valueOf(four_wick),String.valueOf(five_wick)));
                                }
                               if (batting_heading.equals("Most Hat Tricks")){
                                   player_name = object.getString("bow_name");
                                   int count = object.getInt("bow_hatric_count");
                                   setTeamName(team_id);
                                   modelList1.add(new BowlingLeaderModel(i+1,player_name,team,count,1));



                               }


                            }
                            addData();
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
                batting_heading = getIntent().getStringExtra("bowling_heading");

                Map<String, String> map = new HashMap<>();
                map.put("get_bowling_details", "yes");
                Log.d(TAG, "getParams: "+batting_heading);
                map.put("query", batting_heading);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(BowlingLeaderActivity.this).addToRequestQuee(sr);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addHeaders() {
        back = findViewById(R.id.bowling_leader_back);
        title = findViewById(R.id.bowling_leader_toolbar_text);
        batting_heading = getIntent().getStringExtra("bowling_heading");
        title.setText(batting_heading);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        modelList1 = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        TableLayout tl = findViewById(R.id.tablelayout1);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "TEAM", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        if (batting_heading.equals("Best Strike Rate")) {
            tr.addView(getTextView(0, "BALLS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "WKTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "SR", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        }
        if (batting_heading.equals("Best Economy Rate")) {
            tr.addView(getTextView(0, "RUNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "OVRS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "EC", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (batting_heading.equals("Best Bowling Average")) {
            tr.addView(getTextView(0, "RUNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "WKTS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "AVG", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (batting_heading.equals("Best Bowling Innings")) {
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "INNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "RUNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "OVRS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "AVG", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "EC", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "SR", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (batting_heading.equals("Purple Cap")) {
            tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "INNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "RUNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "OVRS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "AVG", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "EC", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "SR", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "4W", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
            tr.addView(getTextView(0, "5W", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        if (batting_heading.equals("Most Hat Tricks")){
            tr.addView(getTextView(0, "HT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        }
        tl.addView(tr, getTblLayoutParams());
    }


    private void setTeamName(int id) {
        switch (id) {
            case 1: {
                team = "CSK";
                break;
            }
            case 2: {
                team = "DC";
                break;
            }
            case 3: {
                team = "KXIP";
                break;
            }
            case 4: {
                team = "KKR";
                break;
            }
            case 5: {
                team = "MI";
                break;
            }
            case 6: {
                team = "RR";
                break;
            }
            case 7: {
                team = "RCB";
                break;
            }
            case 8: {
                team = "SRH";
                break;
            }


        }

    }

    /**
     * This function add the data to the table
     **/

    public void addData() {
        Log.d(TAG, "addData: " + modelList1.size());
        int numCompanies = modelList1.size();
        TableLayout tl = findViewById(R.id.tablelayout1);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            if (batting_heading.equals("Best Strike Rate")) {
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getName()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getTeam_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getBalls_bowled()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getWicket_taken()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getStrike_rate()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tl.addView(tr, getTblLayoutParams());
            }
            if (batting_heading.equals("Best Economy Rate")) {
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getName()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getTeam_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getRuns_given()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getOver_bowled()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getOver_bowled()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tl.addView(tr, getTblLayoutParams());
            }
            if (batting_heading.equals("Best Bowling Average")) {
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getName()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getTeam_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getRuns_given()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getWicket_taken()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getAverage()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tl.addView(tr, getTblLayoutParams());
            }
            if (batting_heading.equals("Best Bowling Innings")) {
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getName()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getTeam_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getMatches_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getInnings()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getRuns_given()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getOver_bowled()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getAverage()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getEconomy()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getStrike_rate()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tl.addView(tr, getTblLayoutParams());
            }
            if (batting_heading.equals("Purple Cap")) {
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getName()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getTeam_name()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getMatches_played()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getInnings()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getRuns_given()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getOver_bowled()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getAverage()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getEconomy()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getStrike_rate()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getFour_wick()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getFive_wick()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tl.addView(tr, getTblLayoutParams());
            }
            if (batting_heading.equals("Most Hat Tricks")){
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getPositon()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, modelList1.get(i).getName(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, modelList1.get(i).getTeam_name(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
                tr.addView(getTextView(i + 1, String.valueOf(modelList1.get(i).getHatric_count()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));

                tl.addView(tr, getTblLayoutParams());
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


    private void fetchData() {
        StringRequest request = new StringRequest(StringRequest.Method.POST,
                Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + batting_heading + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            modelList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bow_name = jsonObject.getString("name");
                                String bow_image = jsonObject.getString("image");
                                String bow_feat = jsonObject.getString("feat");
                                modelList.add(new BowlingLeaderModel(i + 1, bow_image, bow_name, bow_feat));

                            }
                            adapter = new BowlingLeaderAdapter(modelList, BowlingLeaderActivity.this, 1);
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
                map.put("get_bowling_details", "yes");
                map.put("query", batting_heading);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(BowlingLeaderActivity.this).addToRequestQuee(request);
    }


    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        staggeredGridLayoutManager = new GridLayoutManager(BowlingLeaderActivity.this, 2);
        batting_heading = getIntent().getStringExtra("bowling_heading");
        back = findViewById(R.id.bowling_leader_back);
        title = findViewById(R.id.bowling_leader_toolbar_text);
        recyclerView = findViewById(R.id.bowling_leader_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        modelList = new ArrayList<>();
        title.setText(batting_heading);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}


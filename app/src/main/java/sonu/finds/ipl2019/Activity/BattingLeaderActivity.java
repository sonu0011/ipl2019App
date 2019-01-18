package sonu.finds.ipl2019.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.GLU;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sonu.finds.ipl2019.API.Constants;
import sonu.finds.ipl2019.API.MySingletonClass;
import sonu.finds.ipl2019.Adapter.BattingLeadersAdapter;
import sonu.finds.ipl2019.Model.BattingLeadersModel;
import sonu.finds.ipl2019.Model.TabularModel;
import sonu.finds.ipl2019.R;
import sonu.finds.ipl2019.Utills.SharedPreference;

public class BattingLeaderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    String batting_heading;
    private RecyclerView recyclerView;
    List<BattingLeadersModel> list;
    BattingLeadersAdapter adapter;
    private static final String TAG = "BattingLeaderActivity";
    ImageView imageView,background;
    List<TabularModel> modelList;
    TextView name, feat, pos, nametext;

        private static boolean aBoolean;
        int count = 0;
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
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info == null || !info.isConnected()) {
                    aBoolean = false;
                    setContentView(R.layout.home_activity_no_internet_connection);
                    TextView button = findViewById(R.id.no_internet_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));

                        }
                    });
                } else {
                    if (!aBoolean || count ==0) {
                        aBoolean =true;
                        count = count + 1;

                        batting_heading = getIntent().getStringExtra("batting_heading");
                        if (batting_heading.equals("Best Batting Average") || batting_heading.equals("Orange Cap")) {
                            setContentView(R.layout.activity_batting_leader1);
                            Log.d(TAG, "onReceive: inside table");
                            addHeaders();
                            fetchTableData();
                            return;
                        } else {
                            Log.d(TAG, "onReceive:recycleview  ");
                            setContentView(R.layout.activity_batting_leader);
                        }
                        //initialize the things
                        init();
                        //fetch data
                        fetchData();


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

    private void fetchTableData() {

        modelList = new ArrayList<>();
        background =findViewById(R.id.tbl_bg);
        imageView = findViewById(R.id.table_image);
        nametext = findViewById(R.id.table_name);
        feat = findViewById(R.id.table_feat);
        batting_heading = getIntent().getStringExtra("batting_heading");
        StringRequest sr = new StringRequest(StringRequest.Method.POST,
                Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            modelList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name");
                                String match = object.getString("matches");
                                int innings = object.getInt("innings");
                                String runs = object.getString("runs");
                                String runs1 = runs.replace("Runs", "");
                                String hs = object.getString("hs");
                                String fours = object.getString("fours");
                                String fours1 = fours.replace("Fours", "");
                                String sixes = object.getString("sixes");
                                String sixes1 = sixes.replace("Sixes", "");
                                String fifty = object.getString("fifty");
                                String fifty1 = fifty.replace("Fifties", "");
                                String hundered = object.getString("hundered");
                                String hundered1 = hundered.replace("Centuries", "");
                                String hundered2 = hundered1.replace("Century", "");
                                String aver = object.getString("average");
                                if (i == 0) {
                                    String image = object.getString("image");
                                    Glide.with(BattingLeaderActivity.this).load(image).into(imageView);
                                    nametext.setText(name);
                                    int id =object.getInt("team_id");
                                    setBackgroundImages(id);
                                    if (batting_heading.equals("Best Batting Average")) {
                                        feat.setText(aver);
                                    }
                                    if (batting_heading.equals("Orange Cap")) {
                                        feat.setText(runs);
                                    }

                                }
                                modelList.add(new TabularModel(i + 1,
                                        name, match, innings, runs1, hs, aver,
                                        fifty1, hundered2, fours1, sixes1));

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
                Map<String, String> map = new HashMap<>();
                map.put("get_batting_details", "yes");
                map.put("query", batting_heading);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(BattingLeaderActivity.this).addToRequestQuee(sr);


    }

    private void setBackgroundImages(int id) {
        switch (id){
            case 1:{
                background.setImageResource(R.drawable.csk_bg);
                break;
            }
            case 2:{
                background.setImageResource(R.drawable.dc_bg);
                break;
            }
            case 3:{
                background.setImageResource(R.drawable.kxip_bg);
                break;
            }
            case 4 :{
                background.setImageResource(R.drawable.kkr_bg);
                break;
            }
            case 5:{
                background.setImageResource(R.drawable.mi_bg);
                break;
            }case 6:{
                background.setImageResource(R.drawable.rr_bg);
                break;
            }
            case 7:{
                background.setImageResource(R.drawable.rcb_bg);
                break;
            }
            case 8:{
                background.setImageResource(R.drawable.srh_bg);
                break;
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addHeaders() {
    toolbar = findViewById(R.id.mytoolbr);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setTitle(batting_heading);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        TableLayout tl = findViewById(R.id.tablelayout);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        tr.addView(getTextView(0, "POST", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "MAT", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "INNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));

            tr.addView(getTextView(0, "RUNS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));



        tr.addView(getTextView(0, "HS", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


            tr.addView(getTextView(0, "AVG", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));


        tr.addView(getTextView(0, "50", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "100", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "4s", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tr.addView(getTextView(0, "6s", Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
        tl.addView(tr, getTblLayoutParams());
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

    /**
     * This function add the data to the table
     **/

    public void addData() {
        Log.d(TAG, "addData: " + modelList.size());
        int numCompanies = modelList.size();
        TableLayout tl = findViewById(R.id.tablelayout);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, String.valueOf(modelList.get(i).getPosition()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, modelList.get(i).getName(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, modelList.get(i).getMatches_played(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, String.valueOf(modelList.get(i).getInning()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            if (batting_heading.equals("Orange Cap")) {
                tr.addView(getTextView(i + 1, modelList.get(i).getRuns_scored(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.Black)));

            } else {
                tr.addView(getTextView(i + 1, modelList.get(i).getRuns_scored(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));

            }
            tr.addView(getTextView(i + 1, modelList.get(i).getHs(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            if (batting_heading.equals("Best Batting Average")) {
                tr.addView(getTextView(i + 1, modelList.get(i).getAvg(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.Black)));

            } else {
                tr.addView(getTextView(i + 1, modelList.get(i).getAvg(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));

            }

            tr.addView(getTextView(i + 1, modelList.get(i).getFifty(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, modelList.get(i).getHund(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, modelList.get(i).getFours(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, modelList.get(i).getFifty(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tl.addView(tr, getTblLayoutParams());
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
        //tv.setOnClickListener(this);
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
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bat_name = jsonObject.getString("name");
                                String bat_image = jsonObject.getString("image");
                                String featt = jsonObject.getString("feat");
                                if (i == 0) {
                                    int id =jsonObject.getInt("team_id");
                                    setBackgroundImages(id);
                                    Glide.with(BattingLeaderActivity.this).load(bat_image).into(imageView);
                                    name.setText(bat_name);
                                    feat.setText(featt);
                                    pos.setText("1");
                                } else {
                                    list.add(new BattingLeadersModel(i + 1, bat_image, bat_name, featt));

                                }
                            }
                            if (batting_heading.equals("Most 50") || batting_heading.equals("Most 100")) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 2));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list, 0);
                                recyclerView.setAdapter(adapter);

                            } else if (batting_heading.equals("Most Fours") || batting_heading.equals("Most Sixes")) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 3));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list, 2);
                                recyclerView.setAdapter(adapter);

                            } else if (batting_heading.equals("Biggest Six") || batting_heading.equals("Best Strike Rate")) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 3));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list, 3);
                                recyclerView.setAdapter(adapter);

                            } else {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 3));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list, 1);
                                recyclerView.setAdapter(adapter);
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
                map.put("get_batting_details", "yes");
                map.put("query", batting_heading);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(BattingLeaderActivity.this).addToRequestQuee(request);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        batting_heading = getIntent().getStringExtra("batting_heading");
        toolbar =findViewById(R.id.mytoolbr);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(batting_heading);
        recyclerView = findViewById(R.id.batting_leader_recycleview);
        background  = findViewById(R.id.blurimage);
        recyclerView.setHasFixedSize(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        pos = findViewById(R.id.position);
        list = new ArrayList<>();
        imageView = findViewById(R.id.bl_image);
        name = findViewById(R.id.bl_name);
        feat = findViewById(R.id.bl_feat);
        pos = findViewById(R.id.position);
        Toast.makeText(this, "" + batting_heading, Toast.LENGTH_LONG).show();


    }
}

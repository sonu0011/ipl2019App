package sonu.finds.ipl2019.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import sonu.finds.ipl2019.Adapter.HomeAdapter;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;
import sonu.finds.ipl2019.Utills.CheckInternetConnection;
import sonu.finds.ipl2019.Utills.SharedPreference;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
     Toolbar toolbar;
     RecyclerView batting, bowling, team;
     List<HomeModel> list,list1,list3;
     HomeAdapter homeAdapter,homeAdapter1,homeAdapter2;
     int i=1;
     ImageView toggle_image;
     LinearLayoutManager batting_manager, bowling_manager;
     GridLayoutManager team_manager;
    private CheckInternetConnection checkInternetConnection;
    TextView internet_connection;
    BroadcastReceiver broadcastReceiver;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressBar progressBar;
    private static boolean firstConnect = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager manager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if(info == null || !info.isConnected())
                {
                    firstConnect= true;

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
                    if(firstConnect) {
                        Log.d(TAG, "onReceive: internet connection");

                        // do subroutines here
                        setContentView(R.layout.activity_home);
                        init();

                        //set Batting leaders details 2 for batting
                        setData("batting_leaders",batting,homeAdapter,list,2);

                        //set Bowling  Leaders details 3 for bowling
                        setData("bowling_leaders",bowling,homeAdapter1,list1,3);

                        //set Team Data 1 for team
                        setData("team_details",team,homeAdapter2,list3,i);
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                String newToken = instanceIdResult.getToken();
                                Log.e("newToken",newToken);
                                String token =  SharedPreference.getInstance(HomeActivity.this).getDeviceToken();
                                Log.d(TAG, "onSuccess: sharedpref_value"+token);
                                if (token == null){
                                    SharedPreference.getInstance(getApplicationContext()).saveDeviceToken(newToken);
                                    sendToken(newToken);

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.getMessage());
                            }
                        });

                        firstConnect = false;
                    }


                }

            }
        };
        registerReceiver(broadcastReceiver,intentFilter);


    }


    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
        super.onDestroy();
    }
    private void setData(final String parm, final RecyclerView mRecyclerView, final HomeAdapter adapter, final List<HomeModel>modelList, final int checkvalue) {

        StringRequest stringRequest =new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            modelList.clear();
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (checkvalue == 1) {
                                    String team_image = object.getString("team_image");
                                    int team_id = object.getInt("team_id");
                                    String team_heading = object.getString("team_heading");
                                    String team_captain = object.getString("team_captain");
                                    String team_coach = object.getString("team_coach");
                                    modelList.add(new HomeModel(team_image,team_id,team_heading,team_captain,team_coach));

                                } else {
                                    String image = object.getString("image");
                                    String heading = object.getString("heading");
                                    modelList.add(new HomeModel(image, heading));

                                }
                            }
                            setAdapter(mRecyclerView,adapter,modelList,checkvalue);
                            progressBar.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map =new HashMap<>();
                map.put(parm,"yes");
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(HomeActivity.this).addToRequestQuee(stringRequest);
    }

    private void setAdapter(RecyclerView recyclerView1, HomeAdapter adapter, List<HomeModel> modelList,int value) {
        if (value == 1) {
            adapter = new HomeAdapter(HomeActivity.this, modelList, value);
            recyclerView1.setAdapter(adapter);

        } if (value ==2){
            adapter = new HomeAdapter(HomeActivity.this, modelList,2);
            recyclerView1.setAdapter(adapter);
        }
        if (value == 3){
            adapter = new HomeAdapter(HomeActivity.this, modelList,3);
            recyclerView1.setAdapter(adapter);

        }
    }

    private void init() {
        progressBar = findViewById(R.id.myProgressbar);
        progressBar.setBackgroundColor(Color.BLACK);
         toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        batting =findViewById(R.id.batting_recyclerview);
        bowling =findViewById(R.id.bowlingrecycleview);
        team =findViewById(R.id.teamrecycleview);
        list =new ArrayList<>();
        list1 =new ArrayList<>();
        list3 =new ArrayList<>();
        batting_manager =new LinearLayoutManager(HomeActivity.this);
        batting_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bowling_manager =new LinearLayoutManager(HomeActivity.this);
        bowling_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        team_manager =new GridLayoutManager(HomeActivity.this,3);
        batting.setHasFixedSize(true);
        batting.setLayoutManager(batting_manager);
        bowling.setHasFixedSize(true);
        bowling.setLayoutManager(bowling_manager);
        team.setHasFixedSize(true);
        team.setLayoutManager(team_manager);
        toggle_image =findViewById(R.id.home_custom_toolbar_toggle_icon);
        toggle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,DrawerActivity.class));
                overridePendingTransition(R.anim.left_to_right,
                        R.anim.right_to_left);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


    }

    public void sendToken(final String token) {
        Log.d(TAG, "sendToken: send token call");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: token response " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + obj.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: token response" + error.toString());

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("device_name", token);
                return params;
            }
        };
        MySingletonClass.getMySingletonClass(this).addToRequestQuee(stringRequest);
    }



}


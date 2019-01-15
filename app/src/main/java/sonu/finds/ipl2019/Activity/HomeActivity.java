package sonu.finds.ipl2019.Activity;

import android.content.Intent;
import android.os.Build;
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
import sonu.finds.ipl2019.Adapter.HomeAdapter;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        //Initialise the things
        init();

        //set Batting leaders details 2 for batting
        setData("batting_leaders",batting,homeAdapter,list,2);

        //set Bowling  Leaders details 3 for bowling
        setData("bowling_leaders",bowling,homeAdapter1,list1,3);

        //set Team Data 1 for team
        setData("team_details",team,homeAdapter2,list3,i);

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

    }
}


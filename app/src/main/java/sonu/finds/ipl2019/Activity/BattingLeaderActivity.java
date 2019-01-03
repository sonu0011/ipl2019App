package sonu.finds.ipl2019.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import sonu.finds.ipl2019.Adapter.BattingLeadersAdapter;
import sonu.finds.ipl2019.Model.BattingLeadersModel;
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;

public class BattingLeaderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    String batting_heading;
    private RecyclerView recyclerView;
    List<BattingLeadersModel> list;
    BattingLeadersAdapter adapter;
    private static final String TAG = "BattingLeaderActivity";
    ImageView imageView;
    TextView name, feat, pos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batting_leader);
        //initialize the things
        init();

        //fetch data
        fetchData();
    }

    private void fetchData() {
        StringRequest request = new StringRequest(StringRequest.Method.POST,
                Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+batting_heading + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bat_name = jsonObject.getString("name");
                                String bat_image = jsonObject.getString("image");
                                String featt = jsonObject.getString("feat");
                                if (i == 0) {
                                    Glide.with(BattingLeaderActivity.this).load(bat_image).into(imageView);
                                    name.setText(bat_name);
                                    feat.setText(featt);
                                } else {
                                    list.add(new BattingLeadersModel(i + 1, bat_image, bat_name, featt));

                                }
                            }
                            if (batting_heading.equals("Most 50") || batting_heading.equals("Most 100")) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 2));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list,0);
                                recyclerView.setAdapter(adapter);

                            }
                            else if (batting_heading.equals("Most Fours") || batting_heading.equals("Most Sixes")) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 3));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list,2);
                                recyclerView.setAdapter(adapter);

                            }
                            else {
                                recyclerView.setLayoutManager(new GridLayoutManager(BattingLeaderActivity.this, 3));

                                adapter = new BattingLeadersAdapter(BattingLeaderActivity.this, list, 1);
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
                map.put("get_batting_details", "yes");
                map.put("query", batting_heading);
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(BattingLeaderActivity.this).addToRequestQuee(request);
    }

    private void init() {
        batting_heading = getIntent().getStringExtra("batting_heading");
//        toolbar =findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(batting_heading);
        recyclerView = findViewById(R.id.batting_leader_recycleview);
        recyclerView.setHasFixedSize(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        list = new ArrayList<>();
        imageView = findViewById(R.id.bl_image);
        name = findViewById(R.id.bl_name);
        feat = findViewById(R.id.bl_feat);
        pos = findViewById(R.id.position);
        Toast.makeText(this, ""+batting_heading, Toast.LENGTH_LONG).show();


    }
}

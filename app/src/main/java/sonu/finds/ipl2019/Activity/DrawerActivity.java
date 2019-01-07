package sonu.finds.ipl2019.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

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
import sonu.finds.ipl2019.Model.HomeModel;
import sonu.finds.ipl2019.R;

public class DrawerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HomeModel> list;
    GridLayoutManager gridLayoutManager;
    private static final String TAG = "DrawerActivity";
    DrawerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView  = findViewById(R.id.drawer_recycleview);
        list =new ArrayList<>();
        gridLayoutManager =new GridLayoutManager(DrawerActivity.this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        StringRequest stringRequest =new StringRequest(StringRequest.Method.POST, Constants.REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            list.clear();
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String bg =object.getString("drawer_bg");
                                String icon =object.getString("drawer_icon");
                                String heading =object.getString("drawer_heading");
                                list.add(new HomeModel(heading,bg,icon));

                            }
                            adapter =new DrawerAdapter(list,DrawerActivity.this);
                            recyclerView.setAdapter(adapter);


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
                map.put("get_drawer_details","yes");
                return map;
            }
        };
        MySingletonClass.getMySingletonClass(DrawerActivity.this).addToRequestQuee(stringRequest);


    }

    public void BackPressed(View view) {
        onBackPressed();
    }
}

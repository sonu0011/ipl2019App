package sonu.finds.ipl2019.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import sonu.finds.ipl2019.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);


                finish();
            }
        }, 3000);
    }

}


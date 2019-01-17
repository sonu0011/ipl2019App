package sonu.finds.ipl2019.Utills;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

import sonu.finds.ipl2019.API.Constants;

//import sk.android.examscorer.Utills.Constants;

/**
 * Created by sonu on 17/10/18.
 */


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Constants.device_id =s;
        Log.d(TAG, "Refreshed token: " + s);
        storeToken(s);
    }

    private void storeToken(String token) {

       SharedPreference.getInstance(getApplicationContext()).saveDeviceToken(token);


    }
}
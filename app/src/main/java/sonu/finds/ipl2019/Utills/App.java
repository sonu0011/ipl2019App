package sonu.finds.ipl2019.Utills;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


/**
 * Created by sonu on 17/10/18.
 */


public class App extends Application {
    public static final String CHANNLE_ID ="channel1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificaitonChannel();
    }

    private void createNotificaitonChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel(
                    CHANNLE_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is channel 1");
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
}

package sonu.finds.ipl2019.Utills;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import sonu.finds.ipl2019.R;

public class CustomToast {
private Context context;

    public CustomToast(Context context) {
        this.context = context;
    }
    public  Toast GetToast(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast_layout,null);
         Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        return toast;
    }
}

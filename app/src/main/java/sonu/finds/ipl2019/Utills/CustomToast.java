package sonu.finds.ipl2019.Utills;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import sonu.finds.ipl2019.R;

public class CustomToast {
private Context context;
    private AlertDialog.Builder mbuilder;
    private AlertDialog alertDialog;

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
    public void showDialog(){
        mbuilder = new AlertDialog.Builder(context);
        mbuilder.setTitle("IPL 2019");
        mbuilder.setMessage("Please Wait for IPL to start");
       // View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        //mbuilder.setView(view);
        //mbuilder.setCancelable(false);
        mbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mbuilder.create();
        alertDialog = mbuilder.show();
    }
}

package sonu.finds.ipl2019.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonClass {
    private static  MySingletonClass mySingletonClass;
    private RequestQueue requestQueue;
    private  Context context;
    private MySingletonClass(Context mcontext){
        context =mcontext;
        requestQueue =getRequestQueue();

    }

    private RequestQueue getRequestQueue() {
        if (requestQueue ==null){
            requestQueue =Volley.newRequestQueue(context);
        }
        return  requestQueue;
    }
    public static synchronized MySingletonClass getMySingletonClass(Context ctx){
        if (mySingletonClass ==null){
            mySingletonClass =new MySingletonClass(ctx);
        }
        return mySingletonClass;
    }
    public<T>  void  addToRequestQuee(Request<T> request){

        requestQueue.add(request);
    }
}

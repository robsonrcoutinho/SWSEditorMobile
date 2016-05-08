package br.com.ifba.swseditormobile.request;

import android.content.Context;

import com.android.volley.RequestTickle;
import com.android.volley.toolbox.VolleyTickle;

/**
 * Created by Robson on 05/05/2016.
 */
public class RequestManager {
    private static RequestManager instance;
    private RequestProxy mRequestProxy;


    private RequestManager(Context context){
        mRequestProxy = new RequestProxy(context);

    }

    public RequestProxy doRequest(){
        return mRequestProxy;
    }

    public static synchronized RequestManager getInstance(Context context){
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    "getInstance(..)");
        }
        return instance;
    }

}

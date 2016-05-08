package br.com.ifba.swseditormobile.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.RequestTickle;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.VolleyTickle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Robson on 05/05/2016.
 */
public class RequestProxy {
    private static String TAG = RequestProxy.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private RequestTickle mRequestTickle;


    public RequestProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestTickle = VolleyTickle.newRequestTickle(context);
    }

    public List<String> buscaVolleyPlus(String param) {
        String REQUEST = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=";
        REQUEST += param;

        StringRequest request = new StringRequest(Method.GET, REQUEST, null, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "Application/json");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestTickle.add(request);

        NetworkResponse responseNet = mRequestTickle.start();
        List<String> lista = new ArrayList<>();
        if (responseNet.statusCode == 200) {
            String response = VolleyTickle.parseResponse(responseNet);
                if (!response.isEmpty()) {
                    String[] posicao = null;
                    for (int i = 0; i < response.length(); i++) {
                        posicao = response.split("http://");
                    }
                    for (int j = 0; j < posicao.length; j++) {
                        lista.add(posicao[j].toString());
                        Log.d(TAG, "lista : Onto VolleyPlus::" + lista.get(j) + ":");

                    }

                }
    }
        return lista;
    }

    public List<String> buscaParamVolleyPlus(String param) {
        String REQUEST = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=";
        REQUEST += param;


        StringRequest request = new StringRequest(Method.GET, REQUEST, null, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "Application/json");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestTickle.add(request);
        NetworkResponse responseNet = mRequestTickle.start();
        List<String> lista = new ArrayList<>();
        String response = VolleyTickle.parseResponse(responseNet);
        if (!response.isEmpty()) {
            String[] posicao = null;
            for (int i = 0; i < response.length(); i++) {
                posicao = response.split("http://");
            }
            for (int j = 0; j < posicao.length; j++) {
                lista.add(posicao[j].toString());
                Log.d(TAG, "lista : Onto VolleyPlus::" + lista.get(j) + ":");

            }
        }
        return lista;
    }




}
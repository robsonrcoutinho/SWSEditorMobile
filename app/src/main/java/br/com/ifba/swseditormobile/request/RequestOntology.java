package br.com.ifba.swseditormobile.request;


import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Robson on 12/04/2016.
 */
public class RequestOntology extends FragmentActivity {
    private static final String TAG = RequestOntology.class.getSimpleName();
    private static  String REQUEST = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=cat+dog";
    private String jsonResponse;
    private String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";


    private void recebeConsulta(String palavraChave){
        REQUEST+=palavraChave;
    }

    public static List<String> stringRequest() {
        final List<String> listaOnto = new ArrayList<>();
        final String a = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=teste";
        StringRequest sr = new StringRequest(Request.Method.GET,a, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] posicao = null;
                for(int i = 1 ; i<response.length();i++) {
                    posicao = response.split("http://");
                    Log.d(TAG, "lenght ::" + response.length() +":");
                }

                for(int j = 0 ; j<posicao.length; j++){
                    listaOnto.add(posicao[j].toString());
                    Log.d(TAG, "Teste ::" + listaOnto.get(j) +":");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, error.toString());

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Accept","Application/json");
                return params;
            }
        };
        RequestWebApplication.getInstance().addToRequestQueue(sr);
        return listaOnto;
    }



}

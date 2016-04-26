package br.com.ifba.swseditormobile.request;


import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
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
/*
    public List<String> buscaOntologia(){
        final List<String> listaURL = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("cat", "username");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,REQUEST,
                new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Erro recebimento JSON hrests");
                Log.d(TAG,error.toString());
            }
        })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String,String>();
                headers.put("Accept","Application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
               // headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
         }



        };

        RequestWebApplication.getInstance().addToRequestQueue(jsonObjReq);
       return listaURL;
    }


    public void vollyStringRequestForPost() {
        String a = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=cat+dog";
        JsonArrayRequest req = new JsonArrayRequest(a,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String teste = response.toString();
                        Log.d(TAG,teste);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error:" + error.toString());


            }
        }){

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String,String>();
        headers.put("Accept","Application/json");
        headers.put("Content-Type","application/x-www-form-urlencoded");
        return headers;
            }
        };
        RequestWebApplication.getInstance().addToRequestQueue(req);
    }

*/

    public void stringRequest() {
        String a = "http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=teste";
        StringRequest sr = new StringRequest(Request.Method.GET,a, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response.toString();
                Log.d(TAG, teste);

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
    }



}

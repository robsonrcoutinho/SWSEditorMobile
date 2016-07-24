package br.com.ifba.swseditormobile.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Robson on 21/07/2016.
 */
public class GetRequisicao extends AsyncTask<String, Void, List<String>> {
    private ProgressDialog progress;
    private Context mContext;
    private IParamsAsyncTask paramsAsyncTask;

    public GetRequisicao(Context ctx, IParamsAsyncTask paramsAsyncTask) {
        this.mContext = ctx;
        this.paramsAsyncTask = paramsAsyncTask;
    }

    public interface IParamsAsyncTask {
        public void processFinish(List<String> output);
    }

    @Override
    protected List<String> doInBackground(String... params) {
        String paramentro = params[0];
        Log.d("Novo Paramentro Busca: ", paramentro);
        String URLBusca ="http://watson.kmi.open.ac.uk/API/semanticcontent/keywords/?q=";
        URLBusca+=paramentro;
        Log.d("URL FULL",URLBusca);

        List<String> listaParametros = new ArrayList<>();

        String results = null;
        try {
            URL url = new URL(URLBusca);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "Application/json");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                } finally {
                    is.close();
                }

                results = sb.toString();
                /**/

                String[] posicao = null;
                for (int i = 0; i < results.length(); i++) {
                    posicao = results.split("http://");
                }
                for (int j = 0; j < posicao.length; j++) {
                    listaParametros.add(posicao[j].toString());
                }

                progress.dismiss();
                Log.d("RESULST", results);

            }
        } catch (Exception e) {
            progress.dismiss();
        }
        progress.dismiss();
        paramsAsyncTask.processFinish(listaParametros);
        return listaParametros;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(mContext);
        progress.setCancelable(false);
        progress.setMessage("Buscando...");
        progress.show();
    }
    }

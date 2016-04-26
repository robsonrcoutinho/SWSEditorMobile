package br.com.ifba.swseditormobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import br.com.ifba.swseditormobile.R;

/**
 * Created by Robson on 18/04/2016.
 */
public class RenderView extends AppCompatActivity {
    private WebView webview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_final);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        webview = (WebView) findViewById(R.id.webViewFinal);
        webview.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        String htmlRecebido = params.getString("html");

        webview.loadData(htmlRecebido,null,null);

    }
}

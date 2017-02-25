package br.com.ifba.swseditormobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.model.Ontologias;
import br.com.ifba.swseditormobile.model.PageHTML;
import br.com.ifba.swseditormobile.realmDB.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by #### on 18/04/2016.
 */
public class RenderView extends AppCompatActivity {
    private WebView webview;
    private Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_final);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.realm = RealmController.with(this).getRealm();
        RealmController.with(this).refresh();


        webview = (WebView) findViewById(R.id.webViewFinal);
        webview.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        String htmlRecebido = params.getString("html");
        insertPaginaHtml(htmlRecebido);

        webview.loadData(htmlRecebido,"text/html; charset=UTF-8",null);

    }


    public void insertPaginaHtml(String html){
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        PageHTML pageHTML = realm.createObject(PageHTML.class);
        pageHTML.setInformacoesPagina(html);
        Log.d("Gravado: ", html);
        realm.commitTransaction();
    }
}

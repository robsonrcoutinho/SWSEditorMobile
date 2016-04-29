package br.com.ifba.swseditormobile.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.support.v7.widget.SearchView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.ifba.swseditormobile.fragment.EditorDetailFragment;
import br.com.ifba.swseditormobile.fragment.FragmentAddressTag;
import br.com.ifba.swseditormobile.fragment.FragmentOperation;
import br.com.ifba.swseditormobile.fragment.FragmentServiceTag;
import br.com.ifba.swseditormobile.fragment.IFragmentInteractionListener;
import br.com.ifba.swseditormobile.fragment.PropertyListFragment;
import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.model.Operation;
import br.com.ifba.swseditormobile.request.RequestOntology;
import br.com.ifba.swseditormobile.model.ChildGrupo;
import br.com.ifba.swseditormobile.model.Group;
import br.com.ifba.swseditormobile.util.HTMLParser;
import br.com.ifba.swseditormobile.util.ToastManager;
import br.com.ifba.swseditormobile.viewexpandable.CarregaObjetos;
import br.com.ifba.swseditormobile.viewexpandable.ExpandableListAdapterView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        IFragmentInteractionListener, PropertyListFragment.Communicator, View.OnTouchListener, Handler.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CLICK_WV = 1;
    private static final int CLICK_URL = 2;
    private final Handler handler = new Handler(this);
    private WebView webview;
    private ProgressDialog progressDialog;
    private GestureDetector gestureDetector;

    private ExpandableListAdapterView expAdapter;
    private ExpandableListView expandList;

    List<Group> listDataGroup;
    HashMap<Group, List<ChildGrupo>> listDataChild;
    private String serviceString;
    private EditText etServiceHRests;

    private Group group;
    private  String httpAddress;
    private static String addressUrl;
    private static String addressObs;

    private ImageButton gravarHrestsBtn;
    private Spinner spinner;
    private TextView tvOntologyDescription;

    /*Operation*/
    private String tagNomeMetodo;
    private String tagTipoMetodo;
    private String tagParametroOperation;
    private String tagInputDinamico;
    private String  tagOutputDinamico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        gravarHrestsBtn = (ImageButton)findViewById(R.id.gravarDocumento);
        gravarHrestsBtn.setVisibility(View.GONE);

        tvOntologyDescription = (TextView)findViewById(R.id.tvOntologyDescription);
        tvOntologyDescription.setVisibility(View.GONE);

        spinner = (Spinner) findViewById(R.id.ontology);
        spinner.setVisibility(View.GONE);

        webview = (WebView) findViewById(R.id.webView2);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.w3.org/standards/semanticweb/");
        progressDialog = new ProgressDialog(this);

        expandList = (ExpandableListView) findViewById(R.id.exp_list);
        expandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        sv.setOnQueryTextListener(this);
        sv.setQueryHint("URL do serviço");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


    @Override
    public void Message(String message) {
        EditorDetailFragment detailfragment = (EditorDetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_Fragment);
        if (detailfragment != null && detailfragment.isInLayout())
            detailfragment.setText(message);
    }

    public void carregaExpandable(final String serviceNomeTroca, final String labelServiceTroca,
                                  final String descriptionServiceTroca) {
        gravarHrestsBtn.setVisibility(View.VISIBLE);

        listDataGroup = new ArrayList<>();
        listDataChild = new HashMap<>();
        group = new Group();
        group.setNameGroup("hRESTS");
        listDataGroup.add(group);

        listDataChild.put(listDataGroup.get(0), CarregaObjetos.preencheExpandable());
        expAdapter = new ExpandableListAdapterView(this, listDataGroup, listDataChild);
        expandList.setAdapter(expAdapter);

        expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                ChildGrupo chilPos = listDataChild.get(listDataGroup.get(groupPosition)).get(
                        childPosition);
                String selecionado = chilPos.getNomeChildGrupo().toString();

                switch (selecionado) {
                    case "Service":
                        Log.d(TAG, "Switch entrou em: " + selecionado);
                        FragmentServiceTag dialogFragment = new FragmentServiceTag();
                        dialogFragment.recebeTagService(serviceNomeTroca, labelServiceTroca, descriptionServiceTroca);
                        dialogFragment.show(getFragmentManager(), "Service");

                        break;
                    case "Address":
                        Log.d(TAG, "Switch entrou em: " + selecionado);
                        FragmentAddressTag fragmentAddressTag = new FragmentAddressTag();
                        fragmentAddressTag.recebeTagAddress(addressUrl, addressObs);
                        fragmentAddressTag.show(getFragmentManager(), "Address");
                        Log.d(TAG, "Testando PARAM ADRESS: " + addressUrl + addressObs);
                        break;
                    case "Operation":
                        FragmentOperation operation = new FragmentOperation();
                        operation.recebeOperation(tagNomeMetodo, tagTipoMetodo, tagParametroOperation,
                                tagInputDinamico, tagOutputDinamico);
                        operation.show(getFragmentManager(), "Operation");
                        break;

                }
                return false;
            }
        });


        gravarHrestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandList.setVisibility(View.GONE);
                gravarHrestsBtn.setVisibility(View.GONE);
                loadSpinner();


            }
        });
        /*
        gravarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTMLParser htmlCom = new HTMLParser();
                Bundle params = new Bundle();
                String html = htmlCom.montaHTML();
                params.putSerializable("html", html);
                Intent intent = new Intent(MainActivity.this, RenderView.class);
                intent.putExtras(params);
                startActivity(intent);
                Log.d(TAG, html);

            }
        });*/

    }

    /*
    * Usando immersive full_screen para expandir a exibição por toda tela do dispositivo
    * porém deixando o nav bar exibindo
    * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }


    @Override
    public boolean onQueryTextChange(String url) {
        Log.i(TAG, ".onQueryTextChange()" + url);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String url) {
        Log.i(TAG, ".onQueryTextSubmit()" + url);
        httpAddress = "http://" + url;
        addressUrl = httpAddress;

        progressDialog = ProgressDialog.show(this, "Carregando", "Aguarde um momento...");
        webview.setVerticalScrollBarEnabled(true);
        webview.setHorizontalScrollBarEnabled(true);

        webview.setOnTouchListener(this);

        gestureDetector = new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        Log.d(TAG, "02 Touch na View");
                        // if (e.getAction() ==  MotionEvent.ACTION_DOWN)
                        handler.sendEmptyMessageDelayed(CLICK_WV, 200);
                        return super.onDoubleTap(e);
                    }

                });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handler.sendEmptyMessage(CLICK_URL);
                return false;
            }

            @Override
            public void onPageFinished(WebView webView, String http) {
                webview.loadUrl(
                        "javascript:window.HtmlViewer.showHTML" +
                                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'");
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        webview.loadUrl(httpAddress);
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "01 Touch na View : " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_URL) {
            handler.removeMessages(CLICK_WV);
            return true;
        }
        if (msg.what == CLICK_WV) {
            showAlert();
        }
        return true;
    }

    public void showAlert() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_hrests, null);
        etServiceHRests = (EditText) alertLayout.findViewById(R.id.txtService);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        serviceString = etServiceHRests.getText().toString();
                        if (!TextUtils.isEmpty(serviceString)) {
                            carregaExpandable(serviceString, null, null);

                        } else{
                           ToastManager.show(getBaseContext(), "Por favor, insira nome do serviço",  ToastManager.WARNING);
                        }
                    }
                }

        );

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }

            );
            AlertDialog dialog = alert.create();
        dialog.show();
        }

    @Override
    public void onFragmentInteraction(String serviceName, String labelService,
                                      String descriptionService ) {
        carregaExpandable(serviceName, labelService, descriptionService);
    }

    @Override
    public void onFragmentInteractionAddress(String address, String addressO) {
        addressUrl = address;
        addressObs = addressO;
    }


    @Override
    public void onFragmentInteractionOperation(String tagNomeMetodo, String tagTipoMetodo, String tagParametroOperation,
                                               String tagInputDinamico,  String  tagOutputDinamico) {
        this.tagNomeMetodo = tagNomeMetodo;
        this.tagTipoMetodo =tagTipoMetodo;
        this.tagParametroOperation = tagParametroOperation;
        this.tagInputDinamico = tagInputDinamico;
        this.tagOutputDinamico =tagOutputDinamico;
    }

    public void loadSpinner(){

        List<String> lista = RequestOntology.stringRequest(); //new ArrayList<>();
        lista.add("Selecione Ontologia...");
        /* lista.add("onto02");
        lista.add("onto03");
        lista.add("onto04");
        RequestOntology ro = new RequestOntology();

        ro.stringRequest();*/
        //ro.buscaOntologia();

        spinner.setVisibility(View.VISIBLE);
        tvOntologyDescription.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}











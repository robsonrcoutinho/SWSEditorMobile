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
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.support.v7.widget.SearchView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.ifba.swseditormobile.fragment.EditorDetailFragment;
import br.com.ifba.swseditormobile.fragment.FragmentAddressTag;
import br.com.ifba.swseditormobile.fragment.FragmentOperationTag;
import br.com.ifba.swseditormobile.fragment.FragmentServiceTag;
import br.com.ifba.swseditormobile.fragment.IFragmentInteractionListener;
import br.com.ifba.swseditormobile.fragment.PropertyListFragment;
import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.model.InputSemantico;
import br.com.ifba.swseditormobile.model.Ontologias;
import br.com.ifba.swseditormobile.model.OutputSemantico;
import br.com.ifba.swseditormobile.model.ServiceSematico;
import br.com.ifba.swseditormobile.realmDB.RealmController;
import br.com.ifba.swseditormobile.realmDB.ScriptInsertOnto;
import br.com.ifba.swseditormobile.model.ChildGrupo;
import br.com.ifba.swseditormobile.model.Group;
import br.com.ifba.swseditormobile.request.GetRequisicao;
import br.com.ifba.swseditormobile.util.HTMLParser;
import br.com.ifba.swseditormobile.util.ToastManager;
import br.com.ifba.swseditormobile.viewexpandable.CarregaObjetos;
import br.com.ifba.swseditormobile.viewexpandable.ExpandableListAdapterView;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        IFragmentInteractionListener, PropertyListFragment.Communicator, View.OnTouchListener,
        Handler.Callback {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CLICK_WV = 1;
    private static final int CLICK_URL = 2;
    private final Handler handler = new Handler(this);
    private WebView webview;
    private ProgressDialog progressDialog;
    private GestureDetector gestureDetector;
    private TextView titlePrincipalProperties;
    private ExpandableListAdapterView expAdapter;
    private ExpandableListView expandList;

    private List<Group> listDataGroup;
    private HashMap<Group, List<ChildGrupo>> listDataChild;
    private String modelReference;
    private EditText etServiceHRests;

    private Group group;
    private String httpAddress;
    private static String addressUrl;
    private static String addressObs;

    private ImageButton btnGravarHrests;
    private Button btnGravarOntologiasService;
    private Button btnVoltarHrests;

    private Button btnVoltarParam;
    private Button btnContinuaParam;

    private Button btnVoltarLayout2;
    private Button btnFinaliza;

    private Spinner spinnerModeRefence;
    private Spinner spinnerLowering;
    private Spinner spinnerLifting;

    /*Operatin*/
    private String tagNomeMetodo;
    private String tagTipoMetodo;
    private String tagParametroOperation;
    private String tagInputDinamico;
    private String tagOutputDinamico;
    private int opLayout;

    private LinearLayout linearLayout01;
    private LinearLayout linearLayout02;
    private LinearLayout linearLayout03;

    private List<String> lista;
    private List<String> listaParam;
    private List<String> listaOut;

    private ServiceSematico serviceSematico;
    private InputSemantico inputSemantico;
    private OutputSemantico outputSemantico;
    private int opSemantic;

    private RelativeLayout layoutOnLoco;
    private RadioGroup rGroupBusca;
    private int opRadio;

    private Realm realm;
    private RealmResults<Ontologias> results;

    private GetRequisicao getRequisicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = new ArrayList();
        listaParam = new ArrayList();
        listaOut = new ArrayList();


        this.realm = RealmController.with(this).getRealm();
        RealmController.with(this).refresh();


        /*Deletar todas as tuplas do banco*/
        /*RealmController control = RealmController.getInstance();
        control.clearAll();*/

        setRealmData();

        titlePrincipalProperties = (TextView) findViewById(R.id.tvTitlePrincipalProperties);

        /*layout um*/
        linearLayout01 = (LinearLayout) findViewById(R.id.linearlayout1);
        linearLayout01.setVisibility(View.GONE);
        btnGravarOntologiasService = (Button) findViewById(R.id.gravarOntologiasService);
        btnVoltarHrests = (Button) findViewById(R.id.voltarHrests);

        /**/
        /*layout 2*/
        linearLayout02 = (LinearLayout) findViewById(R.id.linearlayout2);
        linearLayout02.setVisibility(View.GONE);
        btnVoltarParam = (Button) findViewById(R.id.btnParamVolta);
        btnContinuaParam = (Button) findViewById(R.id.btnParamContinua);

        /*layout 3*/
        linearLayout03 = (LinearLayout) findViewById(R.id.linearlayout3);
        linearLayout03.setVisibility(View.GONE);
        btnVoltarLayout2 = (Button) findViewById(R.id.btnVoltarLayout2);
        btnFinaliza = (Button) findViewById(R.id.btnFinalizar);

        layoutOnLoco = (RelativeLayout) findViewById(R.id.layoutOnLoco);
        layoutOnLoco.setVisibility(View.GONE);
        rGroupBusca = (RadioGroup) findViewById(R.id.radio_group_busca);


        btnGravarHrests = (ImageButton) findViewById(R.id.gravarDocumento);
        btnGravarHrests.setVisibility(View.GONE);

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
    public void Message(String message) {
        EditorDetailFragment detailfragment = (EditorDetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_Fragment);
        if (detailfragment != null && detailfragment.isInLayout())
            detailfragment.setText(message);
    }


    public void carregaExpandable(final String modelReference, final String serviceNomeTroca, final String labelServiceTroca,
                                  final String descriptionServiceTroca) {

        layoutOnLoco.setVisibility(View.VISIBLE);
        btnGravarHrests.setVisibility(View.VISIBLE);
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
                        dialogFragment.recebeTagService(modelReference, serviceNomeTroca, labelServiceTroca,
                                descriptionServiceTroca);
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
                        FragmentOperationTag operation = new FragmentOperationTag();
                        operation.recebeOperation(tagNomeMetodo, tagTipoMetodo, tagParametroOperation,
                                tagInputDinamico, tagOutputDinamico);
                        operation.show(getFragmentManager(), "Operation");
                        break;

                }
                return false;
            }
        });


        btnGravarHrests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(modelReference) && !TextUtils.isEmpty(serviceNomeTroca) && !TextUtils.isEmpty(labelServiceTroca) &&
                        !TextUtils.isEmpty(descriptionServiceTroca) && !TextUtils.isEmpty(addressUrl) && !TextUtils.isEmpty(addressObs) &&
                        !TextUtils.isEmpty(tagNomeMetodo) && !TextUtils.isEmpty(tagTipoMetodo)) {

                    expandList.setVisibility(View.GONE);
                    btnGravarHrests.setVisibility(View.GONE);
                    loadSpinnerService(modelReference);
                } else {
                    ToastManager.show(getBaseContext(), "Atenção!! Todos os campos devem ser preenchidos..", ToastManager.WARNING);
                }
            }
        });
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
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String url) {

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
            showAlertService();
        }
        return true;
    }

    public void showAlertService() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_hrests, null);
        etServiceHRests = (EditText) alertLayout.findViewById(R.id.txtService);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modelReference = etServiceHRests.getText().toString();
                        if (!TextUtils.isEmpty(modelReference)) {
                            carregaExpandable(modelReference, null, null, null);

                        } else {
                            ToastManager.show(getBaseContext(), "Por favor, insira nome do serviço", ToastManager.WARNING);
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
    public void onFragmentInteraction(String modelReference, String serviceName, String labelService,
                                      String descriptionService) {
        carregaExpandable(modelReference, serviceName, labelService, descriptionService);
    }

    @Override
    public void onFragmentInteractionAddress(String address, String addressO) {
        addressUrl = address;
        addressObs = addressO;
    }


    @Override
    public void onFragmentInteractionOperation(String tagNomeMetodo, String tagTipoMetodo, String tagParametroOperation,
                                               String tagInputDinamico, String tagOutputDinamico, int opLayout) {
        this.tagNomeMetodo = tagNomeMetodo;
        this.tagTipoMetodo = tagTipoMetodo;
        this.tagParametroOperation = tagParametroOperation;
        this.tagInputDinamico = tagInputDinamico;
        this.tagOutputDinamico = tagOutputDinamico;
        this.opLayout = opLayout;
    }

    public void loadSpinnerService(String modelReferenceBusca) {

        linearLayout01.setVisibility(View.VISIBLE);
        serviceSematico = new ServiceSematico();
        titlePrincipalProperties.setText("Service");

        spinnerModeRefence = (Spinner) findViewById(R.id.idModelReference);
        spinnerLowering = (Spinner) findViewById(R.id.idLoweringSchemaMapping);
        spinnerLifting = (Spinner) findViewById(R.id.idLiftingSchemaMapping);

        lista = new ArrayList<>();
        lista.add("Selecione valor...");

        opRadio = rGroupBusca.getCheckedRadioButtonId();

        if (opRadio == R.id.radio_local){
            realm.beginTransaction();
            results = realm.where(Ontologias.class).findAll();
            for (int i = 1; i < results.size(); i++) {
                lista.add(results.get(i).getEndereco());
            }
            realm.commitTransaction();
        }else{
             getRequisicao = (GetRequisicao) new GetRequisicao(MainActivity.this, new GetRequisicao.IParamsAsyncTask() {
                 @Override
                 public void processFinish(List<String> output) {
                    for(int i =0; i<output.size(); i++){
                        lista.add(output.get(i).toString());
                        Log.d(TAG, "Lista: URL CONNECTION::" + output.get(i).toString() + ":" + " VALOR");
                    }
                 }
             }).execute(modelReferenceBusca);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, lista);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerModeRefence.setAdapter(adapter);

        ArrayAdapter<String> adapterLowering = new ArrayAdapter<>(this, R.layout.spinner_item, lista);
        adapterLowering.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLowering.setAdapter(adapterLowering);

        ArrayAdapter<String> adapterLifting = new ArrayAdapter<>(this, R.layout.spinner_item, lista);
        adapterLifting.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLifting.setAdapter(adapterLifting);


        spinnerModeRefence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                serviceSematico.setModelReference(parent.getSelectedItem().toString());
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerLowering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                serviceSematico.setLowering(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerLifting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                serviceSematico.setLifting(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        btnVoltarHrests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandList.setVisibility(View.VISIBLE);
                btnGravarHrests.setVisibility(View.VISIBLE);
                linearLayout01.setVisibility(View.GONE);
                titlePrincipalProperties.setText("Propriedades Semânticas");

            }
        });

        btnGravarOntologiasService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout01.setVisibility(View.GONE);
                if (opLayout == 0) {
                    loadSpinnerParam(tagParametroOperation);
                } else if (opLayout == 1) {
                    loadSpinnerParam(tagInputDinamico);
                }
                /*OBSERVÇÂO*/
                Log.d(TAG, serviceSematico.getModelReference().toString());
                Log.d(TAG, serviceSematico.getLowering().toString());
                Log.d(TAG, serviceSematico.getLifting().toString());
            }
        });
    }



    public void loadSpinnerParam(String param) {

        Log.d(TAG, "loadSpinnerParam()");
        inputSemantico = new InputSemantico();
        if (opLayout == 0) {
            titlePrincipalProperties.setText("Parâmetro");
            opSemantic = 1;

        } else if (opLayout == 1) {
            titlePrincipalProperties.setText("Param Input");
            opSemantic = 0;
        }

        linearLayout02.setVisibility(View.VISIBLE);
        spinnerModeRefence = (Spinner) findViewById(R.id.idParamModelReference);
        spinnerLowering = (Spinner) findViewById(R.id.idParamLoweringSchemaMapping);
        spinnerLifting = (Spinner) findViewById(R.id.idParamLiftingSchemaMapping);

        listaParam = new ArrayList<>();
        listaParam.add("Selecione valor...");
        /**/

        if (opRadio == R.id.radio_local){
            realm.beginTransaction();
            results = realm.where(Ontologias.class).findAll();
            for (int i = 1; i < results.size(); i++) {
                listaParam.add(results.get(i).getEndereco());
            }
            realm.commitTransaction();
        }else{
            getRequisicao = (GetRequisicao) new GetRequisicao(MainActivity.this, new GetRequisicao.IParamsAsyncTask() {
                @Override
                public void processFinish(List<String> output) {
                    for(int i =0; i<output.size(); i++){
                        listaParam.add(output.get(i).toString());
                        Log.d(TAG, "Lista Param: URL CONNECTION::" + output.get(i).toString() + ":" + " VALOR PARAM");
                    }
                }
            }).execute(param);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, listaParam);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerModeRefence.setAdapter(adapter);

        ArrayAdapter<String> adapterLowering = new ArrayAdapter<>(this, R.layout.spinner_item, listaParam);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLowering.setAdapter(adapterLowering);

        ArrayAdapter<String> adapterLifting = new ArrayAdapter<>(this, R.layout.spinner_item, listaParam);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLifting.setAdapter(adapterLifting);


        spinnerModeRefence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                inputSemantico.setModelReference(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerLowering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                inputSemantico.setLowering(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerLifting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                inputSemantico.setLifting(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        if (opLayout == 0) {
            btnContinuaParam.setText("Finalizar");
            btnContinuaParam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HTMLParser htmlParser = new HTMLParser(serviceSematico, inputSemantico, outputSemantico);

                    Bundle params = new Bundle();
                    String html = htmlParser.montaHTML();
                    params.putSerializable("html", html);
                    Intent intent = new Intent(MainActivity.this, RenderView.class);
                    intent.putExtras(params);
                    startActivity(intent);
                    finish();
                    Log.d(TAG, html);
                }
            });

        } else if (opLayout == 1) {
            btnContinuaParam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout02.setVisibility(View.GONE);
                    loadSpinnerAmbos(tagOutputDinamico);
                }
            });
        }

        btnVoltarParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout01.setVisibility(View.VISIBLE);
                linearLayout02.setVisibility(View.GONE);

                titlePrincipalProperties.setText("Service");
            }
        });
    }


    public void loadSpinnerAmbos(String param) {
        outputSemantico = new OutputSemantico();
        linearLayout03.setVisibility(View.VISIBLE);
        titlePrincipalProperties.setText("OutPut");

        spinnerModeRefence = (Spinner) findViewById(R.id.idOutModelReference);
        spinnerLowering = (Spinner) findViewById(R.id.idOutLoweringSchemaMapping);
        spinnerLifting = (Spinner) findViewById(R.id.idOutLiftingSchemaMapping);
        opRadio = rGroupBusca.getCheckedRadioButtonId();

        listaOut = new ArrayList<>();
        listaOut.add("Selecione valor...");

        if (opRadio == R.id.radio_local){
            realm.beginTransaction();
            results = realm.where(Ontologias.class).findAll();
            for (int i = 1; i < results.size(); i++) {
                listaOut.add(results.get(i).getEndereco());
            }
            realm.commitTransaction();
        }else{
            getRequisicao = (GetRequisicao) new GetRequisicao(MainActivity.this, new GetRequisicao.IParamsAsyncTask() {
                @Override
                public void processFinish(List<String> output) {
                    for(int i =0; i<output.size(); i++){
                        listaOut.add(output.get(i).toString());
                        Log.d(TAG, "Lista Param OUT: URL CONNECTION::" + output.get(i).toString() + ":" + " VALOR OUT");
                    }
                }
            }).execute(param);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, listaOut);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerModeRefence.setAdapter(adapter);

        ArrayAdapter<String> adapterLowering = new ArrayAdapter<>(this, R.layout.spinner_item, listaOut);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLowering.setAdapter(adapterLowering);

        ArrayAdapter<String> adapterLifting = new ArrayAdapter<>(this, R.layout.spinner_item, listaOut);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLifting.setAdapter(adapterLifting);


        spinnerModeRefence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                outputSemantico.setModelReference(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerLowering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                outputSemantico.setLowering(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerLifting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                outputSemantico.setLifting(parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        btnVoltarLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout02.setVisibility(View.VISIBLE);
                linearLayout03.setVisibility(View.GONE);
                titlePrincipalProperties.setText("Input");
            }
        });

        btnFinaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTMLParser htmlParser = new HTMLParser(serviceSematico, inputSemantico, outputSemantico);

                Bundle params = new Bundle();
                String html = htmlParser.montaHTML();
                params.putSerializable("html", html);
                Intent intent = new Intent(MainActivity.this, RenderView.class);
                intent.putExtras(params);
                startActivity(intent);
                finish();
                Log.d(TAG, html);

            }
        });
    }

    public void setRealmData() {
        RealmResults<Ontologias> results = realm.where(Ontologias.class).findAll();
        ArrayList<Ontologias> onts = new ArrayList<>();

        onts = ScriptInsertOnto.ontos();

        if (results.size() == 0) {
            for (Ontologias b : onts) {
                realm.beginTransaction();
                realm.copyToRealm(b);
                realm.commitTransaction();
            }
        }
    }


}




















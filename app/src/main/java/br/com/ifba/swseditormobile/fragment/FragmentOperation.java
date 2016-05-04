package br.com.ifba.swseditormobile.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.util.HTMLParser;

/**
 * Created by Robson on 25/04/2016.
 */
public class FragmentOperation extends DialogFragment {
    private static final String TAG = FragmentOperation.class.getSimpleName();
    private EditText edNomeMethod;
    private EditText edTipoMethod;
    private EditText edParametro;
    private RadioGroup radioGroup;
    private String selectRadioButton;


    private String tagNomeMetodo;
    private String tagTipoMetodo;
    private String tagParametroOperation;
    private String tagInputDinamico;
    private String  tagOutputDinamico;

    private   int op;
    private int opLayout = 0;
    private static final String asp = "\"";
    private String concatenaOperation;

    private IFragmentInteractionListener mListener;

    /*Dinâmico*/
    private TextView tvOperationEntrada;
    private TextView tvOperationsaida;
    private EditText edInputOperation;
    private EditText edOutputOperation;

    private RadioButton radioAmbos;
    private RadioButton radioInput;
    private RadioButton radioOutput;

    private TextView tvOperationSaidaEntrada;



    public void recebeOperation(String tagNomeMetodo, String tagTipoMetodo, String tagParametroOperation,
             String tagInputDinamico,  String  tagOutputDinamico) {
        this.tagNomeMetodo = tagNomeMetodo;
        this.tagTipoMetodo =tagTipoMetodo;
        this.tagParametroOperation = tagParametroOperation;
        this.tagInputDinamico = tagInputDinamico;
        this.tagOutputDinamico =tagOutputDinamico;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final View view = getActivity().getLayoutInflater().inflate(R.layout.tag_operation_hrests,null);

        tvOperationEntrada = (TextView) view.findViewById(R.id.tvOperationEntrada);
        tvOperationEntrada.setVisibility(View.GONE);

        edInputOperation = (EditText) view.findViewById(R.id.txtInputOperation);
        edInputOperation.setVisibility(View.GONE);

        edOutputOperation  =(EditText) view.findViewById(R.id.txtOutputOperation);
        edOutputOperation.setVisibility(View.GONE);


        tvOperationsaida = (TextView) view.findViewById(R.id.tvOperationSaida);
        tvOperationsaida.setVisibility(View.GONE);


        edNomeMethod = (EditText) view.findViewById(R.id.txtOperation);
        edNomeMethod.setText(tagNomeMetodo);
        edNomeMethod.setInputType(InputType.TYPE_CLASS_TEXT);
        edNomeMethod.requestFocus();


        edTipoMethod = (EditText) view.findViewById(R.id.txtTipoOperation);
        edTipoMethod.setText(tagTipoMetodo);
        edTipoMethod.setInputType(InputType.TYPE_CLASS_TEXT);
        edTipoMethod.requestFocus();

        tvOperationSaidaEntrada = (TextView) view.findViewById(R.id.tvParamOperation);
        edParametro =(EditText) view.findViewById(R.id.txtParamOperation);
        edParametro.setText(tagParametroOperation);
        edParametro.setInputType(InputType.TYPE_CLASS_TEXT);
        edParametro.requestFocus();

        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);


        radioAmbos = (RadioButton) view.findViewById(R.id.radio_ambos);
        radioAmbos.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  tvOperationSaidaEntrada.setVisibility(View.GONE);
                  edParametro.setVisibility(View.GONE);

                  tvOperationEntrada.setVisibility(View.VISIBLE);
                  tvOperationsaida.setVisibility(View.VISIBLE);
                  edInputOperation.setVisibility(View.VISIBLE);
                  edOutputOperation.setVisibility(View.VISIBLE);
                  return false;
              }
          });

            radioInput = (RadioButton) view.findViewById(R.id.radio_input);
            radioInput.setOnTouchListener(new View.OnTouchListener() {
                      @Override
                      public boolean onTouch(View v, MotionEvent event) {
                          tvOperationSaidaEntrada.setVisibility(View.VISIBLE);
                          edParametro.setVisibility(View.VISIBLE);

                          tvOperationEntrada.setVisibility(View.GONE);
                          tvOperationsaida.setVisibility(View.GONE);
                          edInputOperation.setVisibility(View.GONE);
                          edOutputOperation.setVisibility(View.GONE);
                          return false;
                      }
                  });

            radioOutput = (RadioButton) view.findViewById(R.id.radio_output);
            radioOutput.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tvOperationSaidaEntrada.setVisibility(View.VISIBLE);
                    edParametro.setVisibility(View.VISIBLE);

                    tvOperationEntrada.setVisibility(View.GONE);
                    tvOperationsaida.setVisibility(View.GONE);
                    edInputOperation.setVisibility(View.GONE);
                    edOutputOperation.setVisibility(View.GONE);
                    return false;
                }
            });

        Log.d(TAG, "Valor: marcado " + String.valueOf(op));


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               tagNomeMetodo =edNomeMethod.getText().toString();
               tagTipoMetodo = edTipoMethod.getText().toString();
               tagParametroOperation = edParametro.getText().toString();
               tagInputDinamico = edInputOperation.getText().toString();
               tagOutputDinamico = edOutputOperation.getText().toString();

                op = radioGroup.getCheckedRadioButtonId();

                if (op == R.id.radio_input) {
                    Log.d("INPUT", "");
                    selectRadioButton =
                            "<span class=" + asp + "input" + asp + ">"
                                    + "<strong>Parâmetros de entrada:</strong>" + tagParametroOperation + "</span>"
                                    + "<br/>";

                } else if (op == R.id.radio_output) {
                    Log.d("OUTPUT", " ");
                    selectRadioButton = "</BR>" +
                            "<span class=" + asp + "output" + asp + ">"
                            + "<strong>Valor de Saida:</strong>" +tagParametroOperation + "</span>";
                } else if (op == R.id.radio_ambos) {
                    Log.d(TAG, "AMBOS");
                    selectRadioButton =
                            "<span class=" + asp + "input" + asp + ">"
                                    + "<strong>Parâmetros de entrada:</strong>"+ tagInputDinamico + "</span>"
                                    + "<br/>"
                                    +  "<span class=" + asp + "output" + asp + ">"
                                    + "<strong>Valor de Saida:</strong>" + tagOutputDinamico + "</span>";
                    opLayout = 1;
                }

                concatenaOperation = "<div class=" + asp + "operation" + asp + "id=" + asp + "op1" + asp + ">"
                        + "<h2>Operation:<code class=" + asp + "label" + asp + ">" + tagNomeMetodo + "</code></h2>"
                        + "<strong> Invoque usando:</strong> <span class=" + asp + "method" + asp + ">" + tagTipoMetodo + "</span> </BR>"
                        + selectRadioButton;

                mListener.onFragmentInteractionOperation( tagNomeMetodo,  tagTipoMetodo,  tagParametroOperation,
                         tagInputDinamico, tagOutputDinamico, opLayout);

                HTMLParser html = new HTMLParser();
                html.recebeOperation(concatenaOperation);

                Log.d(TAG, concatenaOperation);

            }
        });


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " IFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}

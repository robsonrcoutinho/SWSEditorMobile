package br.com.ifba.swseditormobile.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.util.HTMLParser;

/**
 * Created by Robson on 05/04/2016.
 */
public class FragmentServiceTag extends DialogFragment{
    private static final String asp = "\"";
    private static final String TAG = FragmentServiceTag.class.getSimpleName();
    private String tagNameService;
    private String tagLabelService;
    private String tagDescriptionService;
    private String modelReference;

    private IFragmentInteractionListener mListener;
    private EditText editModelReference;
    private EditText editService;
    private EditText editDescricaoService;
    private EditText editLabelService;

    private String htmlTagServiceRecebida;
    private String htmlTagServiceLabel;
    private String htmlTagServiceDescription;
    private String concatenaService;


    public void recebeTagService(String modelReference, String tagNameService, String tagLabelService, String tagDescriptionService){
        this.modelReference = modelReference;
        if(tagLabelService != null && tagDescriptionService != null ){
            this.tagLabelService = tagLabelService;
            this.tagDescriptionService = tagDescriptionService;
            this.tagNameService = tagNameService;
            Log.d(TAG,"Entrou aqui::" + tagLabelService +tagDescriptionService);
        }else {
            Log.d(TAG, "Não entrou:  Null" + tagLabelService + "Null" + tagDescriptionService);
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.tag_service_hrests, null);

        editModelReference = (EditText) view.findViewById(R.id.txtInsertModelReference);
        editService = (EditText) view.findViewById(R.id.txtInsertService);
        editLabelService = (EditText) view.findViewById(R.id.txtInsertServiceLabel);
        editDescricaoService= (EditText) view.findViewById(R.id.txtInsertDescriptionService);

        editModelReference.setText(modelReference);
        editModelReference.setInputType(InputType.TYPE_CLASS_TEXT);
        editModelReference.requestFocus();

        editService.setText(tagNameService);
        editService.setInputType(InputType.TYPE_CLASS_TEXT);
        editService.requestFocus();


        editLabelService.setText(tagLabelService);
        editLabelService.setInputType(InputType.TYPE_CLASS_TEXT);
        editLabelService.requestFocus();
        editLabelService.setHint("Label");


        editDescricaoService.setText(tagDescriptionService);
        editDescricaoService.setInputType(InputType.TYPE_CLASS_TEXT);
        editDescricaoService.requestFocus();
        editDescricaoService.setHint("Descrição");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modelReference = editModelReference.getText().toString();
                tagNameService = editService.getText().toString();
                tagLabelService = editLabelService.getText().toString();
                tagDescriptionService = editDescricaoService.getText().toString();

                htmlTagServiceRecebida = "<div class=" + asp + "service" + asp + " id =" + asp + "s1" + asp + ">"
                        + "<h3 align=\"center\">" + tagNameService + "</h3>";
                htmlTagServiceLabel = "<span class="+ asp +"label"+ asp+">"+tagLabelService+"</span>";
                htmlTagServiceDescription = "<p><em>"+tagDescriptionService+"</em></p>";

                concatenaService = htmlTagServiceRecebida+htmlTagServiceLabel+htmlTagServiceDescription+"</div>";
                HTMLParser html = new HTMLParser();
                html.recebeService(concatenaService);

                mListener.onFragmentInteraction(modelReference, tagNameService, tagLabelService, tagDescriptionService);

                Log.d(TAG,"Verificando serviço: "+concatenaService);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                    }
                }
        );
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

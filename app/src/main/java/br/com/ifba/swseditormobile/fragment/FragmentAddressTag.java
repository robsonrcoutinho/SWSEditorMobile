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

import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.util.HTMLParser;
import br.com.ifba.swseditormobile.viewexpandable.CarregaObjetos;

/**
 * Created by Robson on 06/04/2016.
 */
public class FragmentAddressTag extends DialogFragment {
    private static final String asp = "\"";
    private static final String TAG = FragmentServiceTag.class.getSimpleName();
    private EditText editAddress;
    private EditText editAddressObs;
    private String tagNameAddress;
    private String tagNameAddressObs;
    private String htmlTagAddressRecebida;
    private String htmlTagAddressObsRecebida;
    private String concatena;
    private IFragmentInteractionListener mListener;

    public void recebeTagAddress(String tagNameAddress, String tagNameAddressObs) {
        this.tagNameAddress = tagNameAddress;
        this.tagNameAddressObs = tagNameAddressObs;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.tag_address_hrests, null);
        editAddress = (EditText) view.findViewById(R.id.txtInsertAddress);
        editAddressObs = (EditText) view.findViewById(R.id.txtInsertAddressObs);

        editAddress.setText(tagNameAddress);
        editAddress.setInputType(InputType.TYPE_CLASS_TEXT);
        editAddress.requestFocus();
        editAddress.setHint("Address");

        editAddressObs.setText(tagNameAddressObs);
        editAddressObs.setInputType(InputType.TYPE_CLASS_TEXT);
        editAddressObs.requestFocus();
        editAddressObs.setHint("Observações");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagNameAddress = editAddress.getText().toString();
                tagNameAddressObs = editAddressObs.getText().toString();

                htmlTagAddressRecebida = "<span class="+asp+"address"+asp+">"+tagNameAddress+"</span>";
                htmlTagAddressObsRecebida = "<p><em>"+tagNameAddressObs+"</em></p>";

                concatena = htmlTagAddressRecebida+htmlTagAddressObsRecebida;
                HTMLParser html = new HTMLParser();
                html.recebeAddress(concatena);

               mListener.onFragmentInteractionAddress(tagNameAddress,tagNameAddressObs);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
            throw new ClassCastException(activity.toString() + " IFragmentInteractionListener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
package br.com.ifba.swseditormobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import br.com.ifba.swseditormobile.R;

/**
 * Created by Robson on 25/04/2016.
 */
public class FragmentOperation extends DialogFragment {
    private static final String TAG = FragmentOperation.class.getSimpleName();
    private EditText nomeMethod;
    private EditText tipoMethod;
    private RadioButton entrada;
    private RadioButton saida;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getActivity().getLayoutInflater().inflate(R.layout.tag_operation_hrests,null);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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






}

package br.com.ifba.swseditormobile.fragment;

import android.content.Context;

import br.com.ifba.swseditormobile.model.Operation;

/**
 * Created by Robson on 05/04/2016.
 */
public interface IFragmentInteractionListener {
    public void onFragmentInteraction(String modelReference,String serviceName, String labelService, String descriptionService);
    public void onFragmentInteractionAddress(String address, String addressObs);
    public void onFragmentInteractionOperation(String tagNomeMetodo, String tagTipoMetodo, String tagParametroOperation,
                                               String tagInputDinamico,  String  tagOutputDinamico);
}
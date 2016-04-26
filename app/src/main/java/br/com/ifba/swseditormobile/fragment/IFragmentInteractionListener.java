package br.com.ifba.swseditormobile.fragment;

import android.content.Context;

/**
 * Created by Robson on 05/04/2016.
 */
public interface IFragmentInteractionListener {
    public void onFragmentInteraction(String serviceName, String labelService, String descriptionService);
    public void onFragmentInteractionAddress(String address, String addressObs);
}
package br.com.ifba.swseditormobile.activity;

/**
 * Created by Robson on 18/01/2016.
 */
import android.support.v7.widget.SearchView;
import android.util.Log;

import java.io.IOException;

import br.com.ifba.swseditormobile.util.HTMLParser;

public class SearchResults implements SearchView.OnQueryTextListener {
    private static final String TAG = SearchResults.class.getSimpleName();

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i( TAG, "onQueryTextChange -> " + newText);
   /*    try {
            HTMLParser.getURLData(newText);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("Script", "onQueryTextSubmit -> "+query);
        return false;
    }
}



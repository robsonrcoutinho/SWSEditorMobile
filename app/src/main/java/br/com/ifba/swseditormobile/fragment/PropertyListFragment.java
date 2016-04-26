package br.com.ifba.swseditormobile.fragment;

/**
 * Created by Robson on 17/01/2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.ifba.swseditormobile.R;

public class PropertyListFragment extends Fragment  {
    private Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Communicator) {
            communicator = (Communicator) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " PropertyListFragment.Communicator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.painel_property_list_fragment, container, false);


        return view;
    }

    public interface Communicator {
        public void Message(String message);
    }

}
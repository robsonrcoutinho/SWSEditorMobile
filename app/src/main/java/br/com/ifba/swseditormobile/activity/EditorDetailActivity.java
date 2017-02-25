package br.com.ifba.swseditormobile.activity;

/**
 * Created by #### on 17/01/2016.
 */
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import br.com.ifba.swseditormobile.fragment.EditorDetailFragment;
import br.com.ifba.swseditormobile.R;

public class EditorDetailActivity extends Activity {
    public static final String TAG = EditorDetailActivity.class.getSimpleName();
    public static String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.detail_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nameMessage = extras.getString(message);
            EditorDetailFragment detailFragment = (EditorDetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            detailFragment.setText(nameMessage);
        }

    }

}

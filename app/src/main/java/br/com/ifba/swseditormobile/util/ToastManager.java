package br.com.ifba.swseditormobile.util;

/**
 * Created by Robson on 30/03/2016.
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ifba.swseditormobile.R;

public class ToastManager {

    public static final int INFORMATION = 0;
    public static final int WARNING     = 1;
    public static final int ERROR       = 2;

    public static void show(Context context, String text,
                            int toastType) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.toast_layout, null);

        TextView tv = (TextView) layout.findViewById(R.id.tvTexto);
        tv.setText(text);

        LinearLayout llRoot =
                (LinearLayout) layout.findViewById(R.id.llRoot);
        int bg;

        switch (toastType) {
            case WARNING:

                bg  = R.drawable.toast_background;
                break;
            default:
                bg  = R.drawable.toast_background;
                break;
        }

        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warning, 0, 0, 0);
        llRoot.setBackgroundResource(bg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

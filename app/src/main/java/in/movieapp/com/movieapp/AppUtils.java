package in.movieapp.com.movieapp;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by DivyaSethi on 31/07/18.
 */
public class AppUtils {

    public static int calculateNoOfGridColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}

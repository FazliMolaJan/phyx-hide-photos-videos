package com.aganticsoft.phyxhidephotosandvideos.util;

import android.content.res.Resources;

/**
 * Created by ttson
 * Date: 9/30/2017.
 */

public class FormatUtils {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}

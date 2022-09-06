package com.example.part10_30;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {
    static final String TAG = AppUtils.class.getSimpleName();
    public static String getDate (String dataString) {
        try {
            Log.d(TAG, "+++ getDate : start");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date date = format1.parse(dataString);
            DateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            Log.d(TAG, "--- getDate : end");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "xx";
        }
    }

    public static String getTime (String dateString) {
        try {
            Log.d(TAG, "+++ getTime : start");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("h:mm a");
            Date netDate = (date);
            Log.d(TAG, "--- getTime : end");
            return sdf.format(netDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "xx";
        }
    }
}

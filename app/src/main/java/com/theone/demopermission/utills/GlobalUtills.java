package com.theone.demopermission.utills;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GlobalUtills {

    //    todo Snackbar showing time
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;


    //todo Date formats
    public static final String HHMMA = "hh:mm a";
    public static final String HMMA = "h:mm a";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String DDMMMYYYY1 = "dd-MMMM-yyyy";
    public static final String DDMMMMYYYY2 = "dd MMMM yyyy";
    public static final String DDMMMMYYYYZZZZ = "dd MMMM yyyy zzzz";
    public static final String EEEMMMDDYY = "EEE, MMM d, ''yy";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String HMMADDMMMMYYYY = "h:mm a dd MMMM yyyy";
    public static final String KMMAZ = "K:mm a, z";
    public static final String HHOCLOCKAZZZZ = "hh 'o''clock' a, zzzz";
    public static final String YYYYMMDDTHHMMSSSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String EDDMMMYYYYHHMMSSZ = "E, dd MMM yyyy HH:mm:ss z";
    public static final String YYYYMMDDGATHHMMSSZ = "yyyy.MM.dd G 'at' HH:mm:ss z";
    public static final String YYYYYMMMMMDDGGGHHMMAAA = "yyyyy.MMMMM.dd GGG hh:mm aaa";
    public static final String EEEDMMMYYYYHHMMSSZ = "EEE, d MMM yyyy HH:mm:ss Z";
    public static final String YYYYMMDDTGGMMSSSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String YYYMMDDTHHMMSSSSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    // todo  Show Snack bar utills
    public interface ActionClicked {
        public void onClick(View v);
    }

    public static void showMessage(Context context, String message, int length, int BackColor, int MsgTextColor) {
        View parentLayout = (((Activity) context)).findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(parentLayout, message, length);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(BackColor);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(MsgTextColor);//change Snackbar's text color;
        snackbar.show();
    }

    public static void showMessage(Context context, String message, int length) {
        View parentLayout = (((Activity) context)).findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(parentLayout, message, length);
        snackbar.show();
    }

    public static void showMessage(Context context, String message, int length, String ButtonText, int btncolor, int MsgTextColor, int BackColor, final ActionClicked clicked) {
        View parentLayout = (((Activity) context)).findViewById(android.R.id.content);
        Snackbar s = Snackbar.make(parentLayout, message, length);
        s.setAction(ButtonText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClick(v);
            }
        });
        View view = s.getView();
        view.setBackgroundColor(BackColor);
        TextView tv = (TextView) view
                .findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(MsgTextColor);//change textColor

        TextView tvAction = (TextView) view
                .findViewById(android.support.design.R.id.snackbar_action);
        tvAction.setTextSize(16);
        tvAction.setTextColor(btncolor);
        s.show();

    }


//todo Date Conversion Utill functions

    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HHMMA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    public static String getDateTimeFromTimeStamp(Long time, String mDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateTime = new Date(time);
        return dateFormat.format(dateTime);
    }


    public static long getTimeStampFromDateTime(String mDateTime, String mDateFormat) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(mDateTime);
        return date.getTime();
    }

    public static String formatDateTimeFromDate(String mDateFormat, Date date) {
        if (date == null) {
            return null;
        }
        return DateFormat.format(mDateFormat, date).toString();
    }

    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;

    }




}

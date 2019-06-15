package com.theone.demopermission.utills;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hemant on 13-06-2019,
 * at TheOneTechnologies
 */
public class MyDateTimePicker implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static Context mContext;
    private static MyDateTimePicker myDateTimeUtils;
    private Integer mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    private Calendar mCalendar;
    private OnDateTimeSetListener mOnDateTimeSetListener;
    private Integer mDialogStyle = null;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("hh:mm:ss a");
    private long mMinDate = 0, mMaxDate = 0;
    private Integer mPositiveButtonColor, mNegativeButtonColor;
    private String mPositiveButtonText, mNegativeButtonText;
    private boolean mCancelable = true;
    private boolean mCancelOutSideTouch = true;
    private boolean mTakeTimeAlso = false;
    private boolean mShow24HourView = false;

    public MyDateTimePicker(Context context) {
        mContext = context;
    }
    public interface OnDateTimeSetListener {
        void onDateSet(String dateInString, Date date);
        void onTimeSet(String time);
    }
    public static MyDateTimePicker getInstance(Context context) {
        if (myDateTimeUtils == null) {
            mContext = context;
            myDateTimeUtils = new MyDateTimePicker(mContext);
            return myDateTimeUtils;
        } else {
            return myDateTimeUtils;
        }
    }


    public void setCalender(Calendar calendar) {
        mCalendar = calendar;
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mSeconds = mCalendar.get(Calendar.SECOND);

    }


    public void showDateDialog(boolean takeTimeAlso) {

        mTakeTimeAlso = takeTimeAlso;

        if (mCalendar == null) {
            setCalender(Calendar.getInstance());
        }
        DatePickerDialog datePickerDialog;
        if (mDialogStyle != null) {
            datePickerDialog = new DatePickerDialog(mContext, mDialogStyle, this, mYear, mMonth, mDay);
        } else {
            datePickerDialog = new DatePickerDialog(mContext, this, mYear, mMonth, mDay);
        }
        if (mMinDate != 0) {
            datePickerDialog.getDatePicker().setMinDate(mMinDate);
        }

        if (mMaxDate != 0) {
            datePickerDialog.getDatePicker().setMaxDate(mMaxDate);
        }

        datePickerDialog.setCancelable(mCancelable);
        datePickerDialog.setCanceledOnTouchOutside(mCancelOutSideTouch);

        datePickerDialog.show();
        if (mPositiveButtonColor != null) {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(mPositiveButtonColor);
        }
        if (mNegativeButtonColor != null) {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(mNegativeButtonColor);
        }

        if (mPositiveButtonText != null) {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setText("" + mPositiveButtonText);
        }
        if (mNegativeButtonText != null) {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setText("" + mNegativeButtonText);
        }

    }

    public void showTimeDialog(boolean show24HourView) {

        mShow24HourView = show24HourView;

        if (mCalendar == null) {
            setCalender(Calendar.getInstance());
        }

        TimePickerDialog timePickerDialog;
        if (mDialogStyle != null) {
            timePickerDialog = new TimePickerDialog(mContext, mDialogStyle, this, mHour, mMinute, mShow24HourView);
        } else {
            timePickerDialog = new TimePickerDialog(mContext, this, mHour, mMinute, mShow24HourView);
        }
        timePickerDialog.setCancelable(mCancelable);
        timePickerDialog.setCanceledOnTouchOutside(mCancelOutSideTouch);
        timePickerDialog.show();

        if (mPositiveButtonColor != null) {
            timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(mPositiveButtonColor);
        }
        if (mNegativeButtonColor != null) {
            timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(mNegativeButtonColor);
        }

        if (mPositiveButtonText != null) {
            timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setText("" + mPositiveButtonText);
        }
        if (mNegativeButtonText != null) {
            timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setText("" + mNegativeButtonText);
        }
    }

    public MyDateTimePicker setListener(OnDateTimeSetListener onDateTimeSetListener) {
        mOnDateTimeSetListener = onDateTimeSetListener;
        return myDateTimeUtils;
    }

    public void setThemeStyle(int style) {
        mDialogStyle = style;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (mOnDateTimeSetListener != null) {

            mDay = dayOfMonth;
            mMonth = monthOfYear;
            mYear = year;

            if (mTakeTimeAlso) {
                showTimeDialog(mShow24HourView);
            } else {
                giveDateResponse();
            }
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mOnDateTimeSetListener != null) {

            mHour = hourOfDay;
            mMinute = minute;

            if (mTakeTimeAlso) {
                giveDateTimeResponse();
            } else {
                giveTimeResponse();
            }
        }
    }

    private void giveDateTimeResponse() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);


        String date;
        try {
            date = mDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            date = e.getMessage();
        }


        String time;
        try {
            time = mTimeFormat.format(calendar.getTime());
        } catch (Exception e) {
            time = e.getMessage();
        }

        mOnDateTimeSetListener.onDateSet(date + "==" + time, calendar.getTime());

    }


    private void giveDateResponse() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);
        String date;
        try {
            date = mDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            date = e.getMessage();
        }
        mOnDateTimeSetListener.onDateSet(date, calendar.getTime());
    }

    private void giveTimeResponse() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);

        String time;
        try {
            time = mTimeFormat.format(calendar.getTime());
        } catch (Exception e) {
            time = e.getMessage();
        }

        mOnDateTimeSetListener.onTimeSet(time);
    }

    public void setDateReturnFormat(SimpleDateFormat dateFormat) {
        this.mDateFormat = dateFormat;

    }

    public void setTimeReturnFormat(SimpleDateFormat timeFormat) {
        this.mTimeFormat = timeFormat;

    }


    public void setMinDate(Calendar calendar) {
        mMinDate = calendar.getTimeInMillis();
    }

    public void setMinDate(long minDate) {
        mMinDate = minDate;
    }

    public void setMaxDate(Calendar calendar) {
        mMaxDate = calendar.getTimeInMillis();
    }

    public void setMaxDate(long maxDate) {
        mMaxDate = maxDate;
    }

    public void setPositiveButtonColor(Integer positiveButtonColor) {
        mPositiveButtonColor = positiveButtonColor;
    }

    public void setNegativeButtonColor(Integer negativeButtonColor) {
        mNegativeButtonColor = negativeButtonColor;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        mPositiveButtonText = positiveButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        mNegativeButtonText = negativeButtonText;
    }

    public void setCancelable(boolean cancelable) {
        mCancelable = cancelable;
    }

    public void setCancelOutSideTouch(boolean cancelOutSideTouch) {
        mCancelOutSideTouch = cancelOutSideTouch;
    }

    public void set24HourTime(boolean show24HourView) {
        mShow24HourView = show24HourView;
    }
}

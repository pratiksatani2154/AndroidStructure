package com.theone.demopermission.interfaces;

import java.util.Date;

/**
 * Created by Hemant on 13-06-2019,
 * at TheOneTechnologies
 */
public interface OnDateTimeSetListener {
    void onDateSet(String dateInString, Date date);
    void onTimeSet(String time);
}

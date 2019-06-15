package com.theone.demopermission.utills;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    public static int requestCode = 1001;

    private static final String MNC = "MNC";

    // Calendar group.
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

    //Storage Group
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;


    // Camera group.
    public static final String CAMERA = Manifest.permission.CAMERA;

    // Contacts group.
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;

    // Location group.
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    // Microphone group.
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    // Phone group.
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    // Sensors group.
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    public static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;

    // SMS group.
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS";

    // Bookmarks group.
    public static final String READ_HISTORY_BOOKMARKS = "com.android.browser.permission.READ_HISTORY_BOOKMARKS";
    public static final String WRITE_HISTORY_BOOKMARKS = "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS";

    // Social Info group.


    public static boolean useRunTimePermissions() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }


    public static void requestPermissions(Activity activity, String[] permission) {
        if (useRunTimePermissions()) {
            activity.requestPermissions(permission, requestCode);
        }
    }

    public static void requestPermissions(Fragment fragment, String[] permission) {
        if (useRunTimePermissions()) {
            fragment.requestPermissions(permission, requestCode);
        }
    }


    public static void goToAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.getPackageName(), null));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static boolean isPermissionGranted(Activity activity,String permission){
        int res=activity.checkCallingPermission(permission);
        return (res==PackageManager.PERMISSION_GRANTED);

    }

    public static boolean HandleResult(Activity activity, int RequestCode, String[] permissions, int[] grantResults) {
        List<String> rerequestPermission = new ArrayList<String>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                String permission=permissions[i];
                if (useRunTimePermissions()) {
                    boolean showRationale = activity.shouldShowRequestPermissionRationale( permission);
                    if (showRationale) {
                        rerequestPermission.add(permissions[i]);
                    }
                }
            }
        }
        if (rerequestPermission.size() > 0) {
            String tempArr[] = rerequestPermission.toArray(new String[rerequestPermission.size()]);
            requestPermissions(activity, tempArr);
            return false;
        } else {
            return true;
        }

    }

    public static boolean HandleResult(Fragment fragment, int RequestCode, String[] permissions, int[] grantResults) {

        List<String> rerequestPermission = new ArrayList<String>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                String permission=permissions[i];
                if (useRunTimePermissions()) {
                    boolean showRationale = fragment.shouldShowRequestPermissionRationale( permission);
                    if (showRationale) {
                        rerequestPermission.add(permissions[i]);
                    }
                }
            }
        }
        if (rerequestPermission.size() > 0) {
            String tempArr[] = rerequestPermission.toArray(new String[rerequestPermission.size()]);
            requestPermissions(fragment, tempArr);
            return false;
        } else {
            return true;
        }


    }
}

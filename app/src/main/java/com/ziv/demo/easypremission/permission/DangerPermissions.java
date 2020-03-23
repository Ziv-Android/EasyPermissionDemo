package com.ziv.demo.easypremission.permission;

import android.Manifest;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 必须的权限
 *
 * @author ziv
 */
public class DangerPermissions {
    public static final String TAG = "RequirePermissions";
    private static List<String> mDangerPermissions = new ArrayList<>();

    /**
     * 必须高危权限初始化
     */
    static {
        // Calendar日历
        mDangerPermissions.add(Manifest.permission.READ_CALENDAR);
        mDangerPermissions.add(Manifest.permission.WRITE_CALENDAR);
        // Camera相机
        mDangerPermissions.add(Manifest.permission.CAMERA);
        // Contacts
        mDangerPermissions.add(Manifest.permission.READ_CONTACTS);
        mDangerPermissions.add(Manifest.permission.WRITE_CONTACTS);
        mDangerPermissions.add(Manifest.permission.GET_ACCOUNTS);
        // Location
        mDangerPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        mDangerPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        // Microphone
        mDangerPermissions.add(Manifest.permission.RECORD_AUDIO);
        // Phone
        mDangerPermissions.add(Manifest.permission.READ_PHONE_STATE);
        mDangerPermissions.add(Manifest.permission.CALL_PHONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDangerPermissions.add(Manifest.permission.READ_CALL_LOG);
            mDangerPermissions.add(Manifest.permission.WRITE_CALL_LOG);
        }
        mDangerPermissions.add(Manifest.permission.ADD_VOICEMAIL);
        mDangerPermissions.add(Manifest.permission.USE_SIP);
        mDangerPermissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        // Sensors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mDangerPermissions.add(Manifest.permission.BODY_SENSORS);
        }
        // Sms
        mDangerPermissions.add(Manifest.permission.SEND_SMS);
        mDangerPermissions.add(Manifest.permission.RECEIVE_SMS);
        mDangerPermissions.add(Manifest.permission.READ_SMS);
        mDangerPermissions.add(Manifest.permission.RECEIVE_WAP_PUSH);
        mDangerPermissions.add(Manifest.permission.RECEIVE_MMS);
        // Storage外部存储
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDangerPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        mDangerPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 增
     */
    public static boolean addPermission(String permission) {
        if (mDangerPermissions != null && !mDangerPermissions.contains(permission)) {
            return mDangerPermissions.add(permission);
        } else {
            Log.e(TAG, String.format("AddPermission failed: %s already inside.", permission));
        }
        return false;
    }

    public static void addPermissions(List<String> permissions) {
        for (String permission : permissions) {
            addPermission(permission);
        }
    }

    /**
     * 删
     */
    public static boolean deletePermission(String permission) {
        if (mDangerPermissions != null && !mDangerPermissions.isEmpty()) {
            return mDangerPermissions.remove(permission);
        }
        return false;
    }

    public static String deletePermission(int index) {
        if (mDangerPermissions != null && !mDangerPermissions.isEmpty()) {
            if (mDangerPermissions.size() > index) {
                return mDangerPermissions.remove(index);
            } else {
                Log.e(TAG, String.format("DeletePermission: index %d OutOfIndexMax.", index));
            }
        }
        return null;
    }

    public static void clearAllPermission() {
        if (mDangerPermissions != null) {
            mDangerPermissions.clear();
        }
    }

    /**
     * 改
     */
    public static void changePermission(String permission, String newPermission) {
        if (mDangerPermissions != null) {
            if (mDangerPermissions.contains(permission)) {
                deletePermission(permission);
            }
            addPermission(newPermission);
        }
    }

    /**
     * 查
     */
    public static List<String> getPermissions() {
        return mDangerPermissions;
    }
}

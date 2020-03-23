package com.ziv.demo.easypremission.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

/**
 * @author ziv
 */
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";
    private static Context mContext;
    private List<String> mDangerPermissions;

    private PermissionUtil() {}

    public static PermissionUtil getInstance(Context context) {
        mContext = context.getApplicationContext();
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static PermissionUtil INSTANCE = new PermissionUtil();
    }

    public void checkManifestPermission() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = packageInfo.requestedPermissions;
            if (requestedPermissions != null) {
                List<String> permissions = Arrays.asList(requestedPermissions);
                Log.d(TAG, "requestedPermissions: " + permissions.toString());
                mDangerPermissions = DangerPermissions.getPermissions();
                mDangerPermissions.retainAll(permissions);
                Log.e(TAG, "dangerPermissions("+ mDangerPermissions.size() +"): " + mDangerPermissions.toString());
            } else {
                Log.d(TAG, "requestedPermissions: No permission used.");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAllDangerPermission(Context context) {
        String[] dangerPermissionArray = mDangerPermissions.toArray(new String[mDangerPermissions.size()]);
        return hasAllDangerPermission(context, dangerPermissionArray);
    }

    public boolean hasAllDangerPermission(Context context, String... perms) {
        // 只要有一个没有授权, return false.
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestDangerPermissions() {

    }
}

package com.ziv.demo.easypremission.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @author ziv
 */
public class EasyPermissionUtil implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "EasyPermissionUtil";
    private List<String> mDangerPermissions;
    private static Activity mCurrentActivity;
    private static Fragment mCurrentFragment;

    private EasyPermissionUtil() {}

    public static EasyPermissionUtil getInstance(@NonNull Activity activity) {
        mCurrentActivity = activity;
        return SingletonHolder.INSTANCE;
    }

    public static EasyPermissionUtil getInstance(@NonNull Fragment fragment) {
        mCurrentFragment = fragment;
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static EasyPermissionUtil INSTANCE = new EasyPermissionUtil();
    }

    public void release() {
        mCurrentActivity = null;
        mCurrentFragment = null;
    }

    public void checkManifestPermission() {
        try {
            Context applicationContext = mCurrentActivity.getApplicationContext();
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), PackageManager.GET_PERMISSIONS);
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

    public void requestDangerPermissions(int requestCode) {
        String[] dangerPermissions = mDangerPermissions.toArray(new String[mDangerPermissions.size()]);
        if (!EasyPermissions.hasPermissions(mCurrentActivity, dangerPermissions)) {
            Log.d(TAG, "requestDangerPermissions: request permission.");
            PermissionRequest permissionRequest = new PermissionRequest.Builder(mCurrentActivity, requestCode, dangerPermissions).setRationale("重要权限被拒绝, 应用无法正常工作, 点击确定去授权").build();
            EasyPermissions.requestPermissions(permissionRequest);
        } else {
            Log.e(TAG, "requestDangerPermissions: has all danger permission.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted: " + perms.toString());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e(TAG, "onPermissionsDenied: " + perms.toString());
        if (EasyPermissions.somePermissionPermanentlyDenied(mCurrentActivity, perms)) {
            AppSettingsDialog dialog = new AppSettingsDialog.Builder(mCurrentActivity).setRationale("权限被禁止, 请在设置界面授权").setTitle("小朋友, 你是否有很多问号?").build();
            dialog.show();
        }
    }
}

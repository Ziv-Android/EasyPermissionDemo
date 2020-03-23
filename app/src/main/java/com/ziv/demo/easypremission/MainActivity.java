package com.ziv.demo.easypremission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ziv.demo.easypremission.permission.DangerPermissions;
import com.ziv.demo.easypremission.permission.EasyPermissionUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private EasyPermissionUtil mPermissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionUtil = EasyPermissionUtil.getInstance(this);
        mPermissionUtil.checkManifestPermission();
        mPermissionUtil.requestDangerPermissions(0x01);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

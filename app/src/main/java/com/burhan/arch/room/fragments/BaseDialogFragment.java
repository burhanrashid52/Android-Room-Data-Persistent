package com.burhan.arch.room.fragments;

import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Burhanuddin on 9/10/2017.
 */

public class BaseDialogFragment extends DialogFragment {


    private static final int REQUEST_PERMISSION_STORAGE = 53;

    public void onStoragePermissionGranted() {
    }

    public boolean requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // We have access. Life is good.
            onStoragePermissionGranted();
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), WRITE_EXTERNAL_STORAGE)) {
            // We've been denied once before. Explain why we need the permission, then ask again.
            Toast.makeText(getActivity(), "Required Storage Permission to Choose Image From Gallery", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // We've never asked. Just do it.
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onStoragePermissionGranted();
        } else {
            // We were not granted permission this time
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

package com.example.zerosoles.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.zerosoles.R;

public class RationaleDialog extends DialogFragment {

    private static final String ARG_REQUEST_CODE = "requestCode";
    private static final String ARG_FINISH_ACTIVITY = "finish";

    private boolean finishActivity;

    public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_REQUEST_CODE, requestCode);
        arguments.putBoolean(ARG_FINISH_ACTIVITY, finishActivity);
        RationaleDialog dialog = new RationaleDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = requireArguments();
        final int requestCode = arguments.getInt(ARG_REQUEST_CODE);
        finishActivity = arguments.getBoolean(ARG_FINISH_ACTIVITY);

        Activity activity = requireActivity();
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.permission_rationale_location)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            requestCode);
                    finishActivity = false;
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (finishActivity) {
            new AlertDialog.Builder(requireActivity())
                    .setMessage(R.string.permission_required)
                    .setPositiveButton(android.R.string.ok, (d, which) -> requireActivity().finish())
                    .show();
        }
    }
}

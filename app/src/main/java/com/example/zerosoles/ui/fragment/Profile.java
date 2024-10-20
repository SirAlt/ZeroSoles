package com.example.zerosoles.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zerosoles.R;

public class Profile extends Fragment implements View.OnClickListener {

    private TextView tvUsername, tvFullName, tvEmail, tvGender, tvPasswordData, tvNewPassword, tvPassword, tvCanShip;
    private Button btnEdit, btnSave;
    private EditText etUserName, etFullName, etEmail, etGender, etOldPassword, etNewPassword;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbNonBinary;
    private LinearLayout layoutOldPassword, layoutCanShip;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swCanShip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.username_data);
        etUserName = view.findViewById(R.id.et_username);
        tvFullName = view.findViewById(R.id.fullName_data);
        etFullName = view.findViewById(R.id.et_fullName);
        tvEmail = view.findViewById(R.id.email_data);
        etEmail = view.findViewById(R.id.et_email);
        tvGender = view.findViewById(R.id.gender_data);
        rgGender = view.findViewById(R.id.rg_gender);
        tvPasswordData = view.findViewById(R.id.password_data);
        tvPassword = view.findViewById(R.id.tv_password);
        tvNewPassword = view.findViewById(R.id.tv_new_password);
        etNewPassword = view.findViewById(R.id.et_new_password);
        etOldPassword = view.findViewById(R.id.et_old_password);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnSave = view.findViewById(R.id.btn_save_changes);

        layoutOldPassword = view.findViewById(R.id.layout_old_password);
        layoutCanShip = view.findViewById(R.id.layout_can_ship);

        btnEdit.setOnClickListener(this);

        btnSave.setOnClickListener(this);

        return view;
    }

    private void handleEditButton() {

        tvUsername.setVisibility(View.GONE);
        etUserName.setVisibility(View.VISIBLE);

        tvFullName.setVisibility(View.GONE);
        etFullName.setVisibility(View.VISIBLE);

        tvEmail.setVisibility(View.GONE);
        etEmail.setVisibility(View.VISIBLE);

        tvGender.setVisibility(View.GONE);
        rgGender.setVisibility(View.VISIBLE);

//        Can Ship just for staff
        layoutCanShip.setVisibility(View.VISIBLE);

        layoutOldPassword.setVisibility(View.VISIBLE);

        tvPassword.setVisibility(View.GONE);
        tvPasswordData.setVisibility(View.GONE);
        tvNewPassword.setVisibility(View.VISIBLE);
        etNewPassword.setVisibility(View.VISIBLE);

        btnEdit.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
    }

    private void handleSaveButton() {

        tvUsername.setVisibility(View.VISIBLE);
        etUserName.setVisibility(View.GONE);

        tvFullName.setVisibility(View.VISIBLE);
        etFullName.setVisibility(View.GONE);

        tvEmail.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.GONE);

        tvGender.setVisibility(View.VISIBLE);
        rgGender.setVisibility(View.GONE);

        //        Can Ship just for staff
        layoutCanShip.setVisibility(View.GONE);

        layoutOldPassword.setVisibility(View.GONE);

        tvPassword.setVisibility(View.VISIBLE);
        tvPasswordData.setVisibility(View.VISIBLE);
        tvNewPassword.setVisibility(View.GONE);
        etNewPassword.setVisibility(View.GONE);

        btnEdit.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Log.d("view.getId", "onContextClick: " + view.getId());
        if (view.getId() == R.id.btn_edit) {
            handleEditButton();
        } else if (view.getId() == R.id.btn_save_changes) {
            handleSaveButton();
        }
    }
}

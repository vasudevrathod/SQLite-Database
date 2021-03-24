package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.databinding.ActivityChnagePasswordActivtyBinding;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityChnagePasswordActivtyBinding binding;

    private String strCurrentPassword, strNewPassword, strConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChnagePasswordActivtyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        HeaderInit();
        ClickListener();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.change_password));
        binding.header.ivBack.setVisibility(View.VISIBLE);
    }

    private boolean isValidation() {
        boolean isValidate = true;

        strCurrentPassword = binding.etCurrentPassword.getText().toString();
        strNewPassword = binding.etNewPassword.getText().toString();
        strConfirmNewPassword = binding.etConfirmNewPassword.getText().toString();

        if (Utils.isStringNull(strCurrentPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_current_password_blank), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strNewPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_new_password_blank), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strConfirmNewPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_confirm_new_password_blank), Toast.LENGTH_SHORT).show();
        } else if (strNewPassword.equals(strConfirmNewPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_confirm_new_password_validate), Toast.LENGTH_SHORT).show();
        }

        return isValidate;
    }

    private void ClickListener() {
        binding.btChangePassword.setOnClickListener(this);
        binding.header.ivBack.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btChangePassword:
                if (isValidation()) {

                }
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }
}
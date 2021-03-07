package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    ActivitySignUpBinding binding;
    
    private String strFullName = "", strEmail = "", strPhone = "", strPassword = "", strConfirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        HeaderInit();
        ClickListener();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.sign_up));
        binding.header.ivBack.setVisibility(View.VISIBLE);
    }

    private boolean isValidation() {
        boolean isValidate = true;
        
        strFullName = binding.etFullName.getText().toString().trim();
        strEmail = binding.etEmail.getText().toString().trim();
        strPhone = binding.etPhone.getText().toString().trim();
        strPassword = binding.etPassword.getText().toString();
        strConfirmPassword = binding.etConfirmPassword.getText().toString();
        
        if (Utils.isStringNull(strFullName)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_full_name_blank), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strEmail)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_email_blank), Toast.LENGTH_SHORT).show();
        } else if (!Utils.isEmailValidate(strEmail)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_email_validate), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strPhone)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_phone_blank), Toast.LENGTH_SHORT).show();
        } else if (strPhone.length() != 10) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_phone_validate), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_password_blank), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strConfirmPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_confirm_password_blank), Toast.LENGTH_SHORT).show();
        } else if (!strConfirmPassword.equals(strPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_confirm_password_validate), Toast.LENGTH_SHORT).show();
        }

        return isValidate;
    }

    private void CreateSignUp() {

    }

    private void ClickListener() {
        binding.btSignUp.setOnClickListener(this);
        binding.header.ivBack.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSignUp:
                if (isValidation()) {
                    CreateSignUp();
                }
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }
}
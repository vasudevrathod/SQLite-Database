package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignInBinding binding;

    private String strEmail = "",strPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        HeaderInit();
        ClickListener();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.sign_in));
    }

    private boolean isValidation() {
        boolean isValidate = true;

        strEmail = binding.etEmail.getText().toString().trim();
        strPassword = binding.etPassword.getText().toString();

        if (Utils.isStringNull(strEmail)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_email_blank), Toast.LENGTH_SHORT).show();
        } else if (!Utils.isEmailValidate(strEmail)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_email_validate), Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringNull(strPassword)) {
            isValidate = false;
            Toast.makeText(this, getString(R.string.err_password_blank), Toast.LENGTH_SHORT).show();
        }

        return isValidate;
    }

    private void ClickListener() {
        binding.btSignIn.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSignIn:
                if(isValidation()) {
                    startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                }
                break;

            case R.id.tvSignUp:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
        }
    }
}
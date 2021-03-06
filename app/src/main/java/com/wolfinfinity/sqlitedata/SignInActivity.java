package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignInBinding binding;

    private String strEmail = "",strPassword = "";
    private SQLiteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new SQLiteDatabaseHelper(this);

        HeaderInit();
        EditTextListener();
        ClickListener();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.sign_in));
    }

    private void EditTextListener() {
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    binding.etEmail.setText(result);
                    binding.etEmail.setSelection(result.length());
                }
            }
        });
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

    private void CheckUser() {
        if (dbHelper.isValueExist(strEmail)) {
            boolean isLogin = dbHelper.userLogin(strEmail, strPassword);
            if (isLogin) {
                clearAll();
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, getString(R.string.err_something_wrong), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.err_email_not_exist), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAll() {
        binding.etEmail.getText().clear();
        binding.etPassword.getText().clear();
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
                    CheckUser();
                }
                break;

            case R.id.tvSignUp:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
        }
    }
}
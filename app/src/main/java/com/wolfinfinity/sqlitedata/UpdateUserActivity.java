package com.wolfinfinity.sqlitedata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wolfinfinity.sqlitedata.Model.UserListModel;
import com.wolfinfinity.sqlitedata.databinding.ActivityUpdateUserBinding;

public class UpdateUserActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityUpdateUserBinding binding;

    private UserListModel userListModel;
    private SQLiteDatabaseHelper dbHelper;

    private String strFullName = "", strEmail = "", strPhone = "", strUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new SQLiteDatabaseHelper(this);
        if (getIntent().hasExtra("userId")) {
            strUserId = getIntent().getStringExtra("userId");
        }

        HeaderInit();
        EditTextListener();
        GetUserDetails();
        ClickListener();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.update_user_info));
        binding.header.ivBack.setVisibility(View.VISIBLE);
    }

    private void GetUserDetails() {
        userListModel = dbHelper.getUserDetails(strUserId);

        binding.etFullName.setText(userListModel.getUserName());
        binding.etEmail.setText(userListModel.getUserEmail());
        binding.etPhone.setText(userListModel.getUserPhone());
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
        
        strFullName = binding.etFullName.getText().toString().trim();
        strEmail = binding.etEmail.getText().toString().trim();
        strPhone = binding.etPhone.getText().toString().trim();
        
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
        }

        return isValidate;
    }

    private void UpdateUser() {
        long tsLong = System.currentTimeMillis()/1000;
        String ts = Utils.getDate(tsLong);
        Log.d("Current_Date", ts);
        boolean isInserted = dbHelper.updateUser(strUserId, ts, strFullName, strEmail, strPhone);
        if (isInserted) {
            Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Some Problem.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ClickListener() {
        binding.btUpdate.setOnClickListener(this);
        binding.header.ivBack.setOnClickListener(this);
        binding.tvChangePassword.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btUpdate:
                if (isValidation()) {
                    UpdateUser();
                }
                break;

            case R.id.tvChangePassword:
                startActivity(new Intent(UpdateUserActivity.this, ChangePasswordActivity.class).putExtra("userId", strUserId));
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }
}
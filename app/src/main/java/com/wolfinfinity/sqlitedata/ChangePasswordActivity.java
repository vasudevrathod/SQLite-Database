package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.Model.UserListModel;
import com.wolfinfinity.sqlitedata.databinding.ActivityChnagePasswordActivtyBinding;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityChnagePasswordActivtyBinding binding;

    private SQLiteDatabaseHelper dbHelper;

    private String strCurrentPassword, strNewPassword, strConfirmNewPassword, strUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChnagePasswordActivtyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new SQLiteDatabaseHelper(this);
        if (getIntent().hasExtra("userId")) {
            strUserId = getIntent().getStringExtra("userId");
        }

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
        } else if (!strConfirmNewPassword.equals(strNewPassword)) {
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
                long tsLong = System.currentTimeMillis()/1000;
                String ts = Utils.getDate(tsLong);
                Log.d("Current_Date", ts);
                if (isValidation()) {
                    if (dbHelper.isCurrentPasswordMatch(strUserId, strCurrentPassword)) {
                        boolean isPasswordUPdate = dbHelper.updateUserPassword(strUserId, ts, strNewPassword);
                        if (isPasswordUPdate) {
                            Toast.makeText(this, getString(R.string.user_password_updated), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, getString(R.string.err_something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }
}
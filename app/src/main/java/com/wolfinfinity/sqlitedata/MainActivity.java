package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.wolfinfinity.sqlitedata.Adapter.UserListAdapter;
import com.wolfinfinity.sqlitedata.Model.UserListModel;
import com.wolfinfinity.sqlitedata.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final ArrayList<UserListModel> userListModels = new ArrayList<>();
    private SQLiteDatabaseHelper dbHelper;
    private UserListAdapter userListAdapter;

    private boolean isFirstTimeBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new SQLiteDatabaseHelper(MainActivity.this);

        HeaderInit();
        InitUserList();
    }

    private void HeaderInit() {
        binding.header.tvHeaderTitle.setText(getString(R.string.user_list));
    }

    private void InitUserList() {
        binding.rvUserLoginScreen.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter = new UserListAdapter(userListModels, position -> { }, position -> {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Delete");
            alertDialog.setMessage("Are you sure to delete this user?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    (dialog, which) ->{
                boolean isDelete = dbHelper.deleteUser(String.valueOf(userListModels.get(position).getId()));
                if (isDelete) {
                    userListModels.remove(position);
                    userListAdapter.notifyDataSetChanged();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    ((dialog, which) -> dialog.dismiss()));
            alertDialog.show();
        }, position -> {
            String strId = String.valueOf(userListModels.get(position).getId());
            startActivity(new Intent(MainActivity.this, UpdateUserActivity.class).putExtra("userId", strId));
        });
        binding.rvUserLoginScreen.setAdapter(userListAdapter);
    }

    private void GetList() {
        userListModels.clear();
        userListModels.addAll(dbHelper.getAllUserList());
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (!isFirstTimeBack) {
            isFirstTimeBack = true;
            Toast.makeText(this, getString(R.string.press_back_exit_app_message), Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(() -> isFirstTimeBack = false, 3000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetList();
    }
}
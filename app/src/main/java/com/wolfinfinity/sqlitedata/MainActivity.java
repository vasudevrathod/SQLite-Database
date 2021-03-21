package com.wolfinfinity.sqlitedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.wolfinfinity.sqlitedata.Adapter.UserListAdapter;
import com.wolfinfinity.sqlitedata.Model.UserListModel;
import com.wolfinfinity.sqlitedata.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<UserListModel> userListModels = new ArrayList<>();
    private SQLiteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new SQLiteDatabaseHelper(MainActivity.this);

        SetHeader();
        InitUserList();
    }

    private void SetHeader() {
        binding.header.tvHeaderTitle.setText(getString(R.string.user_list));
        binding.header.ivLogout.setVisibility(View.VISIBLE);
    }

    private void InitUserList() {
        binding.rvUserLoginScreen.setLayoutManager(new LinearLayoutManager(this));
        UserListAdapter userListAdapter = new UserListAdapter(userListModels, position -> { });
        binding.rvUserLoginScreen.setAdapter(userListAdapter);
    }

    private void GetList() {

    }
}
package com.wolfinfinity.sqlitedata.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wolfinfinity.sqlitedata.Model.UserListModel;
import com.wolfinfinity.sqlitedata.OnItemClick;
import com.wolfinfinity.sqlitedata.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<UserListModel> userList;
    private OnItemClick onItemClick, onItemDelete, onItemEdit;

    public UserListAdapter(ArrayList<UserListModel> userList, OnItemClick onItemClick, OnItemClick onItemDelete, OnItemClick onItemEdit) {
        this.userList = userList;
        this.onItemClick = onItemClick;
        this.onItemDelete = onItemDelete;
        this.onItemEdit = onItemEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_user_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        UserListModel model = userList.get(position);
        viewHolder.tvUserName.setText(model.getUserName());
        viewHolder.tvUserEmail.setText(model.getUserEmail());
        viewHolder.tvUserPhone.setText(model.getUserPhone());
        viewHolder.tvTime.setText(model.getTime());

        viewHolder.itemView.setOnClickListener(v -> onItemClick.itemClick(position));

        viewHolder.ivDelete.setOnClickListener(v -> onItemDelete.itemClick(position));

        viewHolder.ivEdit.setOnClickListener(v -> onItemEdit.itemClick(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvTime, tvUserEmail, tvUserPhone;
        ImageView ivDelete, ivEdit;

        public ViewHolder(View view) {
            super(view);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvTime = view.findViewById(R.id.tvTime);
            tvUserEmail = view.findViewById(R.id.tvUserEmail);
            tvUserPhone = view.findViewById(R.id.tvUserPhone);
            ivDelete = view.findViewById(R.id.ivDelete);
            ivEdit = view.findViewById(R.id.ivEdit);
        }
    }
}

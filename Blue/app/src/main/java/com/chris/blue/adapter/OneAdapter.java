package com.chris.blue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.chris.blue.R;
import com.chris.blue.bean.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OneAdapter extends PagedListAdapter<User,OneAdapter.OneViewHolder> {


    public OneAdapter() {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public OneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.db_item,null);
        return new OneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneViewHolder holder, int position) {
        User user = getItem(position);
        if (user == null) {
            holder.tvName.setText("loading");
        }
        holder.tvName.setText(user.getName());
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<User> currentList) {
        super.onCurrentListChanged(currentList);
    }

    @Nullable
    @Override
    public PagedList<User> getCurrentList() {
        return super.getCurrentList();
    }

    static class OneViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public OneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}

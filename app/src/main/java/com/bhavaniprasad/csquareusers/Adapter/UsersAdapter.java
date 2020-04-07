package com.bhavaniprasad.csquareusers.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavaniprasad.csquareusers.Interface.OnUsersclicklistener;
import com.bhavaniprasad.csquareusers.Model.UsersData;
import com.bhavaniprasad.csquareusers.R;
import com.bhavaniprasad.csquareusers.SecondActivity;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.Customview> {
    private ArrayList<UsersData> arrList;
    private Context cnt;
    private LayoutInflater layoutInflater;
    private OnUsersclicklistener clickuser;

    public UsersAdapter(Context context, ArrayList<UsersData> userViewModels, SecondActivity onUsersclicklistener1) {
        this.arrList=userViewModels;
        this.cnt=context;
        this.clickuser=onUsersclicklistener1;
    }

    @NonNull
    @Override
    public UsersAdapter.Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.users_row_layout, parent, false);
        return new Customview(view,clickuser);
    }

    @Override
    public void onBindViewHolder(@NonNull Customview holder, int position) {
        final Customview customview=(Customview) holder;
        customview.emailaddr.setText(arrList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class Customview extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView emailaddr;
        OnUsersclicklistener click;
        public Customview(@NonNull View itemView, OnUsersclicklistener clickuser) {
            super(itemView);
            this.click=clickuser;
            emailaddr = itemView.findViewById(R.id.emailaddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            click.onclickuser(getAdapterPosition());
        }
    }
}

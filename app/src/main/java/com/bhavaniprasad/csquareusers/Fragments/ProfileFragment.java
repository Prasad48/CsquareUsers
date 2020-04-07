package com.bhavaniprasad.csquareusers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bhavaniprasad.csquareusers.Model.UsersData;
import com.bhavaniprasad.csquareusers.NetworkConnection;
import com.bhavaniprasad.csquareusers.R;
import com.bhavaniprasad.csquareusers.Repository;
import com.bhavaniprasad.csquareusers.SecondActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends Fragment {
    private Context context;
    private Repository repository;
    private UsersData dbdata;
    private int vl,v;
    Bundle mainbundle;
    ImageView imageView;
    private int position;
    TextView fname,lname,email,id;


    private ProgressBar progressBar;
    private Button btn_retry;
    private RelativeLayout network2;
    NetworkConnection object;
    SecondActivity activity;

    public ProfileFragment(SecondActivity secondActivity, Bundle bundle) {
        this.mainbundle=bundle;
        this.activity=secondActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile,container,false);
        repository=new Repository(getContext());
        dbdata=new UsersData();
        imageView=view.findViewById(R.id.repoavatar);
        progressBar = view.findViewById(R.id.progress_bar);
        btn_retry = view.findViewById(R.id.retry2);
        network2 = view.findViewById(R.id.no_network2);
        object = new NetworkConnection();

        fname=view.findViewById(R.id.profilefname);
        lname=view.findViewById(R.id.profilelname);
        email=view.findViewById(R.id.profileemail);
        id=view.findViewById(R.id.profileuserid);
        context=this.getContext();
        progressBar.setVisibility(View.VISIBLE);
        if(!object.isConnected(context)){
            network2.setVisibility(View.VISIBLE);
        }
        else{
            Bundle b2=mainbundle;
            if(b2!=null){
                position=b2.getInt("position");
                position+=1;

                repository.getTask(position).observe(getActivity(), new Observer<UsersData>() {
                    @Override
                    public void onChanged(UsersData usersData) {
                        dbdata=usersData;
                        if(dbdata!=null){
                            id.setText(""+dbdata.getId());
                            email.setText(""+dbdata.getEmail());
                            fname.setText(""+dbdata.getFirst_name());
                            lname.setText(""+dbdata.getLast_name());
                            Picasso.with(imageView.getContext()).load(dbdata.getAvatar()).into(imageView);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });
            }
        }
        return view;
    }

}

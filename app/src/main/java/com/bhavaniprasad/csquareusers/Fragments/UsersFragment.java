package com.bhavaniprasad.csquareusers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bhavaniprasad.csquareusers.Adapter.UsersAdapter;
import com.bhavaniprasad.csquareusers.AppDatabase;
import com.bhavaniprasad.csquareusers.Model.UserAuthDetails;
import com.bhavaniprasad.csquareusers.Model.UsersData;
import com.bhavaniprasad.csquareusers.Model.UsersList;
import com.bhavaniprasad.csquareusers.NetworkConnection;
import com.bhavaniprasad.csquareusers.R;
import com.bhavaniprasad.csquareusers.Repository;
import com.bhavaniprasad.csquareusers.SecondActivity;
import com.bhavaniprasad.csquareusers.ViewModel.UsersViewModel;

import java.util.ArrayList;

public class UsersFragment extends Fragment {
    private UsersViewModel usersViewModel;
    private RecyclerView recyclerView;
    AppDatabase database;
    private ArrayList<UsersList> usersresultsList;
    private ArrayList<UsersData> usersfrommdb2;

    private ProgressBar progressBar;
    private Button btn_retry;
    private RelativeLayout network2;
    NetworkConnection object;
    private UsersAdapter usersAdapter;
    private Context context;
    private Repository repository;
    FragmentManager manager;
    TextView userscount;


    FragmentTransaction fr;

    UsersData setdata;
    private UserAuthDetails instance;
    private int totalpagescount,globalvar;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_layout,container,false);
        fr= getFragmentManager().beginTransaction();
        instance=UserAuthDetails.getInstance();
        totalpagescount=instance.total_pages;
        globalvar=1;
        userscount=view.findViewById(R.id.totaluserscount);
        repository = new Repository(getContext());
        repository.deleteeverything();
        usersfrommdb2=new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);
        manager=this.getActivity().getSupportFragmentManager();
        btn_retry = view.findViewById(R.id.retry2);
        network2 = view.findViewById(R.id.no_network2);
        object = new NetworkConnection();
        context=this.getContext();
        recyclerView=view.findViewById(R.id.usersrecyclerview);

        if(!object.isConnected(context)){
            network2.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            network2.setVisibility(View.INVISIBLE);
        }

        database = Room.databaseBuilder(context, AppDatabase.class, "Users")
                .allowMainThreadQueries()
                .build();



        usersViewModel = ViewModelProviders.of(getActivity()).get(UsersViewModel.class);

        final LiveData<ArrayList<UsersList>> observelist=usersViewModel.getUsersList(context);
        observelist.observe(this, new Observer<ArrayList<UsersList>>() {
            @Override
            public void onChanged(final ArrayList<UsersList> usersLists) {
                usersresultsList=new ArrayList<>();
                globalvar++;
                usersresultsList= usersLists;
                int datasize=usersresultsList.get(0).getData().size();
                if(datasize>0){
                    for(int i=0;i<datasize;i++){
                        setdata=new UsersData();
                        setdata.setFirst_name(usersresultsList.get(0).getData().get(i).getFirst_name());
                        setdata.setLast_name(usersresultsList.get(0).getData().get(i).getLast_name());
                        setdata.setEmail(usersresultsList.get(0).getData().get(i).getEmail());
                        setdata.setAvatar(usersresultsList.get(0).getData().get(i).getAvatar());

                        usersfrommdb2.add(setdata);
                        repository.insertTask(setdata);

                    }
                    usersAdapter = new UsersAdapter(context, usersfrommdb2, (SecondActivity) context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(usersAdapter);
                    if(globalvar > instance.total_pages){
                        progressBar.setVisibility(View.INVISIBLE);
                        userscount.setText("ToTal Users Count: "+usersfrommdb2.size());
                        observelist.removeObserver(this);
                    }

                }

            }
        });


        usersViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        usersViewModel.getShow_networkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.INVISIBLE);
                    network2.setVisibility(View.VISIBLE);
                } else {
                    network2.setVisibility(View.GONE);
                }
            }
        });


        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(object.isConnected(context)){
                    progressBar.setVisibility(View.VISIBLE);
                    usersViewModel.retry();
                }
            }
        });

        return view;


    }

}



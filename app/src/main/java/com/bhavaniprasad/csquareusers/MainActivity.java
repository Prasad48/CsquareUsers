package com.bhavaniprasad.csquareusers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bhavaniprasad.csquareusers.DAO.UsersDao;
import com.bhavaniprasad.csquareusers.Fragments.UsersFragment;
import com.bhavaniprasad.csquareusers.Model.LoginRequest;
import com.bhavaniprasad.csquareusers.Model.TotalPages;
import com.bhavaniprasad.csquareusers.Model.UserAuthDetails;
import com.bhavaniprasad.csquareusers.Model.UsersData;
import com.bhavaniprasad.csquareusers.ViewModel.LoginViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    private LoginViewModel loginViewModel;
    Call<Object> UsersListCall;
    AppDatabase database;
    Button submit;
    private ProgressBar progressBar;
    private Button btn_retry;
    private RelativeLayout layout_nonetwork;
    NetworkConnection object;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        btn_retry = findViewById(R.id.retry);
        layout_nonetwork = findViewById(R.id.no_network);
        object = new NetworkConnection();
        loginViewModel = ViewModelProviders.of(MainActivity.this).get(LoginViewModel.class);

        if(!object.isConnected(this)){
            layout_nonetwork.setVisibility(View.VISIBLE);
        }
        else {
            layout_nonetwork.setVisibility(View.INVISIBLE);
        }


        email=findViewById(R.id.email);
        password=findViewById(R.id.passord);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                if(email.getText().length()==0 || password.getText().length()==0 ){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setMessage("Please Enter valid Details");
                        dialog.setCancelable(true);

                        dialog.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = dialog.create();
                        alert11.show();

                    }
                else{
                    getdata();
                }
            }
        });



        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!object.isConnected(MainActivity.this)){
                    progressBar.setVisibility(View.INVISIBLE);
                    layout_nonetwork.setVisibility(View.VISIBLE);
                }
                else{
                    layout_nonetwork.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    public void getdata(){

        if(email.getText().length()!=0 && password.getText().length()!=0) {
            progressBar.setVisibility(View.VISIBLE);
            UserAuthDetails obj = UserAuthDetails.getInstance();
            obj.email=email.getText().toString();
            obj.password=password.getText().toString();
            loginViewModel.getMutableLiveData(MainActivity.this).observe(MainActivity.this, new Observer<ArrayList<LoginRequest>>() {


                @Override
                public void onChanged(ArrayList<LoginRequest> loginRequests) {
                    if(loginRequests.size()>0 && loginRequests.get(0).getToken()!="empty"){
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setMessage("Login Successfull!!");
                        dialog.setCancelable(true);

                        dialog.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int id) {
                                        loginViewModel.getPageCountMutableLiveData(MainActivity.this).observe(MainActivity.this, new Observer<JsonObject>() {


                                            @Override
                                            public void onChanged(JsonObject jsonObject) {
                                                if(jsonObject.size()>0){
                                                    UserAuthDetails instance =UserAuthDetails.getInstance();
                                                    String jsonString = jsonObject.toString();
                                                    Gson gson = new Gson();
                                                    TotalPages count = gson.fromJson(jsonString, TotalPages.class);
                                                    instance.total_pages=count.getTotal_pages();
                                                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                                                    startActivity(i);
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    dialog.cancel();
                                                }
                                            }

                                        });
                                    }
                                });
                        AlertDialog alert11 = dialog.create();
                        alert11.show();
                    }
                }
            });

        }



        loginViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        loginViewModel.getShow_networkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.INVISIBLE);
                    layout_nonetwork.setVisibility(View.VISIBLE);
                } else {
                    layout_nonetwork.setVisibility(View.GONE);
                }
            }
        });
    }
}

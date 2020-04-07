package com.bhavaniprasad.csquareusers.ViewModel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.bhavaniprasad.csquareusers.Model.UserAuthDetails;
import com.bhavaniprasad.csquareusers.Model.UsersList;
import com.bhavaniprasad.csquareusers.Remote.ApiMaker;
import com.bhavaniprasad.csquareusers.Remote.LoginApiService;
import java.util.ArrayList;import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends ViewModel {

    private ArrayList<UsersList> arrlist;

    Call<UsersList> usersListcall;
    UserAuthDetails inst;
    public MutableLiveData<ArrayList<UsersList>> UsersListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();


    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }
    public MutableLiveData<Boolean> getShow_networkError() {
        return show_networkError;
    }





    public MutableLiveData<ArrayList<UsersList>> getUsersList(final Context context) {
        LoginApiService apiService = new ApiMaker().getService();
        try {
            inst =UserAuthDetails.getInstance();
            int totalpagescount=inst.total_pages;
            if(totalpagescount!=0){
                for(int i=1;i<=totalpagescount;i++){
                    usersListcall= apiService.getUsersList(i);
                    usersListcall.enqueue(new Callback<UsersList>() {
                        @Override
                        public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                            if (response.isSuccessful()) {
                                arrlist=new ArrayList<>();
                                arrlist.add(response.body());
                                UsersListMutableLiveData.setValue(arrlist);
                            } else {
                                Log.d("error message", "error");
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersList> call, Throwable t) {
                            String error_message= t.getMessage();
                            Log.d("Error loading data", error_message);
                            showProgressBar.setValue(false);
                            show_networkError.setValue(true);
                        }

                    });

                }
            }

        }
        catch (Exception e){
            Log.d("Exception","Auth exception");
        }

        return UsersListMutableLiveData;
    }


    public void retry() {

        usersListcall.clone().enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                if (response.isSuccessful()) {
                    arrlist=new ArrayList<>();
                    arrlist.add(response.body());
                    UsersListMutableLiveData.setValue(arrlist);
                } else {
                    Log.d("error message", "error");
                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                String error_message= t.getMessage();
                Log.d("Error loading data", error_message);
                showProgressBar.setValue(false);
                show_networkError.setValue(true);
            }

        });



    }
}

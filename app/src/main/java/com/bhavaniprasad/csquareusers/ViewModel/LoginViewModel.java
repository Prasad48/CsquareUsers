package com.bhavaniprasad.csquareusers.ViewModel;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhavaniprasad.csquareusers.Model.LoginRequest;
import com.bhavaniprasad.csquareusers.Model.UserAuthDetails;
import com.bhavaniprasad.csquareusers.Remote.ApiMaker;
import com.bhavaniprasad.csquareusers.Remote.LoginApiService;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    Call<LoginRequest> loginCall;
    private ArrayList<LoginRequest> loginresponselist;
    UserAuthDetails instance;
    Call<JsonObject> pagecountCall;

    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<LoginRequest>> loginListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<JsonObject> arrayListMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<JsonObject> getPageCountMutableLiveData(final Context context) {
        LoginApiService apiService = new ApiMaker().getService();
        try {

            pagecountCall= apiService.totalcount();
            pagecountCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        arrayListMutableLiveData.setValue(response.body());
                    } else {
                        Log.d("error message", "error");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String error_message= t.getMessage();
                    Log.d("Error loading data", error_message);
                    showProgressBar.setValue(false);
                    show_networkError.setValue(true);
                }

            });

        }
        catch (Exception e){
            Log.d("Exception","Auth exception");
        }

        return arrayListMutableLiveData;
    }


    public MutableLiveData<ArrayList<LoginRequest>> getMutableLiveData(final Context context) {
        LoginApiService apiService = new ApiMaker().getService();
         instance=UserAuthDetails.getInstance();
        try {
            String email=instance.email;
            String password=instance.password;
            loginCall= apiService.login(email,password);
            loginresponselist=new ArrayList<>();
            loginCall.enqueue(new Callback<LoginRequest>() {
                @Override
                public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                    if (response.isSuccessful()) {
                        loginresponselist.add(response.body());
                        loginListMutableLiveData.setValue(loginresponselist);
                    } else {
                        try {
                            loginresponselist.add(response.body());
                            String errorsplit=response.errorBody().string();
                            errorsplit=errorsplit.replaceAll("[{}\"]","");
                           String errorarrar[]= errorsplit.split(":");
                            String errormessage=errorarrar[1];
                            Toast.makeText(context,errormessage, Toast.LENGTH_LONG).show();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setMessage(errormessage);
                            dialog.setCancelable(true);
                            dialog.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            showProgressBar.setValue(false);
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = dialog.create();
                            alert11.show();
                            Log.d("error message", "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginRequest> call, Throwable t) {
                    String error_message= t.getMessage();
                    Log.d("Error loading data", error_message);
                    showProgressBar.setValue(false);
                    show_networkError.setValue(true);
                    Toast.makeText(context,"Please Check Network Connection,Try again Later",Toast.LENGTH_LONG).show();
                }

            });
        }
        catch (Exception e){
            Log.d("Exception","Auth exception");
        }

        return loginListMutableLiveData;
    }


    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }
    public MutableLiveData<Boolean> getShow_networkError() {
        return show_networkError;
    }


}

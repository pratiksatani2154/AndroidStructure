package com.theone.demopermission.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.theone.demopermission.R;


import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class RetrofitCallback<T> implements Callback<T> {

    private ProgressDialog progressDialog;
    private Context context;
    private boolean validateResponse = true;

    public RetrofitCallback(Context c) {
        context = c;
    }

    public RetrofitCallback(Context c, ProgressDialog dialog) {
        progressDialog = dialog;
        context = c;
    }

    public RetrofitCallback(Context context, ProgressDialog progressDialog, boolean validateResponse) {
        this.progressDialog = progressDialog;
        this.context = context;
        this.validateResponse = validateResponse;
    }

    public RetrofitCallback(Context context, boolean validateResponse) {
        this.context = context;
        this.validateResponse = validateResponse;
    }

    public abstract void onSuccess(T arg0);

    public abstract void onError(ResponseBody responseBody, int errorCode);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (context != null) {

            if (!(((AppCompatActivity) context).isFinishing()) && progressDialog != null && progressDialog.isShowing()) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }

        if (response.isSuccessful() && response.code() == 200) {
            onSuccess(response.body());
        } else {
            onError(response.errorBody(), response.code());
        }
    }


    @Override
    public void onFailure(Call<T> call, Throwable error) {
        if (!validateResponse)
            return;

        String errorMsg;
        error.printStackTrace();
        if (error instanceof SocketTimeoutException) {
            errorMsg = context.getString(R.string.str_connection_timeout);
        } else if (error instanceof UnknownHostException) {
            errorMsg = context.getString(R.string.str_no_internet);
        } else if (error instanceof ConnectException) {
            errorMsg = context.getString(R.string.str_server_not_responding);
        } else if (error instanceof JSONException || error instanceof JsonSyntaxException) {
            errorMsg = context.getString(R.string.str_parse_error);
        } else if (error instanceof IOException) {
            errorMsg = error.getMessage();
        } else {
            errorMsg = context.getString(R.string.str_something_wrong);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
    }
}

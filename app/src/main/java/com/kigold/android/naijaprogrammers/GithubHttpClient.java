package com.kigold.android.naijaprogrammers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;

/**
 * Created by Kigold on 3/10/2017.
 */

public class GithubHttpClient {

    private  final String url = "https://api.github.com/search/users?q=location:lagos+language:java&page=5";

    private  AsyncHttpClient client;

    public  GithubHttpClient(final Context context, String url, RequestParams params) {
        // show progress DIagloag
        //prgDiag.show();

        //make restful call
        client = new AsyncHttpClient();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                //hide progress Dialog
                //prgDiag.hide();
                try {
                    //Json object
                    try {
                        JSONObject obj = new JSONObject(response.toString());
                        //
                        if(statusCode == 200){
                            Toast.makeText(context, "You are successfully registered!", Toast.LENGTH_LONG).show();

                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    //when the JSON response has status boolean value assigned with true
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //prgDiag.hide();
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    /*public void getResult() throws JSONException {
        get(url, null, new JsonHttpResponseHandler()){

        }
    }*/
}

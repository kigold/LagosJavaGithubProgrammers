package com.kigold.android.naijaprogrammers;


import android.content.Context;
import android.widget.Toast;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.Configurable;

/**
 * Created by Kigold on 3/10/2017.
 */

public class GithubClientUsage {
    private JSONObject programmers;
    private JSONArray programmersArray;
    public void getProgrammers(final Context context, String url) throws JSONException {
        GithubClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                programmers = response;
                Toast.makeText(context, "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray result) {
                // Pull out the first event on the public timeline
                //JSONObject firstEvent = timeline.get(0);
                //String tweetText = firstEvent.getString("text");
                Toast.makeText(context, "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
                programmersArray = result;

                // Do something with the response
                //System.out.println(tweetText);
            }
        });
    }

    public JSONObject GetResult(final Context context, String url) {
        try {
            getProgrammers(context, url);
        }
        catch (JSONException e) {

        }
        return programmers;
    }



}

/*
      AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://www.google.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Toast.makeText(getApplicationContext(), "You are Started registered!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), "YFailure", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
 */

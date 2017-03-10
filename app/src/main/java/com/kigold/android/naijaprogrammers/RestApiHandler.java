package com.kigold.android.naijaprogrammers;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;


/**
 * Created by Kigold on 3/9/2017.
 */

public class RestApiHandler {

    //properties
    private String mUrl;// = "https://api.github.com/search/users?q=location:lagos+language:java&page=5";
    private HttpsURLConnection mConn;
    private JSONObject mJsonResponse;
    private String responeCode;

    //constructor
    public RestApiHandler(String url) {
        this.mUrl = url;

        //create Connection
        //createConnection();

    }


    // create httpCache
    public static void createHttpCacheDir(Context context){
        try {
            File httpCacheDIr = new File(context.getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDIr, httpCacheSize);
        } catch (IOException e){
            Log.i(TAG, "HTTP response cache installation failed:" + e);

        }

    }
    //Commit cached data
    public static void flushHttpCache(){
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

    public String callEndPoint (List<Pair> headers) {
        //create URL
        try {
            URL url = new URL(mUrl);
            try {
                HttpsURLConnection mConn = (HttpsURLConnection) url.openConnection();
                //Set HTTP Method
                //mConn.setRequestMethod("POST");
                //default method is GET
                // add headers
                addRequestHeader(headers);
                try {
                    if (mConn.getResponseCode() == 200) {
                        InputStream responseBody = mConn.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        // create JSON Object
                        jsonReader.beginObject();
                        while(jsonReader.hasNext()) {
                            try {
                                mJsonResponse.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            catch (JSONException e){

                            }
                        }
                        jsonReader.close();

                    } else {
                        return null;
                    }
                }
                catch (MalformedURLException e){

                }
            }
            catch (IOException e){
                //handle error
            }
            finally {
                mConn.disconnect();
            }
        }
        catch (MalformedURLException e){
            //handle error
        }
        return "OK";
    }
    private void addRequestHeader(List<Pair> headers) {
        // create header from key value Pair
        for (Pair header: headers) {
            mConn.setRequestProperty(header.getKey(), header.getValue());
        }
    }
    

 //end of class
}

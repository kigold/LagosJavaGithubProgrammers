package com.kigold.android.naijaprogrammers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //
    private String url = "https://api.github.com/search/users?q=location:lagos+language:java&page=5";
    //get Progress Dialog Obj
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        //Create httpRequestCache
        RestApiHandler.createHttpCacheDir(getApplicationContext());

        //ASync
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                RestApiHandler restApiHandler = new RestApiHandler(url);

                // create a List of key value pair for headers
                List<Pair> headers = new ArrayList<Pair>();
                headers.add(new Pair("User-Agent", "Get_Lagos_Java_Developers-v1"));
                headers.add(new Pair("Contact-Me", "kingsleybox@yahoo.com"));


                restApiHandler.callEndPoint(headers);

            }
        });*/

        //Set Progress Dialog Text
        prgDialog = new ProgressDialog(getApplicationContext());
        prgDialog.setMessage("Loading Content ...");
        prgDialog.setCancelable(false);

        //Restful call
        //GithubHttpClient githubHttpClient= new GithubHttpClient(getApplicationContext(), url, null);
        //prgDialog.show();
        GithubHttpClient(url, null);



        // get our list view
        ListView theListView = (ListView) findViewById(R.id.activity_main);

        // prepare elements to display
        final ArrayList<Model> items = Model.getTestingList();

        // add custom btn handler to first list item
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Commit cached files to memory so it can be used later
        //RestApiHandler.flushHttpCache();
    }

    public  void GithubHttpClient(String url, RequestParams params) {
        // show progress DIagloag
        //prgDiag.show();
        final Context context = getApplicationContext();

        //make restful call
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler() {
            
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
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
                        //e.printStackTrace();
                    }
                    //when the JSON response has status boolean value assigned with true

                    Toast.makeText(context, "e.getMessage()", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //prgDiag.hide();
                Toast.makeText(context, "errorResponse.toString()", Toast.LENGTH_LONG).show();
            }
        });

    }
}
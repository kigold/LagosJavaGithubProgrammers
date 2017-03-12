package com.kigold.android.naijaprogrammers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //
    private String url = "https://api.github.com/search/users?q=location:lagos+language:java&page=";
    //private String url = "&page=5";
    private Integer length = 0;
    int toastCount = 0;
    int preLast;

    private int resultTotalCount = 0;
    private int resultCurrentPage = 1;
    private int itemsInResultPage = 0;
    private int currentTotalItems = 0;

    //getters and setters


    public int getCurrentTotalItems() {
        return currentTotalItems;
    }

    public void setCurrentTotalItems(int currentTotalItems) {
        this.currentTotalItems = currentTotalItems;
    }

    public int getResultTotalCount() {
        return resultTotalCount;
    }

    public void setResultTotalCount(int resultTotalCount) {
        this.resultTotalCount = resultTotalCount;
    }

    public int getResultCurrentPage() {
        return resultCurrentPage;
    }

    public void setResultCurrentPage(int resultCurrentPage) {
        this.resultCurrentPage = resultCurrentPage;
    }

    public int getItemsInResultPage() {
        return itemsInResultPage;
    }

    public void setItemsInResultPage(int itemsInResultPage) {
        this.itemsInResultPage = itemsInResultPage;
    }

    //get Progress Dialog Obj
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        //Set Progress Dialog Text
        /*prgDialog = new ProgressDialog(getApplicationContext());
        prgDialog.setMessage("Loading Content ...");
        prgDialog.setCancelable(false);*/
        //prgDialog.show();

        // get our list view
        final ListView theListView = (ListView) findViewById(R.id.list_view);

        //ListView Header
        final TextView list_view_header = (TextView) findViewById(R.id.list_view_header);
        list_view_header.setText(currentTotalItems + " out of " + resultTotalCount);


        // prepare elements to display
        //final ArrayList<Model> items = Model.getTestingList();
        final ArrayList<Model> items = new ArrayList<>();

        // add custom btn handler to first list item
        /*items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON tests" + length.toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        //call github url to get list of users
        githubHttpClient(url+resultCurrentPage, null, System.getProperty("http.agent"), new Callback<JSONArray>() {
            @Override
            public void onResponse(JSONArray listOfProgrammers) {
                if(listOfProgrammers.length() != 0) {
                    //set results page number,  inc by 1
                    setResultCurrentPage(resultCurrentPage + 1);
                    // pass list of programmers to ListView Adapter
                    processDataFromGithubCallback(listOfProgrammers, adapter, list_view_header);
                }
                else{
                    Toast.makeText(getApplicationContext(), getItemsInResultPage() + " items where received, list completely downloaded " + toastCount, Toast.LENGTH_LONG).show();
                }

            }
        });

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS test", Toast.LENGTH_SHORT).show();
                //Share User with friends
                shareit(v.getTag().toString());
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

        /*
        if the List gotten from Endpoint is incomplete,
        Fetch the remaining List from End when User scrolls to the buttom
        using SCroll Listener
        */
        theListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                switch (view.getId()){
                    case R.id.list_view:
                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if(lastItem == totalItemCount){
                            if(preLast !=lastItem){
                                //to avoid multiple calls for last item
                                preLast = lastItem;
                                Toast.makeText(getApplicationContext(), "Programmers1: " + "Scrolled to Bottom" + toastCount , Toast.LENGTH_SHORT).show();
                                toastCount++;
                                //if all data result is not yet retrieved, that is there are still more to be gotten
                                int test_condi1 = resultTotalCount/itemsInResultPage;
                                if(resultCurrentPage == 1 || (resultTotalCount/itemsInResultPage) >= resultCurrentPage-1){
                                    String newUrl = url+resultCurrentPage;
                                    //call github url to get list of users
                                    githubHttpClient(url+resultCurrentPage, null, System.getProperty("http.agent"), new Callback<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray listOfProgrammers) {
                                            if(listOfProgrammers.length() != 0) {
                                                //set results page number,  inc by 1
                                                setResultCurrentPage(resultCurrentPage + 1);
                                                // pass list of programmers to ListView Adapter
                                                processDataFromGithubCallback(listOfProgrammers, adapter, list_view_header);
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), getItemsInResultPage() + " items where received, list completely downloaded " + toastCount, Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                                }
                            }
                        }
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Commit cached files to memory so it can be used later
        //RestApiHandler.flushHttpCache();
    }

    public void githubHttpClient(String url, RequestParams params, final String user_agent, final Callback<JSONArray> callback) {
        // show progress DIagloag
        //prgDiag.show();
        final Context context = getApplicationContext();

        //make restful call
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", user_agent);
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
                            JSONArray resultList = (JSONArray) response.get("items");
                            setResultTotalCount((Integer)response.get("total_count"));
                            setItemsInResultPage(resultList.length());
                            setCurrentTotalItems(getCurrentTotalItems() + resultList.length());
                            Toast.makeText(context, "Retrieved a total Number of " + getItemsInResultPage() + " items", Toast.LENGTH_LONG).show();
                            if (callback != null) {
                                callback.onResponse(resultList);
                            }
                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        //e.printStackTrace();
                    }
                    //when the JSON response has status boolean value assigned with true

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //prgDiag.hide();
                Toast.makeText(context, "error " + statusCode, Toast.LENGTH_LONG).show();
            }
        });

    }
    //share user with friends intent
    public void shareit(String tag) {
        //get Username and url by spliting tag with "|"
        //String username = tag.split("[|]")[0];
        //String url = tag.split("[|]")[1];
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //String shareBody = MessageFormat.format("Check out this awesome developer @github {0}, <github {1}>.", username, url);
        String shareBody =  MessageFormat.format("Check out this awesome developer @{0}", tag);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lagos Java Developer on Github");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void processDataFromGithubCallback(JSONArray listOfProgrammers, FoldingCellListAdapter adapter, TextView list_view_header){
        //Call back method that processes and pass the http response JSonarray to the List Adapter
        for(int i = 0 ; i < listOfProgrammers.length() ; i++){
            try {
                adapter.add(new Model(
                        listOfProgrammers.getJSONObject(i).getString("login"),//username
                        listOfProgrammers.getJSONObject(i).getString("html_url"),//githuburl
                        listOfProgrammers.getJSONObject(i).getString("avatar_url")//avatar
                ));
            }
            catch (JSONException e){
            }
        }
        //update ListVIew Header TextView
        list_view_header.setText(currentTotalItems + " out of " + resultTotalCount);
    }
}

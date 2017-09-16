package com.example.android.lagosjavadevelopers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OLANUBI J. A on 9/1/2017.
 */


public class loadList extends AppCompatActivity {
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    List<SourceItem> sourceItemList;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar progressBar;
    public static final String URL = "https://api.github.com/search/users?q=language:java+location:lagos";
   // public static String profile_url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        sourceItemList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        sendRequest();
    }


    public void sendRequest() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Hide ProgressBar after load completion
                        progressBar.setVisibility(View.INVISIBLE);

                        // display response

                        try {
                            //get the count of items loaded;
                            //int count = response.getInt("total_count");

                            // get the array of developer details
                            JSONArray items = response.getJSONArray("items");

                            //Now loop through all the JSONobjects in the items array and extract specific details
                            //in each JSONObject
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject details = items.getJSONObject(i);

                                //create a SourceItem object and give it the values from the JSONObject
                                SourceItem sourceItem = new SourceItem(details.getString("login"), details.getString("avatar_url"), details.getString("url"));




                                //add sourceItem to sourceItemList
                                sourceItemList.add(sourceItem);

                                //create a custom recyclerview object
                                mAdapter = new RecyclerAdapter(sourceItemList, new RecyclerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(SourceItem item) {
                                        ProfileDetails.url = item.getURL();
                                        Intent showDetails = new Intent(getApplicationContext(), ProfileDetails.class);
                                        startActivity(showDetails);
                                    }
                                });
                                // Add mAdapter to RecyclerView
                                mRecyclerView.setAdapter(mAdapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If error occurs, display error in toast
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(getRequest);
    }


}

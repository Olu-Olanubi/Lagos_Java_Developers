package com.example.android.lagosjavadevelopers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileDetails extends AppCompatActivity {


    ImageView user_image;
    TextView user_name;

    TextView user_URL;
    static String url, userName;
    //static String userName;
    String userImage, fullName, userLink, repo, followers, blog;
    //String fullName;
    //String userLink;
    //String repo;
    //String followers;
    //String blog;
    private ProgressBar progressBar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        user_image = (ImageView) (findViewById(R.id.details_image));
        user_name = (TextView) (findViewById(R.id.details_username));
        user_URL = (TextView) (findViewById(R.id.details_profileURL));
        setRequest();

    }

    //Method to launch JSON Object Request to retrieve user details
    public void setRequest() {
        progressBar1 = (ProgressBar) findViewById(R.id.progress_bar1);
        progressBar1.setVisibility(View.VISIBLE);

        JsonObjectRequest detailRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    // display response
                    @Override
                    public void onResponse(JSONObject reply) {
                        //Hide ProgressBar after load completion
                        progressBar1.setVisibility(View.INVISIBLE);

                        try {
                            //Extract needed details from the JSONObject of the user details
                            userName = reply.getString("login");
                            userImage = reply.getString("avatar_url");
                            fullName = reply.getString("name");
                            userLink = reply.getString("html_url");
                            repo = reply.getString("public_repos");
                            followers = reply.getString("followers");
                            blog = reply.getString("blog");

                            //Assign these data to their respective views
                            user_name.setText(fullName);
                            user_URL.setText(userLink);
                            setImage(userImage);

                            //Make share button visible
                            Button shareButton = (Button) findViewById(R.id.shareButton);
                            shareButton.setVisibility(View.VISIBLE);

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
                    }
                }
        );


        Glide.with(this).load(userImage).into(user_image);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(detailRequest);
    }

    //Method to load user image
    public void setImage(String userImage) {
        Glide.with(this).load(userImage).into(user_image);
    }

    //Method to share user profile
    public void shareProfile(View v) {
        String shareBody = String.format("Check out this awesome developer at @%s, %s", userName, url);
        Intent sharingIntent = new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.setType("text/plain");
        startActivity(sharingIntent);

    }
}



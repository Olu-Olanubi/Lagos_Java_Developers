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
    TextView user_name, user_URL, user_repos, user_followers,user_following, user_blog;
    TextView name, profile,no_of_repos, no_of_followers, no_of_following, userBlog;

    static String url, userName;

    String userImage, fullName, userLink,repo, followers,following, blog;

    private ProgressBar progressBar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        name = (TextView) (findViewById(R.id.name));
        profile = (TextView) (findViewById(R.id.profile));
        no_of_repos =(TextView) (findViewById(R.id.no_of_repos));
        no_of_followers = (TextView) (findViewById(R.id.no_of_followers));
        no_of_following = (TextView) (findViewById(R.id.no_of_following));
        userBlog = (TextView) (findViewById(R.id.userBlog));

        user_image = (ImageView) (findViewById(R.id.details_image));
        user_name = (TextView) (findViewById(R.id.details_username));
        user_URL = (TextView) (findViewById(R.id.details_profileURL));
        user_repos = (TextView) (findViewById(R.id.details_repos));
        user_followers =(TextView) (findViewById(R.id.details_followers));
        user_following = (TextView) (findViewById(R.id.details_following));
        user_blog = (TextView) (findViewById(R.id.details_blog));
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
                        //Hide ProgressBar after load completion and mke TextViews visible
                        progressBar1.setVisibility(View.GONE);
                        name.setVisibility(View.VISIBLE);
                        profile.setVisibility(View.VISIBLE);
                        no_of_repos.setVisibility(View.VISIBLE);
                        no_of_followers.setVisibility(View.VISIBLE);
                        no_of_following.setVisibility(View.VISIBLE);
                        userBlog.setVisibility(View.VISIBLE);

                        try {
                            //Extract needed details from the JSONObject of the user details
                            userName = reply.getString("login");
                            userImage = reply.getString("avatar_url");
                            fullName = reply.getString("name");
                            userLink = reply.getString("html_url");
                            repo = String.valueOf(reply.getInt("public_repos"));
                            followers = String.valueOf(reply.getInt("followers"));
                            following = String.valueOf(reply.getInt("following"));
                            blog = reply.getString("blog");


                            //Assign these data to their respective views
                            user_name.setText(fullName);
                            user_URL.setText(userLink);

                            user_repos.setText(repo);
                            user_followers.setText(followers);
                            user_following.setText(following);
                            user_blog.setText(blog);
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
        String shareBody = String.format("Check out this awesome developer at @%s, %s", userName, userLink);
        Intent sharingIntent = new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.setType("text/plain");
        startActivity(sharingIntent);

    }
}



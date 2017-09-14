package com.example.android.lagosjavadevelopers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
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
    static String url;
    String userName;
    String userImage;
    String fullName;
    String userLink;
    String repo;
    String followers;
    String blog;

    //constructor to initialize method
   // public ProfileDetails() {
        //this.url = loadList.profile_url;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        user_image = (ImageView) (findViewById(R.id.details_image));
        user_name = (TextView) (findViewById(R.id.details_username));
        user_URL = (TextView) (findViewById(R.id.details_profileURL));
        user_URL.setClickable(true);
        user_URL.setMovementMethod(LinkMovementMethod.getInstance());
        setRequest();

    }

    public void setRequest() {

        JsonObjectRequest detailRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject reply) {

                        //Hide ProgressBar after load completion
                        // display response
                        try {
                            userName = reply.getString("login");
                            userImage = reply.getString("avatar_url");
                            fullName = reply.getString("name");
                            userLink = reply.getString("html_url");
                            repo = reply.getString("public_repos");
                            followers = reply.getString("followers");
                            blog = reply.getString("blog");
                            user_name.setText(fullName);
                            user_URL.setText(Html.fromHtml(userLink));
                            setImage(userImage);


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

       public void setImage(String userImage){
            Glide.with(this).load(userImage).into(user_image);
        }
}

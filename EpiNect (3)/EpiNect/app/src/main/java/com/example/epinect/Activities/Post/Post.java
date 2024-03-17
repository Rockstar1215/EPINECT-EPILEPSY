package com.example.epinect.Activities.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.common.Method;
import com.example.epinect.Adapters.RequestAdapter;
import com.example.epinect.DataModels.RequestDataModel;
import com.example.epinect.R;
import com.example.epinect.Utils.Endpoints;
import com.example.epinect.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Post extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter requestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        TextView make_request_button = findViewById(R.id.make_request_button);
        make_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Post.this, MakeRequestActivity.class));
            }
        });
        requestDataModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestAdapter = new RequestAdapter(requestDataModels, this);
        recyclerView.setAdapter(requestAdapter);
        fetchPosts();
    }


    private void fetchPosts() {
        // Make a GET request to the server to fetch posts
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Endpoints.get_requests, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response and populate the list
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Extract data for each post
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String message = jsonObject.getString("message");
                                String url = jsonObject.getString("url");
                                String number = jsonObject.getString("number");

                                // Create a RequestDataModel object and add it to the list
                                RequestDataModel requestDataModel = new RequestDataModel();
                                requestDataModel.setId(id);
                                requestDataModel.setMessage(message);
                                requestDataModel.setUrl(url);
                                requestDataModel.setNumber(number);
                                requestDataModels.add(requestDataModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Notify the adapter that the data set has changed
                        requestAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(Post.this, "Error fetching posts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("PostActivity", "Error fetching posts: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
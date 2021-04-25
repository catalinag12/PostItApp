package com.example.mreadyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mreadyapp.adapters.FeedAdapter;
import com.example.mreadyapp.retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    Button btnAddPost;
    RecyclerView recyclerView;
    ImageView exit;
    FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_feed);

        btnAddPost=findViewById(R.id.btnAddPost);
        recyclerView=findViewById(R.id.recyclerView);
        exit=findViewById(R.id.exitImg);



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        feedAdapter=new FeedAdapter();
        getPosts();

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PostActivity.class);
                startActivity(intent);
            }
        });



    }

    public void getPosts(){
        Call<ResponseBody> postsList= RetrofitClient.getInstance().getApi().getPosts();

        postsList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        JSONArray postsList=jsonObject.getJSONArray("data");
                        List<JSONObject> responses=new ArrayList<JSONObject>();
                        Log.d("Success",postsList.toString());
                        for(int i=0;i<postsList.length();i++)
                            responses.add(postsList.getJSONObject(i));

                        Log.d("Raspunsuri",responses.toString());

                        feedAdapter.setData(responses);
                        recyclerView.setAdapter(feedAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
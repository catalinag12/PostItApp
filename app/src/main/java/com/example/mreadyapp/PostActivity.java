package com.example.mreadyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mreadyapp.retrofit.RetrofitClient;

import java.io.IOException;

public class PostActivity extends AppCompatActivity {

    EditText edtMessage;
    Button btnAddPost;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        edtMessage=findViewById(R.id.edtMessage);
        btnAddPost=findViewById(R.id.btnAddPost);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_create_post);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=edtMessage.getText().toString();
                String token= getIntent().getStringExtra("TOKEN");

                Call<ResponseBody> call= RetrofitClient.getInstance().getApi()
                        .posts(message,"Bearer "+token);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if(response.isSuccessful()) {
                                String s = response.body().string();
                                //Toast.makeText(PostActivity.this, "Succes " + s + token, Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(),FeedActivity.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PostActivity.this,"Fail "+ t.getMessage() ,Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
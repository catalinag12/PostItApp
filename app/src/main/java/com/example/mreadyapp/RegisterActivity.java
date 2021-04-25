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

public class RegisterActivity extends AppCompatActivity {

    EditText edtFullName, edtUsername, edtPassword, edtRepeatPassword;
    Button btnCreateAccount;
    String Username, Password, Display_name;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_register);

        edtFullName=findViewById(R.id.editTextFullName);
        edtUsername=findViewById(R.id.editTextUsername);
        edtPassword=findViewById(R.id.editTextPassword);
        edtRepeatPassword=findViewById(R.id.editTextRepeatPassword);
        btnCreateAccount=findViewById(R.id.btn_create);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        Username=edtUsername.getText().toString();
        Password=edtPassword.getText().toString();
        Display_name=edtFullName.getText().toString();
        String repeatPass=edtRepeatPassword.getText().toString();

        if(Display_name.isEmpty()){
            edtFullName.setError("Complete the full name!");
            edtFullName.requestFocus();
            return;
        }
        if(Username.isEmpty()){
            edtUsername.setError("Complete the username!");
            edtUsername.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            edtPassword.setError("Choose a password!");
            edtPassword.requestFocus();
            return;
        }

        if(repeatPass.isEmpty() || !repeatPass.equals(Password)){
            edtRepeatPassword.setError("Passwords don't match!");
            edtRepeatPassword.requestFocus();
            return;
        }

        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().
                register(Username, Password, Display_name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().string();
                    Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
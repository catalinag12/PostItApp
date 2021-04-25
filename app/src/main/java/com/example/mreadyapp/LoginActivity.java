package com.example.mreadyapp;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mreadyapp.retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvCreateAccount;
    String Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        tvCreateAccount=findViewById(R.id.tvRegister);
        btnLogin=findViewById(R.id.btn_login);


        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        Username=edtUsername.getText().toString();
        Password=edtPassword.getText().toString();

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

        Call<ResponseBody> call= RetrofitClient.getInstance().getApi()
                .login(Username,Password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String token=null;
                JSONObject jsonObject;
                JSONObject obj;
                try {
                    String s= response.body().string();
                     jsonObject = new JSONObject(s);
                     obj= (JSONObject) jsonObject.get("data");
                    token = obj.getString("token");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, "Succes " + token, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("TOKEN", token);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Fara succes " ,Toast.LENGTH_LONG).show();

            }
        });
    }
}
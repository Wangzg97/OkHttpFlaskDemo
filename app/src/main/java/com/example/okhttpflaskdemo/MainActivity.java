package com.example.okhttpflaskdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button send;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.send);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(username.getText());
                String pass = String.valueOf(password.getText());
                String url = "http://192.168.1.115:8090/register";//服务器地址
                SendMessage(url, name, pass);
            }
        });
    }


    private void SendMessage(String url, String userName, String passWord) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", userName);
        formBuilder.add("password", passWord);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
//        Request request = new Request.Builder().url(url).get(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            // 请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"请求失败！！！");
                Log.d(TAG, "e==="+e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            // 请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"请求成功！！！");
                final String res = response.body().string();
                Log.d(TAG,"返回数据="+res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "用户名已注册", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (res.equals("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

    }
}
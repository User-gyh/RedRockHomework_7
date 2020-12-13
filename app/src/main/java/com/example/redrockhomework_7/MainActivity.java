package com.example.redrockhomework_7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<RvData> list = new ArrayList<>();
    private final RvAdapter rvAdapter = new RvAdapter(list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_demo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapter);
        sendGetNetRequest();
        rvAdapter.notifyDataSetChanged();
    }
    private void sendGetNetRequest() {
        new Thread(
                () ->{
                    try {
                        URL url=new URL("https://www.wanandroid.com/article/list/0/json");
                        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                        connection.setReadTimeout(8000);
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.connect();
                        InputStream in=connection.getInputStream();
                        String responseData= StreamToString(in);
                        jsonDecodeTest(responseData);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
        ).start();
    }
    private String StreamToString(InputStream in) {
        StringBuilder sb=new StringBuilder();
        String oneLine;
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneLine=reader.readLine())!=null) {
                sb.append(oneLine).append('\n');
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    private void jsonDecodeTest(String jsonData) {
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONObject jsonObjectData=jsonObject.getJSONObject("data");
            JSONArray jsonArrayData = jsonObjectData.getJSONArray("datas");
            for (int a=0;a<jsonArrayData.length();a++) {
                JSONObject jsonObjectData1=jsonArrayData.getJSONObject(a);
                String link=jsonObjectData1.getString("link");
                String shareUser=jsonObjectData1.getString("shareUser");
                String title=jsonObjectData1.getString("title");
                list.add(new RvData(title,shareUser,link));
                Log.d("MainActivity", "st: "+title);
            }rvAdapter.notifyDataSetChanged();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
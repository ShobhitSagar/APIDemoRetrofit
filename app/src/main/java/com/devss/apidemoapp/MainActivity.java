package com.devss.apidemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
//    private String []arr = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceholderAPI placeholderAPI = retrofit.create(PlaceholderAPI.class);

        Call<List<PlaceholderPost>> call = placeholderAPI.getPosts();

        call.enqueue(new Callback<List<PlaceholderPost>>() {
            @Override
            public void onResponse(Call<List<PlaceholderPost>> call, Response<List<PlaceholderPost>> response) {
                if (response.isSuccessful()) {
                    List<PlaceholderPost> posts = response.body();
                    List<String> list = new ArrayList<String>();
                    for (int i=0; i<posts.size(); i++) {
                        list.add(i+1+". "+posts.get(i).getTitle().toString());
                        Log.d("Success", posts.get(i).getTitle().toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, list);
                    Log.d("~~~~~~~~~~~~", list.toString());
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PlaceholderPost>> call, Throwable t) {

            }
        });

        Log.d("Yo","Hello!");
    }
}
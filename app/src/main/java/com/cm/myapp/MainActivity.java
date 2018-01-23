package com.cm.myapp;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static final String API = "https://api.androidhive.info/contacts/";
    static final String USER_API = "https://api.github.com/users/%s";
    BroadcastReceiver receiver;
    OkHttpClient client;
    ImageLoader imageLoader;
    ImageView profile;
    TextView id, name, createAt;
    Handler mainHandler;
    Button btnOnline;
    GitHub.User user;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_image:
                Intent intent = new Intent(this, ImageActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        btnOnline = findViewById(R.id.btnOnline);
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.htmlUrl != null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl));
                    startActivity(browserIntent);
                }
            }
        });

        profile = findViewById(R.id.profileImg);
        id = findViewById(R.id.userId);
        name = findViewById(R.id.userName);
        createAt = findViewById(R.id.createAt);
        mainHandler = new Handler(getMainLooper());
        // lvContacts = findViewById(R.id.contactList);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new MyBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter("110");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                id.setText(query);
                Request request = new Request.Builder().url(String.format(USER_API, query)).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Gson gson = new Gson();
                        user = gson.fromJson(str, GitHub.User.class);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                id.setText(user.login);
                                name.setText(user.name);
                                createAt.setText(user.created_at);
                                imageLoader.displayImage(user.avatar_url, profile);
                                btnOnline.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    static class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Receive intent " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        }
    }
}


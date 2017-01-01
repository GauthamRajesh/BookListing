package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String encodedSearchQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        performNetworkRequest();
    }
    public class DownloadFilesTask extends AsyncTask<String, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(String... params) {
            return Network.getBookData(params[0]);
        }
        @Override
        protected void onPostExecute(List<Book> books) {
            BookAdapter b = new BookAdapter(getApplicationContext(), books);
            ListView l = (ListView) findViewById(R.id.real_book_list);
            l.setEmptyView(findViewById(R.id.empty_list_view));
            l.setAdapter(b);
        }
    }
    public void performNetworkRequest() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchBox = (EditText) findViewById(R.id.search);
                String searchQuery = searchBox.getText().toString();
                String dataUrl = "https://www.googleapis.com/books/v1/volumes?q="+searchQuery+"&maxResults=15";
                try {
                    encodedSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");
                    String replaced = dataUrl.replace(searchQuery, encodedSearchQuery);
                    if(isConnected) {
                        new DownloadFilesTask().execute(replaced);
                    }
                    else {
                        Log.e("MainActivity", "No internet connection");
                    }
                }
                catch(UnsupportedEncodingException e) {
                    Log.e("MainActivity", "Problem encoding url");
                }
            }
        });
    }
}

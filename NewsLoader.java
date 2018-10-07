package com.example.android.newsapp;


//Uses AsyncTask to do the network request in the background

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>>{

//Tag for log messages
    private static final String LOG_TAG = NewsLoader.class.getName();

    //Sets up for the url
    private String mUrl;

    //Set up a new NewsLoader using the url
    public  NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

   //This is on a background thread.
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news articles.
        List<News> newsArticles = QueryUtils.fetchNewsData(mUrl);
        return newsArticles;
    }
}

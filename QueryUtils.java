package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//Used to request data from API
public final class QueryUtils {

    //tag for log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    //Query the API site and return a list of newsArticle objects

    public static List<News> fetchNewsData(String requestUrl) {
        //Create url object
        URL url = createUrl(requestUrl);

        //Perform a request to the URL and receive a Json response
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem found when making the HTTP request.", e);
        }

        //Extract the relevant fields from the json response and create a list
        List<News> newsArticles = extractFromAPI(jsonResponse);
        return newsArticles;
    }

    //Returns new URL from given string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //Make a HTTP Request and return a string
    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        //Return early if url is null
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news article results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Convert the input stream into a string with the whole Json response
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Return a list of News objects that is built by parsing the JSOn response
    private static List<News> extractFromAPI(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        //Create an emply ArrayList
        List<News> newsArticles = new ArrayList<>();

        //Try to parse the JSON string and throw an exception if needed.
        try {
            JSONObject root = new JSONObject(newsJSON);

            //Extract the Json Array with the key "results"
            JSONArray newsArray = root.getJSONArray("results");

            //Create a News object for each article in the newsArray
            for (int i = 0; i < newsArray.length(); i++) {
                //Get a single article at position i
                JSONObject currentNewsArticle = newsArray.getJSONObject(i);

                //Extract the values from the JSON array
                String date = currentNewsArticle.getString("webPublicationDate");
                String title = currentNewsArticle.getString("webTitle");
                String section = currentNewsArticle.getString("sectionName");
                String url = currentNewsArticle.getString("webUrl");

                //Create a new News object with the information
                News news = new News(title, date, url, section);
                newsArticles.add(news);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news article JSON results.", e);
        }

        // Return the list of earthquakes
        return newsArticles;
    }

}

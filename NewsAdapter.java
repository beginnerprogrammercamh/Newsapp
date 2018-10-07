package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new adapter
     *
     * @param context      of the app
     * @param newsArticles is the list of news articles, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newsArticles) {
        super(context, 0, newsArticles);
    }

    //Returns a list item view that shows the information from the API that we want
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.new_list_item, parent, false);
        }
        // Find the news story at the given position in the list of news stories
        News currentNews = getItem(position);

        //Find and set the title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        String title = currentNews.getTitle();
        titleView.setText(title);

        //Find and set the date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String date = currentNews.getDate();
        dateView.setText(date);

        //Find and set the section
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
        String section = currentNews.getSection();
        sectionView.setText(section);

        //Return the formatted list view
        return listItemView;

    }
}

package com.example.android.newsapp;

public class News {

    //Important data from the API that we will need in our app
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mSection;

    /*Creating a new News object
     @param title is the title of the article
     @param date is the date of the article
    @param url is the url link
     @param section is the section title that the story falls under */

    public News(String title, String date, String url, String section){
        mTitle = title;
        mDate = date;
        mUrl = url;
        mSection = section;
    }

    //Below are the get functions for the params above
    public String getTitle() {return mTitle;}
    public String getDate() {return mDate;}
    public String getUrl() {return mUrl;}
    public String getSection() {return mSection;}
}

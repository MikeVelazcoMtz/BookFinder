package com.example.android.bookfinder;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel Angel Velazco on 27/05/2017.
 *
 * Class that retrieves the book info
 */
class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String queryString;

    BookLoader(Context context, Bundle args) {
        super(context);

        queryString = args.getString("search");

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }

    @Override
    public List<Book> loadInBackground() {
        if (!queryString.isEmpty()) {
            Uri.Builder builder = new Uri.Builder();

            queryString = builder.scheme("https")
                    .authority("www.googleapis.com")
                    .appendPath("books")
                    .appendPath("v1")
                    .appendPath("volumes")
                    .appendQueryParameter("q", queryString)
                    .appendQueryParameter("maxResults", "10").toString();
            return RestUtils.fetchBooksData(queryString);
        } else {
            return new ArrayList<>();
        }
    }
}

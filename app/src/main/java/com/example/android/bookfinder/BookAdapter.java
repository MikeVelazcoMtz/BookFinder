package com.example.android.bookfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Miguel Angel Velazco on 24/05/2017.
 * <p>
 * ArrayAdapter class that renders the items for the ListView
 */
public class BookAdapter extends ArrayAdapter {
    public BookAdapter(@NonNull Context context, ArrayList<Book> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);

        TextView subTitle = (TextView) convertView.findViewById(R.id.sub_title);

        TextView authors = (TextView) convertView.findViewById(R.id.authors);

        // Get the Book data
        Book book = (Book) getItem(position);

        // Assigning the data into the views

        title.setText(book.getTitle());

        String bookSubTitle = book.getSubTitle();

        if (bookSubTitle.isEmpty()) {
            subTitle.setVisibility(View.GONE);
        } else {
            subTitle.setText(book.getSubTitle());
        }

        String bookAuthors = book.getAuthors();

        if (bookAuthors.isEmpty()) {
            TextView authorsLbl = (TextView) convertView.findViewById(R.id.authors_lbl);
            authorsLbl.setVisibility(View.GONE);
            authors.setVisibility(View.GONE);
        } else {
            authors.setText(book.getAuthors());
        }


        // Book Thumbnail

        if (book.getThumbnail() != null) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.book_img);
            imageView.setImageBitmap(book.getThumbnail());
        }

        return convertView;
    }
}

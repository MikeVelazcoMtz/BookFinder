package com.example.android.bookfinder;


import android.graphics.Bitmap;

/**
 * Model of a book used by {@link BookAdapter}.
 */
public class Book {
    private String title;
    private String sub_title;
    private String authors;
    private Bitmap thumbnail;

    /**
     * Instantiates a new Book.
     *
     * @param title     the title
     * @param sub_title the sub title
     * @param authors   the authors
     */
    public Book(String title, String sub_title, String authors, Bitmap thumbnail) {
        this.title = title;
        this.sub_title = sub_title;
        this.authors = authors;
        this.thumbnail = thumbnail;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets sub title.
     *
     * @return the sub title
     */
    public String getSubTitle() {
        return sub_title;
    }

    /**
     * Gets authors.
     *
     * @return the authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Gets the thumbnail.
     *
     * @return the thumbnail
     */
    public Bitmap getThumbnail() {
        return thumbnail;
    }
}

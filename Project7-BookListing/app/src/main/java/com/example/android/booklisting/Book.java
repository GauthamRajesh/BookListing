package com.example.android.booklisting;

public class Book {
    private String mName;
    private String mAuthor;
    public Book(String name, String author) {
        mName = name;
        mAuthor = author;
    }
    public String getTitle() {
        return mName;
    }
    public String getAuthor() {
        return mAuthor;
    }
}

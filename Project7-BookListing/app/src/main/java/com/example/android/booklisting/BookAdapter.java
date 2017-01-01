package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);

        }
        Book currentBook = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());
        return listItemView;
    }
}

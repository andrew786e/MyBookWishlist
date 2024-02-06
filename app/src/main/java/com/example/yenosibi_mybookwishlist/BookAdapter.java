package com.example.yenosibi_mybookwishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
 *Class Name: BookAdapter
 *
 * Description: This class creates a custom adapter for the Book Class
 *
 * Design Rationale: Implemented class based of standard practice when dealing with custom adapters
 *
 * Outstanding Issues: None
 */
public class BookAdapter extends ArrayAdapter<Book> {

//    Used by Main Activty to update DataList count
    interface BookListChangedListener{
        void onBookListChanged() ;
    }

    private BookListChangedListener bookListChangedListner ;
    public BookAdapter(Context context , ArrayList<Book>books){
        super(context , 0 ,books) ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_content , parent , false) ;
        }else{
            view = convertView ;
        }

        Book book = getItem(position) ;

        TextView bookTitle = view.findViewById(R.id.bookTitle) ;
        TextView authorName = view.findViewById(R.id.authorName) ;
        TextView genre = view.findViewById(R.id.genre) ;
        TextView status = view.findViewById(R.id.status) ;
        TextView publicationYear = view.findViewById(R.id.publicationYear) ;

//        Still have to do status and date
        bookTitle.setText(book.getBookTitle());
        authorName.setText(book.getAuthorName()) ;
        genre.setText(book.getGenre());
        status.setText(book.getStatus());
        publicationYear.setText(String.valueOf(book.getPublicationYear()));

        return view ;
    }

//    Sets the listener for Book Listner for the Main Activity
    public void setBookListChangedListener(BookListChangedListener listener){
        this.bookListChangedListner = listener ;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
//      Calls the onBookListChanged function implemented in the main activity
        if(bookListChangedListner != null){
            bookListChangedListner.onBookListChanged();
        }
    }
}

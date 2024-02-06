package com.example.yenosibi_mybookwishlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
 * Class: MainActivity
 *
 * Description: This class extends AppCompactAcivity and Implements AddBookFragment.AddBookDialogListener
 * , BookAdapter.BookListChangedListener , EditDeleteFragment.EditDeleteListener. The class creates views for
 * the EditDeleteFragment and AddBookFragment. It also monitors the number of books in the Book Adapter and the
 * the number of books read. This class notifies the Book Adapter whenever a change is made to Adapter and either edits,deletes or adds a book
 *
 * Design Rationale: This class creates the Fragment Views because it is the main entry point of the app
 *
 * Outstanding Issues: It displays multiple fragments even if the first fragment created has not been closed.
 */

public class MainActivity extends AppCompatActivity implements AddBookFragment.AddBookDialogListener , BookAdapter.BookListChangedListener , EditDeleteFragment.EditDeleteListener{

    private ArrayList<Book> bookArrayInfo ;
    private ListView bookList ;

    private int readCounter ;
    private int bookPosition ;

    private Book selectedBook ;
    private BookAdapter bookAdapter ;

    @Override
    public void delete(int position){
        bookArrayInfo.remove(position) ;
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void createEditView(){
        EditDeleteFragment.newInstance(bookPosition, selectedBook , true).show(getSupportFragmentManager() , "Edit Book"); ;
    }

    @Override
    public void edit(int position , Book editedBook){
        bookArrayInfo.set(bookPosition , editedBook) ;
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBookListChanged(){
        setNumberOfBooks();
        setNumberOfReadBooks();
    }

    @Override
    public void addBook(Book newBook){
        bookAdapter.add(newBook);
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookArrayInfo = new ArrayList<Book>() ;


        bookList = findViewById(R.id.bookList) ;
        bookAdapter = new BookAdapter(this , bookArrayInfo) ;
        bookList.setAdapter(bookAdapter);

//     Sets the initialNumber of Books
        setNumberOfBooks();
        setNumberOfReadBooks();

// Sets up a listener that updates the number of books in list
        bookAdapter.setBookListChangedListener(this);

        Button addBookButton = findViewById(R.id.addBook) ;
        addBookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AddBookFragment().show(getSupportFragmentManager() , "Add new book") ;
            }
        });

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBook = (Book) parent.getItemAtPosition(position) ;
                bookPosition = position ;
                EditDeleteFragment.newInstance(position, selectedBook).show(getSupportFragmentManager() , "Edit or Remove a Book"); ;
            }
        });
    }

    public void setNumberOfBooks(){
        String bookInfo = getString(R.string.numberOfBooks) ;
        Integer numOfBooks = bookArrayInfo.size() ;
        String convertedBookNum = String.valueOf(numOfBooks) ;
        String formatBookNumTest = String.format(bookInfo , convertedBookNum) ;
        TextView bookNumView = findViewById(R.id.bookNumInfo) ;
        bookNumView.setText(formatBookNumTest);
    }

    public void setNumberOfReadBooks(){
        readCounter = 0 ;

        for(int i = 0 ; i < bookArrayInfo.size() ; i++){
            if(bookArrayInfo.get(i).getStatus().equals("Read")){
                readCounter += 1 ;
            }
        }

        String readStatus = getString(R.string.books_read) ;
        String convertReadNum = String.valueOf(readCounter) ;
        String formatReadNum = String.format(readStatus , convertReadNum) ;
        TextView numOfReadView = findViewById(R.id.numberOfReadBooks) ;
        numOfReadView.setText(formatReadNum);
    }
}
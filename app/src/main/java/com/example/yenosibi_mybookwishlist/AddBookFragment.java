package com.example.yenosibi_mybookwishlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/*
 * Class Name: AddBookFragment
 *
 * Description: This class creates a layout that enables users to add a book. It sends information of the added Book to the Main activity
 *
 * Design Rationale: The class leaves the responsibility of adding a book to the Main Activity as it creates a loosely coupled system
 *
 * Outstanding Issues: The class does not enforce the user to fill in all of the fields
 */

public class AddBookFragment extends DialogFragment {
//  Used by the Main activity to update Book Adapter
    interface  AddBookDialogListener{
        void addBook(Book book);
    }
    private AddBookDialogListener bookListener ;

    public AddBookFragment(){

    }

//    Attaches the Add BookFragment to the Main Activity and sets up the listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddBookDialogListener){
            bookListener = (AddBookDialogListener) context ;
        }else{
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

//    Error checking needs to be done
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View addBookView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_book , null) ;

        EditText editBookName = addBookView.findViewById(R.id.editBookName) ;
        EditText editAuthorName = addBookView.findViewById(R.id.editAuthorName) ;
        EditText editGenre = addBookView.findViewById(R.id.editGenre) ;
        EditText editDate = addBookView.findViewById(R.id.editYear) ;
        RadioGroup readOrUnread = addBookView.findViewById(R.id.editStatus) ;

        //      Set Listners that handle textViolations
        bookCharacterCount(editBookName);
        authorCharacterCount(editAuthorName);
//        publicationIntegerCount(editDate);

        AlertDialog.Builder addBookBuilder= new AlertDialog.Builder(getContext()) ;

        Dialog addBookDialog = addBookBuilder
                .setView(addBookView)
                .setNegativeButton("Cancel" , null)
                .setPositiveButton("Add Book" , (dialog , which) -> {
                    String bookName = editBookName.getText().toString() ;
                    String authorName = editAuthorName.getText().toString() ;
                    String genre = editGenre.getText().toString() ;
                    String date = editDate.getText().toString() ;

                    int selectedRadioButtonId = readOrUnread.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = addBookView.findViewById(selectedRadioButtonId);
                    String status = selectedRadioButton.getText().toString();

                    Book newBook = new Book(bookName , authorName , genre , date , status) ;
//                    Book Listner in Main Activity Called
                    bookListener.addBook(newBook);
                }).create() ;


        AlertDialog addAlertDialog = (AlertDialog)addBookDialog ;
        addAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = addAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setEnabled(false);
                editDate.setError("Date has to have 4 numbers");
            }
        });

        publicationIntegerCount(editDate , addAlertDialog);

        return addBookDialog ;
    }

    private void bookCharacterCount(EditText editBookName){
        editBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 50){
                    editBookName.setError("Book limit of 50 words reached");
                }
            }
        });
    }

    private void authorCharacterCount(EditText editAuthorName){
        editAuthorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 30){
                    editAuthorName.setError("Author Name limit has been reached");
                }
            }
        });
    }

    private void publicationIntegerCount(EditText editDate , AlertDialog addBookDialog){
        editDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() != 4){
                    editDate.setError("Date has to have 4 numbers");
                }
                addBookDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(editable.length() == 4);
            }
        });
    }
}

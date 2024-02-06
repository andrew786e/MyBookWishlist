package com.example.yenosibi_mybookwishlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * Class Name: Book
 *
 * Description: This class keeps track of specific information of a book.
 *
 * Design Rationale: Design based of objected oriented principles.
 *
 * Outstanding Issues: None
 */
public class Book implements Serializable {

    private String bookTitle ;
    private String authorName ;
    private String genre ;

    private String publicationYear ;

    private String status ;
    public Book(String  bookTitle , String authorName , String genre, String publicationYear, String status){
        this.bookTitle = bookTitle ;
        this.authorName = authorName ;
        this.genre = genre ;
        this.publicationYear = publicationYear ;
        this.status = status ;
    }

//    Test Constructor
    public void setBookTitle(String bookTitle){

        this.bookTitle = bookTitle ;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }
}

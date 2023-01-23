package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.models.Genre;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository authorRepository;

    public void createBook(Book book){
        int author_id=book.getAuthor().getId();
        Author author=authorRepository.findById(author_id).get();
        List<Book> booksList=author.getBooksWritten();
        booksList.add(book);
        author.setBooksWritten(booksList);
        book.setAuthor(author);
        authorRepository.save(author);

    }

    public List<Book> getBooks(String genre, boolean available, String author){

        Author getAuthor=authorRepository.findByName(author);
        List<Book> booksByAuthor=getAuthor.getBooksWritten();
        List<Book> availableBooks=new ArrayList<>();
        List<Book> unAvailableBooks=new ArrayList<>();

            for(Book book:booksByAuthor){
                String bookType= String.valueOf(book.getGenre());
                if(genre.equals(bookType)) {
                    if(book.isAvailable()) {
                        availableBooks.add(book);
                    }
                    else{
                        unAvailableBooks.add(book);
                    }
                }
            }
            if(available==true) return availableBooks;
            return unAvailableBooks;             //find the elements of the list by yourself

    }
}

package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        Card card=cardRepository5.findById(cardId).get();
        Book book=bookRepository5.findById(bookId).get();
        Transaction transaction=new Transaction();
        transaction.setCard(card);
        transaction.setBook(book);



        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        if(book==null || !book.isAvailable()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(transaction);
            throw new Exception("Book is either unavailable or not present");
        }

        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");

        if(card==null||card.getCardStatus().equals("DEACTIVATED")){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(transaction);
            throw new Exception("Card is invalid");
        }

        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");

       if(card.getBooks().size()>=max_allowed_books) {
           transaction.setTransactionStatus(TransactionStatus.FAILED);
           transactionRepository5.save(transaction);
           throw new Exception("Book limit has reached for this card");
       }
        //setting book unavaible

       // List<Transaction> transactionList=book.getTransactions();
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        book.setAvailable(false);
       book.setCard(card);
       card.getBooks().add(book);


       cardRepository5.save(card);
       bookRepository5.updateBook(book);

       transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
       transactionRepository5.save(transaction);


        //Note that the error message should match exactly in all cases
        //return transactionId instead



        //This saving of transcation can't be avoided bcz card is not bidirectionally connected to txn
        //and for the book we are not calling the inbuilt .save function



        return transaction.getTransactionId();
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);
        Book book=bookRepository5.findById(bookId).get();
        Card card=cardRepository5.findById(cardId).get();


        transaction.setBook(book);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transaction.setFineAmount(100);
        transactions.add(transaction);
        book.setAvailable(true);
        book.setTransactions(transactions);
        bookRepository5.save(book);
        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = null;
        return returnBookTransaction; //return the transaction after updating all details
    }
}

package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        transaction.setIssueOperation(true);



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

        if(card==null||card.getCardStatus().equals(CardStatus.DEACTIVATED)){
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
        /*  List<Transaction> transactionList=transactionRepository5.find(cardId,bookId,TransactionStatus.SUCCESSFUL,true);
        Transaction transaction=transactionList.get(transactionList.size()-1);
        Date issueDate=transaction.getTransactionDate();
        long issueTime=System.currentTimeMillis()-issueDate.getTime();
        long no_of_days_passed= TimeUnit.DAYS.convert(issueTime, TimeUnit.MILLISECONDS);
        int fine=0;
        if(no_of_days_passed>getMax_allowed_days){
            fine= (int) ((no_of_days_passed-getMax_allowed_days)*fine_per_day);
        }

        Book book=bookRepository5.findById(bookId).get();
        book.setCard(null);
        book.setAvailable(true);
        bookRepository5.updateBook(book);
        Transaction transaction1=new Transaction();
        transaction1.setBook(transaction.getBook());
        transaction1.setCard(transaction.getCard());
        transaction1.setFineAmount(fine);
        transaction1.setIssueOperation(false);
        transaction1.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository5.save(transaction1);
        return transaction;

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well


       //return the transaction after updating all details/////
        List<Transaction> transactions = transactionRepository5.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);

        Transaction transaction = transactions.get(transactions.size() - 1);

        Date issueDate = transaction.getTransactionDate();

        long timeIssuetime = Math.abs(System.currentTimeMillis() - issueDate.getTime());

        long no_of_days_passed = TimeUnit.DAYS.convert(timeIssuetime, TimeUnit.MILLISECONDS);

        int fine = 0;
        if(no_of_days_passed > getMax_allowed_days){
            fine = (int)((no_of_days_passed - getMax_allowed_days) * fine_per_day);
        }

        Book book = transaction.getBook();
        Card card=cardRepository5.findById(cardId).get();
        List<Book> issuedBooks=card.getBooks();
        issuedBooks.remove(book);
        cardRepository5.save(card);


        book.setAvailable(true);
        book.setCard(null);

        bookRepository5.updateBook(book);

        Transaction tr = new Transaction();
        tr.setBook(transaction.getBook());
        tr.setCard(transaction.getCard());
        tr.setIssueOperation(false);
        tr.setFineAmount(fine);
        tr.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository5.save(tr);

        return tr;


    }
}*/

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${books.max_allowed}")
    public
    int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public
    int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public
    int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        Book book = bookRepository.findById(bookId).get();
        Card card = cardRepository.findById(cardId).get();

        Transaction transaction = new Transaction();

        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setIssueOperation(true);

        //Book should be available
        if(book == null || !book.isAvailable()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is either unavailable or not present");
        }

        //Card is unavaible or its deactivated
        if(card == null || card.getCardStatus().equals(CardStatus.DEACTIVATED)){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card is invalid");
        }

        if(card.getBooks().size() >= max_allowed_books){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book limit has reached for this card");
        }

        book.setCard(card);
        book.setAvailable(false);
        List<Book> bookList = card.getBooks();
        bookList.add(book);


        cardRepository.save(card);

        bookRepository.updateBook(book);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository.save(transaction);
        //This saving of transcation can't be avoided bcz card is not bidirectionally connected to txn
        //and for the book we are not calling the inbuilt .save function



        return transaction.getTransactionId();
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{



        List<Transaction> transactions = transactionRepository.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);
        if(transactions==null) throw new Exception("No transactions available");

        Transaction transaction = transactions.get(transactions.size() - 1);


        Date issueDate = transaction.getTransactionDate();

        long timeIssuetime = Math.abs(System.currentTimeMillis() - issueDate.getTime());

        long no_of_days_passed = TimeUnit.DAYS.convert(timeIssuetime, TimeUnit.MILLISECONDS);

        int fine = 0;
        if(no_of_days_passed > getMax_allowed_days){
            fine = (int)((no_of_days_passed - getMax_allowed_days) * fine_per_day);
        }
        Book book = transaction.getBook();

        Card card=cardRepository.findById(cardId).get();
        List<Book> issuedBooks=card.getBooks();
        issuedBooks.remove(book);
        card.setBooks(issuedBooks);
        cardRepository.save(card);



        book.setAvailable(true);
        book.setCard(null);

        bookRepository.updateBook(book);

        Transaction tr = new Transaction();
        tr.setBook(transaction.getBook());
        tr.setCard(transaction.getCard());
        tr.setIssueOperation(false);
        tr.setFineAmount(fine);
        tr.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository.save(tr);

        return tr;
    }
}


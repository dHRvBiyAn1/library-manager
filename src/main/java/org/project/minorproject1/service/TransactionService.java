package org.project.minorproject1.service;

import org.project.minorproject1.models.*;
import org.project.minorproject1.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @Value("${students.books.max-allowed}")
    private Integer maxAllowedBooks;

    @Value("${books.return.duration}")
    private Integer returnDuration;

    @Value("${fine.per-day}")
    private Integer finePerDay;

    public String initiateTransaction(Integer studentId, Integer bookId, TransactionType transactionType) throws Exception {
        switch (transactionType) {
            case ISSUE:
                return initiateIssuance(studentId, bookId);
            case RETURN:
                return initiateReturn(studentId, bookId);
            default:
                throw new Exception("Invalid Transaction Type");
        }
    }

    private String initiateIssuance(Integer studentId, Integer bookId) throws Exception {
        Student student = this.studentService.getStudentDetailsResponse(studentId, true).getStudent();
        Book book = this.bookService.getBookById(bookId);

        if (student == null) {
            throw new Exception("Student is not present");
        }
        if (book == null || book.getStudent() != null) {
            throw new Exception("Book is not available for issuance");
        }
        List<Book> issuedBooks = student.getBookList();
        if (issuedBooks != null && issuedBooks.size() >= this.maxAllowedBooks) {
            throw new Exception("Student has issued maximum number of books allowed");
        }

        Transaction transaction = Transaction.builder()
                .externalTransactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.PENDING)
                .student(student)
                .book(book)
                .build();

        try {
            book.setStudent(student);
            book = this.bookService.createOrUpdate(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            this.transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            this.transactionRepository.save(transaction);

            if (book.getStudent() != null) {
                book.setStudent(null);
                this.bookService.createOrUpdate(book);
            }
        }
        return transaction.getExternalTransactionId();
    }

    public String initiateReturn(Integer studentId, Integer bookId) throws Exception {
        Student student = this.studentService.getStudentDetailsResponse(studentId, false).getStudent();
        Book book = this.bookService.getBookById(bookId);
        if (student == null) {
            throw new Exception("Student is not present");
        }

        if (book == null || book.getStudent() == null || book.getStudent().getId() != studentId)  {
            throw new Exception("Book is not available for return");
        }

        Transaction transaction = Transaction.builder()
                .externalTransactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .student(student)
                .book(book)
                .build();
        transaction = this.transactionRepository.save(transaction);

        try {
            Integer fine = this.calculateFine(book, student);
            book.setStudent(null);
            book = this.bookService.createOrUpdate(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            this.transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            this.transactionRepository.save(transaction);

            if (book.getStudent() == null) {
                book.setStudent(student);
                this.bookService.createOrUpdate(book);
            }
        }
        return transaction.getExternalTransactionId();
    }

    public Integer calculateFine(Book book, Student student) {
        Transaction issuedTxn =  this.transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(student, book, TransactionType.ISSUE, TransactionStatus.SUCCESS);
        long issuedtimeInMillis = issuedTxn.getUpdatedOn().getTime();
        long timePassedInMillis = System.currentTimeMillis() - issuedtimeInMillis;
        Long daysPassed = TimeUnit.DAYS.convert(timePassedInMillis, TimeUnit.MILLISECONDS);
        if (daysPassed > returnDuration ) {
            return Math.toIntExact((daysPassed - returnDuration) * finePerDay);
        }
        return 0;
    }
}

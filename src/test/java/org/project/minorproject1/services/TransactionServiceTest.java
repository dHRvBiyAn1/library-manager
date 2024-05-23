package org.project.minorproject1.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.minorproject1.dto.GetStudentDetailsResponse;
import org.project.minorproject1.models.*;
import org.project.minorproject1.repository.TransactionRepository;
import org.project.minorproject1.service.BookService;
import org.project.minorproject1.service.StudentService;
import org.project.minorproject1.service.TransactionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    StudentService studentService;

    @Mock
    BookService bookService;

    @Test
    public void testCalculateFine_PositiveFine() {

        Book book = Book.builder().id(5).build();

        Student student = Student.builder()
                .id(1)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1)
                .student(student)
                .book(book)
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.SUCCESS)
                .updatedOn(new Date(1713543590000L))
                .build();
        Mockito.when(transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(
                Mockito.eq(student),
                Mockito.eq(book),
                Mockito.eq(TransactionType.ISSUE),
                Mockito.eq(TransactionStatus.SUCCESS))).thenReturn(transaction);
        int fine = transactionService.calculateFine(book, student);
        Assert.assertEquals(15, fine);
    }

    @Test
    public void testCalculateFine_NoFine() {

        Book book = Book.builder()
                .id(5)
                .build();
        Student student = Student.builder()
                .id(1)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1)
                .student(student)
                .book(book)
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.SUCCESS)
                .updatedOn(new Date(1714925990000l))
                .build();
        Mockito.when(transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(Mockito.eq(student), Mockito.eq(book), Mockito.eq(TransactionType.ISSUE), Mockito.eq(TransactionStatus.SUCCESS))).thenReturn(transaction);
        int fine = transactionService.calculateFine(book, student);
        Assert.assertEquals(0, fine);
    }

    @Test
    public void testInitiateReturn() throws Exception {
        Student student = Student.builder()
                .id(1)
                .build();
        Book book = Book.builder()
                .id(5)
                .student(student)
                .build();
        GetStudentDetailsResponse getStudentDetailsResponse = GetStudentDetailsResponse.builder()
                .student(student)
                .bookList(new ArrayList<>())
                .build();
        String externalId = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .id(1)
                .externalTransactionId(externalId)
                .student(student).book(book)
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.SUCCESS)
                .updatedOn(new Date(1714925990000l))
                .build();
        Mockito.when(studentService.getStudentDetailsResponse(1, true)).thenReturn(getStudentDetailsResponse);
        Mockito.when(bookService.getBookById(1)).thenReturn(book);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        Mockito.when(transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(
                Mockito.eq(student),
                Mockito.eq(book),
                Mockito.eq(TransactionType.ISSUE),
                Mockito.eq(TransactionStatus.SUCCESS))).thenReturn(transaction);
        Mockito.when(bookService.createOrUpdate(Mockito.any())).thenReturn(book);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        String txnId = transactionService.initiateReturn(1, 1);
        Assert.assertEquals(externalId, txnId);
    }

    @Test(expected = Exception.class)
    public void testInitiateReturn_StudentNotAssigned() throws Exception {

        Student student = Student.builder().id(1).build();
        Book book = Book.builder().id(5)
                .build();
        GetStudentDetailsResponse getStudentDetailsResponse = GetStudentDetailsResponse.builder().student(student).bookList(new ArrayList<>()).build();
        Mockito.when(studentService.getStudentDetailsResponse(1, true)).thenReturn(getStudentDetailsResponse);
        Mockito.when(bookService.getBookById(1)).thenReturn(book);
        transactionService.initiateReturn(1, 1);

    }
}

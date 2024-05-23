package org.project.minorproject1.service;

import org.project.minorproject1.dto.CreateBookRequest;
import org.project.minorproject1.models.Author;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksWithSorting(String field) {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public List<Book> getBooksByStudentId(Integer studentId) {
        return this.bookRepository.findByStudentId(studentId);
    }

    public Book getBookById(Integer bookId) {
        return this.bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> create(List<CreateBookRequest> request){
        List<Book> books = new ArrayList<>();
        for (CreateBookRequest r : request) {
            Book book = r.mapToBookAndAuthor();
            Author author = book.getAuthor();
            author = this.authorService.getOrCreate(author);
            book.setAuthor(author);
            bookRepository.save(book);
            books.add(book);
        }
        return books;
    }

    public Book createOrUpdate(Book book){
        return this.bookRepository.save(book);
    }

}

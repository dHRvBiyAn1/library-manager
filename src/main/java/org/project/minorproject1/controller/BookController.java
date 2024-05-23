package org.project.minorproject1.controller;

import jakarta.validation.Valid;
import org.project.minorproject1.dto.CreateBookRequest;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.repository.BookRepository;
import org.project.minorproject1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public List<Book> createBook(@Valid @RequestBody List<CreateBookRequest> request) {
        return bookService.create(request);
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") Integer bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping("")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/sort/{field}")
    public List<Book> getAllBooksWithSorting(@PathVariable("field") String field){
        return bookService.getAllBooksWithSorting(field);
    }
}

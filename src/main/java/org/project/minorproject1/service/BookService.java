package org.project.minorproject1.service;

import org.project.minorproject1.models.Book;
import org.project.minorproject1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooksByStudentId(Integer studentId) {
        return this.bookRepository.findByStudentId(studentId);
    }

}

package org.project.minorproject1.repository;

import org.project.minorproject1.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByStudentId(int studentId);
}

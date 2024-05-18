package org.project.minorproject1.service;

import org.project.minorproject1.dto.GetStudentDetailsResponse;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.models.Student;
import org.project.minorproject1.repository.BookRepository;
import org.project.minorproject1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;

    public GetStudentDetailsResponse getStudentDetailsResponse(Integer studentId, boolean requireBookList) {
        Student student = this.studentRepository.findById(studentId).orElse(null);
        List<Book> bookList = null;
        if (requireBookList) {
            bookList = this.bookRepository.findByStudentId(studentId);
        }
        this.studentRepository.save(Student.builder().build());
        return GetStudentDetailsResponse.builder()
                .student(student)
                .bookList(bookList)
                .build();
    }

}

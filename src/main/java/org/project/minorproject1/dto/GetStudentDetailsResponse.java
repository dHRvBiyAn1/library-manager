package org.project.minorproject1.dto;

import lombok.*;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.models.Student;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStudentDetailsResponse {

    private Student student;

    private List<Book> bookList;
}

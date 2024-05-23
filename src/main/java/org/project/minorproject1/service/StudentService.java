package org.project.minorproject1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.minorproject1.dto.CreateStudentRequest;
import org.project.minorproject1.dto.GetStudentDetailsResponse;
import org.project.minorproject1.dto.UpdateStudentRequest;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.models.Student;
import org.project.minorproject1.models.StudentStatus;
import org.project.minorproject1.repository.BookRepository;
import org.project.minorproject1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public GetStudentDetailsResponse getStudentDetailsResponse(Integer studentId, boolean requireBookList) {
        Student student = this.studentRepository.findById(studentId).orElse(null);
        List<Book> bookList = null;
//        if (requireBookList) {
//            bookList = this.bookRepository.findByStudentId(studentId);
//        }
        return GetStudentDetailsResponse.builder()
                .student(student)
                .bookList(student.getBookList())
                .build();
    }

    public List<Student> getAllStudentsWithSorting(String field) {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public List<Student> create(List<CreateStudentRequest> request) {
        List<Student> students = new ArrayList<>();
        for (CreateStudentRequest r : request) {
            Student student = r.mapToStudent();
            this.studentRepository.save(student);
            students.add(student);
        }
        return students;
    }

    public GetStudentDetailsResponse update(Integer studentId, UpdateStudentRequest request){
        Student student = request.mapToStudent();
        student.setId(1);
        GetStudentDetailsResponse studentDetailsResponse = this.getStudentDetailsResponse(studentId, false );
        Student savedStudent = studentDetailsResponse.getStudent();
        Student target = this.merge(student, savedStudent);
        studentRepository.save(target);
        studentDetailsResponse.setStudent(target);
        return studentDetailsResponse;
    }

    private Student merge(Student incoming, Student saved) {
        JSONObject incomingStudent = mapper.convertValue(incoming, JSONObject.class);
        JSONObject savedStudent = mapper.convertValue(saved, JSONObject.class);

        Iterator it = incomingStudent.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (incomingStudent.get(key) != null){
                savedStudent.put(key, incomingStudent.get(key));
            }
        }
        return mapper.convertValue(savedStudent, Student.class);
    }

    public GetStudentDetailsResponse deactivate(Integer studentId) {
        this.studentRepository.deactivate(studentId, StudentStatus.INACTIVE);
        return this.getStudentDetailsResponse(studentId, false);
    }


}

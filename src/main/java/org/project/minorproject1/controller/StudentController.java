package org.project.minorproject1.controller;

import jakarta.validation.Valid;
import org.project.minorproject1.dto.CreateStudentRequest;
import org.project.minorproject1.dto.GetStudentDetailsResponse;
import org.project.minorproject1.dto.UpdateStudentRequest;
import org.project.minorproject1.models.Student;
import org.project.minorproject1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public List<Student> createStudent(@Valid @RequestBody List<CreateStudentRequest> request){
        return studentService.create(request);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/sort/{field}")
    public List<Student> getAllStudentsWithSorting(@PathVariable String field) {
        return studentService.getAllStudentsWithSorting(field);
    }

    @GetMapping("/{studentId}")
    public GetStudentDetailsResponse getStudentDetailsResponse(
            @PathVariable("studentId") int studentId,
            @RequestParam(value = "require-book-list", required = false, defaultValue = "false") boolean requireBookList) {
        return this.studentService.getStudentDetailsResponse(studentId, requireBookList);
    }

    @PatchMapping("/{studentId}")
    public GetStudentDetailsResponse updateStudentDetails(
            @PathVariable("studentId") Integer studentId,
            @Valid @RequestBody UpdateStudentRequest request){

        return this.studentService.update(studentId, request);
    }

    @DeleteMapping("/{studentId}")
    public GetStudentDetailsResponse deactivateStudent(
            @PathVariable("studentId") Integer studentId){
        return this.studentService.deactivate(studentId);
    }
}

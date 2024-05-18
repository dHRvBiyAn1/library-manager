package org.project.minorproject1.controller;

import org.project.minorproject1.dto.GetStudentDetailsResponse;
import org.project.minorproject1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{studentId}")
    public GetStudentDetailsResponse getStudentDetailsResponse(
            @PathVariable("studentId") int studentId,
            @RequestParam(value = "require-book-list", required = false, defaultValue = "false") boolean requireBookList) {
        return this.studentService.getStudentDetailsResponse(studentId, requireBookList);
    }

}

package org.project.minorproject1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.project.minorproject1.models.Student;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentRequest {
    private String name;
    private String email;
    private String mobile;

    public Student mapToStudent(){
        return Student.builder()
                .name(this.name)
                .mobile(this.mobile)
                .email(this.email)
                .build();
    }
}

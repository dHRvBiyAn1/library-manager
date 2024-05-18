package org.project.minorproject1.repository;

import org.project.minorproject1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

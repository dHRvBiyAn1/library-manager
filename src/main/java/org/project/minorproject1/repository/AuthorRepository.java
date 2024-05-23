package org.project.minorproject1.repository;

import org.project.minorproject1.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);
}

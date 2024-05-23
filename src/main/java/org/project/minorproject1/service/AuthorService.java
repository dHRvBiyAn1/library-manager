package org.project.minorproject1.service;

import org.project.minorproject1.models.Author;
import org.project.minorproject1.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author getOrCreate(Author author){
        Author savedAuthor = this.authorRepository.findByEmail(author.getEmail());

        if (savedAuthor == null){
            author = this.authorRepository.save(author);
            return author;
        }
        return savedAuthor;
    }
}

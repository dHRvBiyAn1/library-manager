package org.project.minorproject1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.project.minorproject1.models.Author;
import org.project.minorproject1.models.Book;
import org.project.minorproject1.models.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {
    private String bookName;
    private Genre genre;
    private String authorName;
    @NotBlank
    private String authorEmail;

    public Book mapToBookAndAuthor(){
        return Book.builder()
                .name(this.bookName)
                .genre(this.genre)
                .author(
                        Author.builder()
                                .email(this.authorEmail)
                                .name(this.authorName)
                                .build()
                )
                .build();
    }
}

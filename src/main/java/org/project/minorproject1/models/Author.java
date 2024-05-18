package org.project.minorproject1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique=true)
    private String email;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "author")
    private List<Book> bookList;

}

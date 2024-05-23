package org.project.minorproject1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String name;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList", "createdOn"})
    private Author author;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Student student;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("book")
    private List<Transaction> transactionList;
}

package org.project.minorproject1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String externalTransactionId;

    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne
    @JoinColumn
    private Student student;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Integer fine;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}

package hu.elte.financetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="transactions")
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @UuidGenerator
    String id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_name")
    Category category;

    LocalDateTime date;

    String description;

    Double amount;
}

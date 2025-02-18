package hu.elte.financetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name="categories")
@RequiredArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    String name;
}

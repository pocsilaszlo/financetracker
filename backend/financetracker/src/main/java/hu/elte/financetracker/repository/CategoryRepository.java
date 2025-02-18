package hu.elte.financetracker.repository;

import hu.elte.financetracker.entity.Category;
import hu.elte.financetracker.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
    public List<Category> findAll();
}

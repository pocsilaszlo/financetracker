package hu.elte.financetracker.repository;

import hu.elte.financetracker.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    public List<Transaction> findAll();
}

package hu.elte.financetracker.service;

import hu.elte.financetracker.entity.Category;
import hu.elte.financetracker.entity.Transaction;
import hu.elte.financetracker.repository.CategoryRepository;
import hu.elte.financetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService {

    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public FinanceService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void add(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }
}

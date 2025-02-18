package hu.elte.financetracker.controller;

import hu.elte.financetracker.entity.Category;
import hu.elte.financetracker.entity.Transaction;
import hu.elte.financetracker.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private FinanceService financeService;

    public TransactionController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/all")
    private ResponseEntity<?> getTransactions(){
        return new ResponseEntity<>(financeService.getAllTransactions(), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<?> addTransaction(@RequestBody Transaction transaction){
        financeService.add(transaction);
        return ResponseEntity.ok(Map.of("message", "Transaction added"));
    }

    @GetMapping("/categories/all")
    private ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(financeService.getAllCategories(), HttpStatus.OK);
        //return new ResponseEntity<>(Map.of("key", "value"), HttpStatus.OK);
    }

    @PostMapping("/categories/add")
    private ResponseEntity<?> addCategory(@RequestBody Category category){
        financeService.add(category);
        return ResponseEntity.ok(Map.of("message", "Category added"));
    }
}

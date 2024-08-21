package com.bank.bank_api.resource;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bank.bank_api.builder.TransactionBuilder;
import com.bank.bank_api.builder.TransactionValidationErrorBuilder;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;
import com.bank.bank_api.validation.TransactionValidationError;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepo;

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getToDos() {
        return ResponseEntity.ok(transactionRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getToDoById(@PathVariable String id) {
        Optional<Transaction> result = transactionRepo.findById(id);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.notFound().build();

        // return ResponseEntity.ok(transactionRepo.findById(id));
    }

    // @GetMapping("/")
    // public ResponseEntity<Transaction> getTransactionByNotes(@PathVariable String id) {
    //     Optional<Transaction> result = transactionRepo.findById(id);
    //     if (result.isPresent())
    //         return ResponseEntity.ok(result.get());
    //     return ResponseEntity.notFound().build();

    //     // return ResponseEntity.ok(transactionRepo.findById(id));
    // }

    @PostMapping("/{id}")
    // @PatchMapping("/{id}")
    public ResponseEntity<Transaction> setCompleted(@PathVariable String id) {
        Optional<Transaction> result = transactionRepo.findById(id);
        if (result.isPresent()) {
            result.get().setCompleted(true);
            transactionRepo.save(result.get());

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(result.get().getId()).toUri();
            return ResponseEntity.ok().header("Location", location.toString()).build();
        }
        
        return ResponseEntity.notFound().build();

    }

    // @RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.PUT })
    @PostMapping
    public ResponseEntity<?> createToDo(@RequestBody Transaction transaction,
            Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(TransactionValidationErrorBuilder.fromBindingErrors(errors));
        }
        Transaction result = transactionRepo.save(transaction);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteToDo(@PathVariable String id) {
        transactionRepo.delete(TransactionBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Transaction> deleteToDo(@RequestBody Transaction toDo) {
        transactionRepo.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public TransactionValidationError handleException(Exception exception) {
        return new TransactionValidationError(exception.getMessage());
    }
}

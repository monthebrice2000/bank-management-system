package com.bank.bank_api.builder;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.bank.bank_api.validation.TransactionValidationError;

public class TransactionValidationErrorBuilder {
    
    public static TransactionValidationError fromBindingErrors(Errors errors) {
        TransactionValidationError error = new TransactionValidationError(
                "Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}

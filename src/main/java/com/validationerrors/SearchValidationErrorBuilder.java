package com.validationerrors;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class SearchValidationErrorBuilder {
    public static SearchValidationError fromBindingErrors(Errors errors){
        SearchValidationError error = new SearchValidationError("Validation failed."
         + errors.getErrorCount() + " error(s).");
        for (ObjectError objectError : errors.getAllErrors()){
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}

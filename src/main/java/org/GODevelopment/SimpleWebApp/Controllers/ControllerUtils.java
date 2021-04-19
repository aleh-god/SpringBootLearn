package org.GODevelopment.SimpleWebApp.Controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        // interface Collector<T,A,R>
        // A mutable reduction operation that accumulates input elements into a mutable result container, optionally transforming the accumulated result into a final representation after all input elements have been processed.
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap( // Преобразуем поток в Мапу
                fieldError -> fieldError.getField() + "Error",  //  FieldError -> k "Error" пригодится использовать в templates
                FieldError::getDefaultMessage                   //  FieldError -> v
        );
        return bindingResult.getFieldErrors() // Returns a List of FieldError instances
                .stream()
                .collect(collector); // Преобразуем поток в Мапу, см выше
    }
}

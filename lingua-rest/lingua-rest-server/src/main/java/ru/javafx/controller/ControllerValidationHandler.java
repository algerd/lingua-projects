package ru.javafx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.VndErrors;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.javafx.message.MessageDTO;
import ru.javafx.message.MessageType;

@ControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private MessageSource msgSource;
    
    // Messages as MessageDTO(custom message class) list
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<MessageDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        List<MessageDTO> messages = new ArrayList<>();
        errors.forEach(error -> {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            messages.add(new MessageDTO(MessageType.ERROR, msg));
        });
        return !messages.isEmpty() ? messages : null;      
    }
   
    /*
    // Messages as HATEOAS list
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public VndErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        List<VndError> vndErrors = new ArrayList<>();
        errors.forEach(error -> {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            VndError vndError = new VndError("error", msg);
            vndErrors.add(vndError);
        });
        return !vndErrors.isEmpty() ? new VndErrors(vndErrors) : null;       
    }
    */
}

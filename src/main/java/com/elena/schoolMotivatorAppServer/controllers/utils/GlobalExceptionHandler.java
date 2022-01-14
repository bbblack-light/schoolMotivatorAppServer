package com.elena.schoolMotivatorAppServer.controllers.utils;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.EntityAlreadyExistsException;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.InvalidArgumentException;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.UnauthorizedException;
import javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.*;

/*
@ControllerAdvice tells your spring application that this class will do the exception handling for your application.
@RestController will make it a controller and let this class render the response.
Use @ExceptionHandler annotation to define the class of Exception it will catch. (A Base class will catch all the Inherited and extended classes)
*/

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ResponseEntity handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity(new OperationResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public ResponseEntity handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity(new OperationResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InvalidArgumentException.class})
    @ResponseBody
    public ResponseEntity handleInvalidArgumentException(InvalidArgumentException ex) {
        return new ResponseEntity(new OperationResponse(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    @ResponseBody
    public ResponseEntity handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return new ResponseEntity(new OperationResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity handleBaseException(DataIntegrityViolationException e) {
        return new ResponseEntity(new OperationResponse(e.getRootCause().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity handleNullPointerException(NullPointerException e) {
        return new ResponseEntity(new OperationResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

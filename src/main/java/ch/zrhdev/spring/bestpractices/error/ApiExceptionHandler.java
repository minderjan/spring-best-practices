package ch.zrhdev.spring.bestpractices.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Environment environment;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Failed to validate the requested arguments", errors);
        return new ResponseEntity<>(apiErrorResponse, headers, apiErrorResponse.getStatusObject());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "This Media Type is not supported", "unsupported:"+ unsupported + "supported:"+ supported);
        return new ResponseEntity<>(apiErrorResponse, headers, apiErrorResponse.getStatusObject());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        ApiErrorResponse apiErrorResponse;
        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            apiErrorResponse = new ApiErrorResponse(status, exceptionName, message);
        } else {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Failed to read the requested message", ex.getMessage());
        }
        return new ResponseEntity<>(apiErrorResponse, headers, apiErrorResponse.getStatusObject());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getRootCause();
        ApiErrorResponse apiErrorResponse;
        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            apiErrorResponse = new ApiErrorResponse(status, exceptionName, message);
        } else {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "Could not find the requested resource.", ex.getMessage());
        }
        return new ResponseEntity<>(apiErrorResponse, headers, apiErrorResponse.getStatusObject());
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "could not find the requested entity");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusObject());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setErrors("The Server failed to proceed this request");

        // Only provide the error message on development instances
        for (final String profileName : environment.getActiveProfiles()) {
            if (profileName.equals("local") || profileName.equals("tychon")) {
                apiError.setMessage(ex.getLocalizedMessage());
            } else {
                apiError.setMessage("Something went wrong during request processing");
            }
        }
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusObject());
    }
}

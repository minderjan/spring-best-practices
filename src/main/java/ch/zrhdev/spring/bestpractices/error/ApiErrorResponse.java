package ch.zrhdev.spring.bestpractices.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.message = message;
        this.errors = errors;
        this.status = status;
    }

    public ApiErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.message = message;
        errors = Arrays.asList(error);
        this.status = status;
    }

    public ApiErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setErrors(String error) {
        this.errors = Arrays.asList(error);
    }

    @JsonIgnore
    public HttpStatus getStatusObject() {
        return status;
    }

    public String getStatus() {
        return status.getReasonPhrase();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public static ObjectNode getJsonMessage(String message, String error){
        return new ObjectMapper().createObjectNode().put("Message", message).put("Error", error);
    }

}

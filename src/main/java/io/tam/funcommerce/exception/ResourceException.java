package io.tam.funcommerce.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpStatus internalServerError() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus notFound() {
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus badRequest() {
        return HttpStatus.BAD_REQUEST;
    }

    public ResourceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}


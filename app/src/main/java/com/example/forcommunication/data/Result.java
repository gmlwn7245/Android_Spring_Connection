package com.example.forcommunication.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
@NoArgsConstructor
@ToString
public class Result {

    // Success sub-class
    @RequiredArgsConstructor
    @Getter
    @ToString
    public final static class Success<T> extends Result {
        private final T data;
    }

    // Error sub-class
    @RequiredArgsConstructor
    @Getter
    @ToString
    public final static class Error extends Result {
        private final Exception error;
    }

}
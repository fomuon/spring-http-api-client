package org.example.httpclient;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface ResponseMapper<T> {

    T map(ResponseEntity<T> responseEntity);
}

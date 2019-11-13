package com.sankuai.inf.leaf.admin.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

public class Rest<T> implements Serializable {
    private static final long serialVersionUID = 5305943341261130670L;

    private Integer status;
    private String message;
    private T data;

    public static <T> Rest<T> success(T data) {
        return build(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> Rest<T> failure(String message) {
        return build(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> Rest<T> build(Integer status, String message, T data) {
        Rest<T> rest = new Rest<>();
        rest.setStatus(status);
        rest.setMessage(message);
        rest.setData(data);
        return rest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rest<?> rest = (Rest<?>) o;
        return Objects.equals(status, rest.status) &&
                Objects.equals(message, rest.message) &&
                Objects.equals(data, rest.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package ru.sfedu.accounter.lab1.model;

import java.util.Objects;

public class Result {
    public enum State {
        Success,
        Warning,
        Error
    }
    private State state;
    private String message;

    public Result(State state, String message) {
        this.state = state;
        this.message = message;
    }

    public void setState(Result.State state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return state + ": " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result result)) return false;
        return getState() == result.getState() && Objects.equals(getMessage(), result.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState(), getMessage());
    }
}

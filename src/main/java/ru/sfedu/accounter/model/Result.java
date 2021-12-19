package ru.sfedu.accounter.model;

public class Result {
    private ResultState resultState;
    private String message;

    public Result(ResultState resultState, String message) {
        this.resultState = resultState;
        this.message = message;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{resultState=" + resultState + ", message=: " + message + "}";
    }
}
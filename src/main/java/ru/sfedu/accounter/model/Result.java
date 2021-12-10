package ru.sfedu.accounter.model;

public class Result {
    private ResultType resultType;
    private String message;

    public Result(ResultType resultType, String message) {
        this.resultType = resultType;
        this.message = message;
    }

    public void setResultState(ResultType resultType) {
        this.resultType = resultType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultType getResultState() {
        return resultType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{resultState=" + resultType + ", message=: " + message + "}";
    }
}
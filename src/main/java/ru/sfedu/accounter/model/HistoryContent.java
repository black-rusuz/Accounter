package ru.sfedu.accounter.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class HistoryContent implements Serializable {
    private UUID _id;
    private String className;
    private String creationDate;
    private String actor;
    private String methodName;
    private String json;
    private Result.State state;

    public HistoryContent() {
    }

    public HistoryContent(UUID _id, String className, String creationDate, String actor, String methodName, String json, Result.State state) {
        set_id(_id);
        setClassName(className);
        setCreationDate(creationDate);
        setActor(actor);
        setMethodName(methodName);
        setJson(json);
        setState(state);
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Result.State getState() {
        return state;
    }

    public void setState(Result.State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryContent that = (HistoryContent) o;
        return Objects.equals(get_id(), that.get_id())
                && Objects.equals(getClassName(), that.getClassName())
                && Objects.equals(getCreationDate(), that.getCreationDate())
                && Objects.equals(getActor(), that.getActor())
                && Objects.equals(getMethodName(), that.getMethodName())
                && Objects.equals(getJson(), that.getJson())
                && getState() == that.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                get_id(),
                getClassName(),
                getCreationDate(),
                getActor(),
                getMethodName(),
                getJson(),
                getState());
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "id=" + _id +
                ", className='" + className + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", json=" + json +
                ", status=" + state +
                '}';
    }
}

package ru.sfedu.accounter.lab2.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Neeested {
    private String description;
    private String link;

    public Neeested() {
    }

    public Neeested(String description, String link) {
        this.description = description;
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neeested neeested)) return false;
        return Objects.equals(getDescription(), neeested.getDescription()) && Objects.equals(getLink(), neeested.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getLink());
    }

    @Override
    public String toString() {
        return "Nested{" +
                "description='" + description + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

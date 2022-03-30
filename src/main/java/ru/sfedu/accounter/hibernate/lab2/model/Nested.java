package ru.sfedu.accounter.hibernate.lab2.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Nested {
    private String description;
    private String link;

    public Nested() {
    }

    public Nested(String description, String link) {
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
        if (!(o instanceof Nested nested)) return false;
        return Objects.equals(getDescription(), nested.getDescription()) && Objects.equals(getLink(), nested.getLink());
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

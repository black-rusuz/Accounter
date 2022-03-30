package ru.sfedu.accounter.hibernate.lab4.list;

import javax.persistence.*;
import java.util.Objects;
import java.util.List;

@Entity
public class Bean {
    @Id
    @GeneratedValue
    private long id;
    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn
    private List<String> strings;

    public Bean() {
    }

    public Bean(long id, List<String> strings) {
        this.id = id;
        this.strings = strings;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return id == bean.id && Objects.equals(strings, bean.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strings);
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", strings=" + strings +
                '}';
    }
}

package ru.sfedu.accounter.lab2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Bean implements Serializable {

    @Id @GeneratedValue
    private long id;
    private String name;
    private Date dateCreated;
    @Column(name = "isChecked")
    private boolean check;
    @Embedded
    private Nested nested;

    public Bean() {
    }

    public Bean(long id, String name, Date dateCreated, boolean check, Nested nested) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.check = check;
        this.nested = nested;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Nested getNested() {
        return nested;
    }

    public void setNested(Nested nested) {
        this.nested = nested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bean that)) return false;
        return getId() == that.getId()
                && isCheck() == that.isCheck()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDateCreated(), that.getDateCreated())
                && Objects.equals(getNested(), that.getNested());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDateCreated(), isCheck(), getNested());
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + dateCreated +
                ", check=" + check +
                ", balance=" + nested +
                '}';
    }
}

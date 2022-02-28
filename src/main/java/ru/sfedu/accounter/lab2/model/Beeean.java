package ru.sfedu.accounter.lab2.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@javax.persistence.Entity
public class Beeean implements Serializable {

    @Id @GeneratedValue
    private long id;
    private String name;
    private Date dateCreated;
    @Column(name = "isChecked")
    private boolean check;
    @Embedded
    private Neeested neeested;

    public Beeean() {
    }

    public Beeean(long id, String name, Date dateCreated, boolean check, Neeested neeested) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.check = check;
        this.neeested = neeested;
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

    public Neeested getNeeested() {
        return neeested;
    }

    public void setNeeested(Neeested neeested) {
        this.neeested = neeested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beeean that)) return false;
        return getId() == that.getId()
                && isCheck() == that.isCheck()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDateCreated(), that.getDateCreated())
                && Objects.equals(getNeeested(), that.getNeeested());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDateCreated(), isCheck(), getNeeested());
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + dateCreated +
                ", check=" + check +
                ", balance=" + neeested +
                '}';
    }
}

package ru.sfedu.accounter.hibernate.lab3.msc.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class Transaction
 */
@Element
@MappedSuperclass
public abstract class Transaction implements Serializable {

    //
    // Fields
    //

    @Id
    @GeneratedValue
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Column(name = "size")
    @Element
    @CsvBindByPosition(position = 1)
    private double value;

    @Element
    @CsvBindByPosition(position = 2)
    private String name = "";

    //
    // Constructors
    //

    public Transaction() {
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of id System.currenttimemillis()
     */
    public void setId() {
        id = System.currentTimeMillis();
    }

    /**
     * Set the value of id
     *
     * @param newVar the new value of id
     */
    public void setId(long newVar) {
        id = newVar;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the value of value
     *
     * @param newVar the new value of value
     */
    public void setValue(double newVar) {
        value = newVar;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public double getValue() {
        return value;
    }

    /**
     * Set the value of name
     *
     * @param newVar the new value of name
     */
    public void setName(String newVar) {
        name = newVar;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    //
    // Other methods
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction transaction)) return false;
        return getId() == transaction.getId()
                && Double.compare(transaction.getValue(), getValue()) == 0
                && Objects.equals(getName(), transaction.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getName());
    }

    @Override
    public String toString() {
        return String.format("| %13d | %12s | %-20s |",
                getId(),
                getValue(),
                getName());
    }
}

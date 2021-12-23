package ru.sfedu.accounter.model.beans;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Transaction
 */
@Element
abstract public class Transaction implements Serializable {

    //
    // Fields
    //

    @Attribute
    @CsvBindByName
    private long id;

    @Element
    @CsvBindByName
    private double value;

    @Element
    @CsvBindByName
    private String name;

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
        return "Transaction{" +
                "id=" + getId() +
                ", value=" + getValue() +
                ", name='" + getName() + '\'' +
                '}';
    }
}

package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Balance
 */
@Element
public class Balance implements Serializable {

    //
    // Fields
    //

    @Attribute
    private long id;

    @Element
    private String time;

    @Element
    private double value;

    //
    // Constructors
    //

    public Balance() {
    }

    public Balance(String time, double value) {
        setId();
        setTime(time);
        setValue(value);
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
     * Set the value of time
     *
     * @param newVar the new value of time
     */
    public void setTime(String newVar) {
        time = newVar;
    }

    /**
     * Get the value of time
     *
     * @return the value of time
     */
    public String getTime() {
        return time;
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

    //
    // Other methods
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance balance)) return false;
        return getId() == balance.getId()
                && Double.compare(balance.getValue(), getValue()) == 0
                && Objects.equals(getTime(), balance.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTime(), getValue());
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + getId() +
                ", time='" + getTime() + '\'' +
                ", value=" + getValue() +
                '}';
    }
}

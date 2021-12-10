package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Class Transaction
 */
abstract public class Transaction implements Serializable {

    //
    // Fields
    //

    @Attribute
    private long id;

    // String is temporarily used because SimpleXml does not serialize LocalDateTime
    // TODO: Change to LocalDateTime
    @Element
    private String time;

    @Element
    private double value;

    @Element
    private String name;

    @Element
    private Balance newBalance;

    //
    // Constructors
    //

    public Transaction () { };

    public Transaction (String time, double value, String name, Balance newBalance) {
        setId();
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
    };

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of id System.currenttimemillis()
     */
    public void setId () {
        id = System.currentTimeMillis();
    }

    /**
     * Set the value of id
     * @param newVar the new value of id
     */
    public void setId (long newVar) {
        id = newVar;
    }

    /**
     * Get the value of id
     * @return the value of id
     */
    public long getId () {
        return id;
    }

    /**
     * Set the value of time
     * @param newVar the new value of time
     */
    public void setTime (String newVar) {
        time = newVar;
    }

    /**
     * Get the value of time
     * @return the value of time
     */
    public String getTime () {
        return time;
    }

    /**
     * Set the value of value
     * @param newVar the new value of value
     */
    public void setValue (double newVar) {
        value = newVar;
    }

    /**
     * Get the value of value
     * @return the value of value
     */
    public double getValue () {
        return value;
    }

    /**
     * Set the value of name
     * @param newVar the new value of name
     */
    public void setName (String newVar) {
        name = newVar;
    }

    /**
     * Get the value of name
     * @return the value of name
     */
    public String getName () {
        return name;
    }

    /**
     * Set the value of newBalance
     * @param newVar the new value of newBalance
     */
    public void setNewBalance (Balance newVar) {
        newBalance = newVar;
    }

    /**
     * Get the value of newBalance
     * @return the value of newBalance
     */
    public Balance getNewBalance () {
        return newBalance;
    }

    //
    // Other methods
    //

}

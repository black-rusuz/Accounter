package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Class Plan
 */
public class Plan implements Serializable {

    //
    // Fields
    //

    @Attribute
    private long id;

    // String is temporarily used because SimpleXml does not serialize LocalDateTime
    // TODO: Change to LocalDateTime
    @Element
    private String startDate;

    @Element
    private String name;

    @Element
    private String period;

    @Element
    private Transaction transaction;

    //
    // Constructors
    //

    public Plan () { };

    public Plan (String startDate, String name, String period, Transaction transaction) {
        setId();
        setStartDate(startDate);
        setName(name);
        setPeriod(period);
        setTransaction(transaction);
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
     * Set the value of startDate
     * @param newVar the new value of startDate
     */
    public void setStartDate (String newVar) {
        startDate = newVar;
    }

    /**
     * Get the value of startDate
     * @return the value of startDate
     */
    public String getStartDate () {
        return startDate;
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
     * Set the value of period
     * @param newVar the new value of period
     */
    public void setPeriod (String newVar) {
        period = newVar;
    }

    /**
     * Get the value of period
     * @return the value of period
     */
    public String getPeriod () {
        return period;
    }

    /**
     * Set the value of transaction
     * @param newVar the new value of transaction
     */
    public void setTransaction (Transaction newVar) {
        transaction = newVar;
    }

    /**
     * Get the value of transaction
     * @return the value of transaction
     */
    public Transaction getTransaction () {
        return transaction;
    }

    //
    // Other methods
    //

}

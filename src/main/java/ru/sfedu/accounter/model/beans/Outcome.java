package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Element;

import java.io.Serializable;


/**
 * Class Outcome
 */
public class Outcome extends Transaction implements Serializable {

    //
    // Fields
    //

    @Element
    private OutcomeCategory outcomeCategory;

    //
    // Constructors
    //

    public Outcome () { };

    public Outcome (String time, double value, String name, Balance newBalance, OutcomeCategory outcomeCategory) {
        setId();
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
        setOutcomeCategory(outcomeCategory);
    };

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of outcomeCategory
     * @param newVar the new value of outcomeCategory
     */
    public void setOutcomeCategory (OutcomeCategory newVar) {
        outcomeCategory = newVar;
    }

    /**
     * Get the value of outcomeCategory
     * @return the value of outcomeCategory
     */
    public OutcomeCategory getOutcomeCategory () {
        return outcomeCategory;
    }

    //
    // Other methods
    //

}

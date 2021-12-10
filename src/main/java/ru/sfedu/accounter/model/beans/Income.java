package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Element;

import java.io.Serializable;


/**
 * Class Income
 */
public class Income extends Transaction implements Serializable {

    //
    // Fields
    //

    @Element
    private IncomeCategory incomeCategory;

    //
    // Constructors
    //

    public Income () { };

    public Income (String time, double value, String name, Balance newBalance, IncomeCategory incomeCategory) {
        setId();
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
        setIncomeCategory(incomeCategory);
    };

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of incomeCategory
     * @param newVar the new value of incomeCategory
     */
    public void setIncomeCategory (IncomeCategory newVar) {
        incomeCategory = newVar;
    }

    /**
     * Get the value of incomeCategory
     * @return the value of incomeCategory
     */
    public IncomeCategory getIncomeCategory () {
        return incomeCategory;
    }

    //
    // Other methods
    //

}

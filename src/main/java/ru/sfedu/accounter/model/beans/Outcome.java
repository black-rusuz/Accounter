package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Element;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

import java.io.Serializable;
import java.util.Objects;


/**
 * Class Outcome
 */
@Element
public class Outcome extends Transaction implements Serializable {

    //
    // Fields
    //

    @Element
    private OutcomeCategory outcomeCategory;

    //
    // Constructors
    //

    public Outcome() {
    }

    public Outcome(String time, double value, String name, Balance newBalance, OutcomeCategory outcomeCategory) {
        setId();
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
        setOutcomeCategory(outcomeCategory);
    }

    public Outcome(long id, String time, double value, String name, Balance newBalance, OutcomeCategory outcomeCategory) {
        setId(id);
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
        setOutcomeCategory(outcomeCategory);
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of outcomeCategory
     *
     * @param newVar the new value of outcomeCategory
     */
    public void setOutcomeCategory(OutcomeCategory newVar) {
        outcomeCategory = newVar;
    }

    /**
     * Get the value of outcomeCategory
     *
     * @return the value of outcomeCategory
     */
    public OutcomeCategory getOutcomeCategory() {
        return outcomeCategory;
    }

    //
    // Other methods
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Outcome outcome)) return false;
        return getId() == outcome.getId()
                && Double.compare(outcome.getValue(), getValue()) == 0
                && Objects.equals(getTime(), outcome.getTime())
                && Objects.equals(getName(), outcome.getName())
                && Objects.equals(getNewBalance(), outcome.getNewBalance())
                && getOutcomeCategory() == outcome.getOutcomeCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOutcomeCategory());
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "id=" + getId() +
                ", time='" + getTime() + '\'' +
                ", value=" + getValue() +
                ", name='" + getName() + '\'' +
                ", newBalance=" + getNewBalance() +
                ", outcomeCategory=" + outcomeCategory +
                '}';
    }
}

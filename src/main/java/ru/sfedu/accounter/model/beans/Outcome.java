package ru.sfedu.accounter.model.beans;

import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName(column = "outcome_category")
    private OutcomeCategory category;

    //
    // Constructors
    //

    public Outcome() {
    }

    public Outcome(double value, String name, OutcomeCategory category) {
        setId();
        setValue(value);
        setName(name);
        setCategory(category);
    }

    public Outcome(long id, double value, String name, OutcomeCategory category) {
        setId(id);
        setValue(value);
        setName(name);
        setCategory(category);
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
    public void setCategory(OutcomeCategory newVar) {
        category = newVar;
    }

    /**
     * Get the value of outcomeCategory
     *
     * @return the value of outcomeCategory
     */
    public OutcomeCategory getCategory() {
        return category;
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
                && Objects.equals(getName(), outcome.getName())
                && getCategory() == outcome.getCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory());
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "id=" + getId() +
                ", value=" + getValue() +
                ", name='" + getName() + '\'' +
                ", outcomeCategory=" + category +
                '}';
    }
}

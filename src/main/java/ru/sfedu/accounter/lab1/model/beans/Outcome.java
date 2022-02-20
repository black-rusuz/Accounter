package ru.sfedu.accounter.lab1.model.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.accounter.lab1.model.enums.OutcomeCategory;

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
    @CsvBindByPosition(position = 3)
    private OutcomeCategory category = OutcomeCategory.OUTCOME;

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
        return Objects.hash(getId(), getValue(), getName(), getCategory());
    }

    @Override
    public String toString() {
        return String.format("| %13d | - %12s | %-20s | %-12s |",
                getId(),
                String.format("%.2f", getValue()),
                getName(),
                getCategory());
    }
}

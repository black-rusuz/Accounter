package ru.sfedu.accounter.model.beans;

import org.simpleframework.xml.Element;
import ru.sfedu.accounter.model.enums.IncomeCategory;

import java.io.Serializable;
import java.util.Objects;


/**
 * Class Income
 */
@Element
public class Income extends Transaction implements Serializable {

    //
    // Fields
    //

    @Element
    private IncomeCategory incomeCategory;

    //
    // Constructors
    //

    public Income() {
    }

    public Income(String time, double value, String name, Balance newBalance, IncomeCategory incomeCategory) {
        setId();
        setTime(time);
        setValue(value);
        setName(name);
        setNewBalance(newBalance);
        setIncomeCategory(incomeCategory);
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of incomeCategory
     *
     * @param newVar the new value of incomeCategory
     */
    public void setIncomeCategory(IncomeCategory newVar) {
        incomeCategory = newVar;
    }

    /**
     * Get the value of incomeCategory
     *
     * @return the value of incomeCategory
     */
    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    //
    // Other methods
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income income)) return false;
        return getId() == income.getId()
                && Double.compare(income.getValue(), getValue()) == 0
                && Objects.equals(getTime(), income.getTime())
                && Objects.equals(getName(), income.getName())
                && Objects.equals(getNewBalance(), income.getNewBalance())
                && getIncomeCategory() == income.getIncomeCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTime(), getValue(), getName(), getNewBalance(), getIncomeCategory());
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + getId() +
                ", time='" + getTime() + '\'' +
                ", value=" + getValue() +
                ", name='" + getName() + '\'' +
                ", newBalance=" + getNewBalance() +
                ", incomeCategory=" + getIncomeCategory() +
                '}';
    }
}

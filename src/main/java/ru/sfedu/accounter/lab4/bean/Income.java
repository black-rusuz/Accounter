package ru.sfedu.accounter.lab4.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.accounter.model.enums.IncomeCategory;

import javax.persistence.Entity;
import java.util.Objects;


/**
 * Class Income
 */
@Element
@Entity
public class Income extends Transaction {

    //
    // Fields
    //

    @Element
    @CsvBindByPosition(position = 3)
    private IncomeCategory category = IncomeCategory.INCOME;

    //
    // Constructors
    //

    public Income() {
    }

    public Income(double value, String name, IncomeCategory category) {
        setId();
        setValue(value);
        setName(name);
        setCategory(category);
    }

    public Income(long id, double value, String name, IncomeCategory category) {
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
     * Set the value of incomeCategory
     *
     * @param newVar the new value of incomeCategory
     */
    public void setCategory(IncomeCategory newVar) {
        category = newVar;
    }

    /**
     * Get the value of incomeCategory
     *
     * @return the value of incomeCategory
     */
    public IncomeCategory getCategory() {
        return category;
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
                && Objects.equals(getName(), income.getName())
                && getCategory() == income.getCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getName(), getCategory());
    }

    @Override
    public String toString() {
        return String.format("| %13d | + %12s | %-20s | %-12s |",
                getId(),
                String.format("%.2f", getValue()),
                getName(),
                getCategory());
    }
}

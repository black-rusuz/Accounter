package ru.sfedu.accounter.model.beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.accounter.utils.TransactionConverter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Plan
 */
@Element
public class Plan implements Serializable {

    //
    // Fields
    //

    @Attribute
    @CsvBindByName(column = "plan_id")
    private long id;

    @Element
    @CsvBindByName(column = "plan_period")
    private long period;

    @Element(type = Transaction.class)
    @CsvCustomBindByName(converter = TransactionConverter.class)
    private Transaction transaction;

    //
    // Constructors
    //

    public Plan() {
    }

    public Plan(long period, Transaction transaction) {
        setId();
        setPeriod(period);
        setTransaction(transaction);
    }

    public Plan(long id, long period, Transaction transaction) {
        setId(id);
        setPeriod(period);
        setTransaction(transaction);
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
     * Set the value of period
     *
     * @param newVar the new value of period
     */
    public void setPeriod(long newVar) {
        period = newVar;
    }

    /**
     * Get the value of period
     *
     * @return the value of period
     */
    public long getPeriod() {
        return period;
    }

    /**
     * Set the value of transaction
     *
     * @param newVar the new value of transaction
     */
    public void setTransaction(Transaction newVar) {
        transaction = newVar;
    }

    /**
     * Get the value of transaction
     *
     * @return the value of transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    //
    // Other methods
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plan plan)) return false;
        return getId() == plan.getId()
                && getPeriod() == plan.getPeriod()
                && Objects.equals(getTransaction(), plan.getTransaction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPeriod(), getTransaction());
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", period=" + period +
                ", transaction=" + transaction +
                '}';
    }
}

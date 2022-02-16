package ru.sfedu.accounter.utils;

import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

public class SampleData {
    public Balance b1;
    public Balance b2;

    public Income i1;
    public Income i2;

    public Outcome o1;
    public Outcome o2;

    public Plan p1;
    public Plan p2;

    public void createObjects() {
        b1 = new Balance(30000);
        b2 = new Balance(29300);

        i1 = new Income(30000, "Salary", IncomeCategory.SALARY);
        i2 = new Income(100, "Found on street", IncomeCategory.BONUS);

        o1 = new Outcome(500, "Megafon", OutcomeCategory.SUBSCRIPTION);
        o2 = new Outcome(300, "Food", OutcomeCategory.SUPERMARKET);

        p1 = new Plan(1000L * 60 * 60 * 24 * 30, i1);
        p2 = new Plan(1000L * 60 * 60 * 24 * 30, o1);
    }
}

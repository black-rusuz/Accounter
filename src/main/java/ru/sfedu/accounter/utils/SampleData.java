package ru.sfedu.accounter.utils;

import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

public class SampleData {
    public Balance b1;
    public Balance b2;

    public Transaction t1;
    public Transaction t2;
    public Transaction t3;
    public Transaction t4;

    public Plan p1;
    public Plan p2;

    public void createObjects() {
        b1 = new Balance(30000);
        b2 = new Balance(29300);

        t1 = new Income(30000, "Salary", IncomeCategory.SALARY);
        t2 = new Outcome(500, "Megafon", OutcomeCategory.SUBSCRIPTION);
        t3 = new Outcome(300, "Food", OutcomeCategory.SUPERMARKET);
        t4 = new Income(100, "Found on street", IncomeCategory.BONUS);

        p1 = new Plan(1000L * 60 * 60 * 24 * 30, t1);
        p2 = new Plan(1000L * 60 * 60 * 24 * 30, t2);
    }
}

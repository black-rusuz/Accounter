package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

public class TestBase {
    Balance b1;
    Balance b2;
    Plan p1;
    Plan p2;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;

    public void createObjects() {
        b1 = new Balance(30000);
        b2 = new Balance(29300);

        p1 = new Plan(1000L * 60 * 60 * 24 * 30, t1);
        p2 = new Plan(1000L * 60 * 60 * 24 * 30, t2);

        t1 = new Income(30000, "Salary", IncomeCategory.Salary);
        t2 = new Outcome(500, "Megafon", OutcomeCategory.Subscription);
        t3 = new Outcome(300, "Food", OutcomeCategory.Supermarket);
        t4 = new Income(100, "Found on street", IncomeCategory.Bonus);
    }
}
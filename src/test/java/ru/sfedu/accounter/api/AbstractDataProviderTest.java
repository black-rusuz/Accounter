package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;
import java.util.List;

public class AbstractDataProviderTest extends SampleData {
    public AbstractDataProvider dataProvider = new DataProviderXml();
    public static final Logger log = LogManager.getLogger(AbstractDataProviderTest.class);

    public AbstractDataProviderTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        createObjects();
        loadSampleData();
    }

    public void loadSampleData() {
        b1 = dataProvider.appendBalance(b1);
        b2 = dataProvider.appendBalance(b2);

        t1 = dataProvider.appendTransaction(t1);
        t2 = dataProvider.appendTransaction(t2);
        t3 = dataProvider.appendTransaction(t3);
        t4 = dataProvider.appendTransaction(t4);

        p1 = dataProvider.appendPlan(p1);
        p2 = dataProvider.appendPlan(p2);
    }

    @AfterEach
    public void tearDown() {
        cleanUp();
    }

    public void cleanUp() {
        dataProvider.deleteBalance(b1.getId());
        dataProvider.deleteBalance(b2.getId());

        dataProvider.deleteTransaction(t1.getId());
        dataProvider.deleteTransaction(t2.getId());
        dataProvider.deleteTransaction(t3.getId());
        dataProvider.deleteTransaction(t4.getId());

        dataProvider.deletePlan(p1.getId());
        dataProvider.deletePlan(p2.getId());
    }

    @Test
    public void manageBalanceTestPositive() {
        List<Balance> expectedBalancesHistory = List.of(b1, b2);
        List<Balance> actualBalancesHistory = dataProvider.manageBalance("", 0);
        Assertions.assertEquals(expectedBalancesHistory, actualBalancesHistory);
        dataProvider.deleteBalance(dataProvider.getAllBalance().get(dataProvider.getAllBalance().size() - 1).getId());
    }

    @Test
    public void manageBalanceTestNegative() {
        List<Balance> expectedBalancesHistory = List.of(b1);
        List<Balance> actualBalancesHistory = dataProvider.manageBalance("", 0);
        Assertions.assertNotEquals(expectedBalancesHistory, actualBalancesHistory);
        dataProvider.deleteBalance(dataProvider.getAllBalance().get(dataProvider.getAllBalance().size() - 1).getId());
    }

    @Test
    public void calculateBalanceTestPositive() {
        List<Transaction> expectedTransactions = List.of(t1, t2, t3, t4);
        double expectedBalanceValue = 0;
        for (Transaction transaction : expectedTransactions) {
            if (transaction.getClass().equals(Income.class))
                expectedBalanceValue += transaction.getValue();
            if (transaction.getClass().equals(Outcome.class))
                expectedBalanceValue -= transaction.getValue();
        }
        Balance actualNewBalance = dataProvider.calculateBalance().get();
        Balance expectedNewBalance = new Balance(actualNewBalance.getId(), expectedBalanceValue);
        Assertions.assertEquals(expectedNewBalance, actualNewBalance);
        dataProvider.deleteBalance(actualNewBalance.getId());
    }

    @Test
    public void calculateBalanceTestNegative() {
        List<Transaction> expectedTransactions = List.of(t1, t2, t3);
        double expectedBalanceValue = 0;
        for (Transaction transaction : expectedTransactions) {
            if (transaction.getClass().equals(Income.class))
                expectedBalanceValue += transaction.getValue();
            if (transaction.getClass().equals(Outcome.class))
                expectedBalanceValue -= transaction.getValue();
        }
        Balance actualNewBalance = dataProvider.calculateBalance().get();
        Balance expectedNewBalance = new Balance(actualNewBalance.getId(), expectedBalanceValue);
        Assertions.assertNotEquals(expectedNewBalance, actualNewBalance);
        dataProvider.deleteBalance(actualNewBalance.getId());
    }

    @Test
    public void displayIncomesAndOutcomesTestPositive() {
        List<Transaction> expectedTransactions = List.of(t1, t2, t3, t4);
        List<Transaction> actualTransactions = dataProvider.displayIncomesAndOutcomes();
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void displayIncomesAndOutcomesTestNegative() {
        List<Transaction> expectedTransactions = List.of(t1, t2, t3);
        List<Transaction> actualTransactions = dataProvider.displayIncomesAndOutcomes();
        Assertions.assertNotEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void repeatTransactionTestPositive() {
        Transaction actualTransaction = dataProvider.repeatTransaction(t1.getId()).get();
        Transaction expectedTransaction = t1;
        expectedTransaction.setId(actualTransaction.getId());
        Assertions.assertEquals(expectedTransaction, actualTransaction);
        dataProvider.deleteTransaction(dataProvider.getAllTransaction().get(0).getId());
    }

    @Test
    public void repeatTransactionTestNegative() {
        Transaction actualTransaction = dataProvider.repeatTransaction(t1.getId()).get();
        Transaction expectedTransaction = t2;
        expectedTransaction.setId(actualTransaction.getId());
        Assertions.assertNotEquals(expectedTransaction, actualTransaction);
        dataProvider.deleteTransaction(dataProvider.getAllTransaction().get(1).getId());
    }

    @Test
    public void makePlanBasedOnTransactionTestPositive() {
        Plan actualPlan = dataProvider.makePlanBasedOnTransaction(t1.getId()).get();
        Plan expectedPlan = new Plan(actualPlan.getId(), (long) 1000 * 60 * 60 * 24 * 30, dataProvider.getTransactionById(t1.getId()));
        Assertions.assertEquals(expectedPlan, actualPlan);
        dataProvider.deletePlan(actualPlan.getId());
    }

    @Test
    public void makePlanBasedOnTransactionTestNegative() {
        Plan actualPlan = dataProvider.makePlanBasedOnTransaction(t1.getId()).get();
        Plan expectedPlan = new Plan(actualPlan.getId(), (long) 1000 * 60 * 60 * 24 * 30, dataProvider.getTransactionById(t2.getId()));
        Assertions.assertNotEquals(expectedPlan, actualPlan);
        dataProvider.deletePlan(actualPlan.getId());
    }

    @Test
    public void managePlansTestPositive() {
        List<Plan> expectedPlanList = List.of(p1, p2);
        List<Plan> actualPlanList = dataProvider.managePlans(0, false);
        Assertions.assertEquals(expectedPlanList, actualPlanList);
    }

    @Test
    public void managePlansTestNegative() {
        List<Plan> expectedPlanList = List.of(p1);
        List<Plan> actualPlanList = dataProvider.managePlans(0, false);
        Assertions.assertNotEquals(expectedPlanList, actualPlanList);
    }

    @Test
    public void displayPlansTestPositive() {
        List<Plan> expectedPlanList = List.of(p1, p2);
        List<Plan> actualPlanList = dataProvider.displayPlans();
        Assertions.assertEquals(expectedPlanList, actualPlanList);
    }

    @Test
    public void displayPlansTestNegative() {
        List<Plan> expectedPlanList = List.of(p1);
        List<Plan> actualPlanList = dataProvider.displayPlans();
        Assertions.assertNotEquals(expectedPlanList, actualPlanList);
    }

    @Test
    public void executePlanNowTestPositive() {
        Transaction expectedTransaction = p1.getTransaction();
        Transaction actualTransaction = dataProvider.executePlanNow(p1.getId()).get();
        expectedTransaction.setId(actualTransaction.getId());
        Assertions.assertEquals(expectedTransaction, actualTransaction);
        dataProvider.deleteTransaction(dataProvider.getAllTransaction().get(0).getId());
    }

    @Test
    public void executePlanNowTestNegative() {
        Transaction expectedTransaction = p1.getTransaction();
        Transaction actualTransaction = dataProvider.executePlanNow(p2.getId()).get();
        expectedTransaction.setId(actualTransaction.getId());
        Assertions.assertNotEquals(expectedTransaction, actualTransaction);
        dataProvider.deleteTransaction(dataProvider.getAllTransaction().get(0).getId());
    }
}

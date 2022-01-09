package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDataProviderTest extends TestBase {
    protected IDataProvider dataProvider;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        createObjects();
    }

    @AfterEach
    public void tearDown() {
        dataProvider.deleteBalance(b1.getId());
        dataProvider.deleteBalance(b2.getId());

        dataProvider.deletePlan(p1.getId());
        dataProvider.deletePlan(p2.getId());

        dataProvider.deleteTransaction(t1.getId());
        dataProvider.deleteTransaction(t2.getId());
        dataProvider.deleteTransaction(t3.getId());
        dataProvider.deleteTransaction(t4.getId());
    }

    @Test
    public void testGetAllBalancePositive() {
        List<Balance> list = List.of(b1, b2);
        b1 = dataProvider.appendBalance(b1);
        b2 = dataProvider.appendBalance(b2);
        Assertions.assertEquals(list, dataProvider.getAllBalance());
    }

    @Test
    public void testGetAllBalanceNegative() {
        List<Balance> list = List.of(b1, b2);
        b1 = dataProvider.appendBalance(b1);
        Assertions.assertNotEquals(list, dataProvider.getAllBalance());
    }

    @Test
    public void testGetBalanceByIdPositive() {
        b1 = dataProvider.appendBalance(b1);
        Assertions.assertEquals(b1, dataProvider.getBalanceById(b1.getId()));
    }

    @Test
    public void testGetBalanceByIdNegative() {
        Assertions.assertEquals(new Balance(), dataProvider.getBalanceById(1L));
    }

    @Test
    public void testAppendBalancePositive() {
        Assertions.assertEquals(b1, dataProvider.appendBalance(b1));
    }

    @Test
    public void testAppendBalanceNegative() {
        Assertions.assertNotEquals(b1, dataProvider.appendBalance(b2));
    }

    @Test
    public void testDeleteBalancePositive() {
        b1 = dataProvider.appendBalance(b1);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.deleteBalance(b1.getId()));
    }

    @Test
    public void testDeleteBalanceNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.deleteBalance(b1.getId()));
    }

    @Test
    public void testUpdateBalancePositive() {
        dataProvider.appendBalance(b1);
        b1.setValue(500);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updateBalance(b1));
    }

    @Test
    public void testUpdateBalanceNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updateBalance(b1));
    }

    @Test
    public void testGetAllPlanPositive() {
        List<Plan> list = List.of(p1, p2);
        p1 = dataProvider.appendPlan(p1);
        p2 = dataProvider.appendPlan(p2);
        Assertions.assertEquals(list, dataProvider.getAllPlan());
    }

    @Test
    public void testGetAllPlanNegative() {
        List<Plan> list = List.of(p1, p2);
        p1 = dataProvider.appendPlan(p1);
        Assertions.assertNotEquals(list, dataProvider.getAllPlan());
    }

    @Test
    public void testGetPlanByIdPositive() {
        p1 = dataProvider.appendPlan(p1);
        Assertions.assertEquals(p1, dataProvider.getPlanById(p1.getId()));
    }

    @Test
    public void testGetPlanByIdNegative() {
        Assertions.assertEquals(new Plan(), dataProvider.getPlanById(1L));
    }

    @Test
    public void testAppendPlanPositive() {
        Assertions.assertEquals(p1, dataProvider.appendPlan(p1));
    }

    @Test
    public void testAppendPlanNegative() {
        Assertions.assertNotEquals(p1, dataProvider.appendPlan(p2));
    }

    @Test
    public void testDeletePlanPositive() {
        p1 = dataProvider.appendPlan(p1);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.deletePlan(p1.getId()));
    }

    @Test
    public void testDeletePlanNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.deletePlan(p1.getId()));
    }

    @Test
    public void testUpdatePlanPositive() {
        dataProvider.appendPlan(p1);
        p1.setPeriod(500L);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updatePlan(p1));
    }

    @Test
    public void testUpdatePlanNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updatePlan(p1));
    }

    @Test
    public void testGetAllTransactionPositive() {
        List<Transaction> list = List.of(t1, t2);
        t1 = dataProvider.appendTransaction(t1);
        t2 = dataProvider.appendTransaction(t2);
        Assertions.assertEquals(list, dataProvider.getAllTransaction());
    }

    @Test
    public void testGetAllTransactionNegative() {
        List<Transaction> list = List.of(t1, t2);
        t1 = dataProvider.appendTransaction(t1);
        Assertions.assertNotEquals(list, dataProvider.getAllTransaction());
    }

    @Test
    public void testGetTransactionByIdPositive() {
        t1 = dataProvider.appendTransaction(t1);
        Assertions.assertEquals(t1, dataProvider.getTransactionById(t1.getId()));
    }

    @Test
    public void testGetTransactionByIdNegative() {
        Assertions.assertEquals(new Transaction() {
        }, dataProvider.getTransactionById(1L));
    }

    @Test
    public void testAppendTransactionPositive() {
        Assertions.assertEquals(t1, dataProvider.appendTransaction(t1));
    }

    @Test
    public void testAppendTransactionNegative() {
        Assertions.assertNotEquals(t1, dataProvider.appendTransaction(t2));
    }

    @Test
    public void testDeleteTransactionPositive() {
        t1 = dataProvider.appendTransaction(t1);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.deleteTransaction(t1.getId()));
    }

    @Test
    public void testDeleteTransactionNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.deleteTransaction(t1.getId()));
    }

    @Test
    public void testUpdateTransactionPositive() {
        dataProvider.appendTransaction(t1);
        t1.setValue(500);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updateTransaction(t1));
    }

    @Test
    public void testUpdateTransactionNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updateTransaction(t1));
    }
}
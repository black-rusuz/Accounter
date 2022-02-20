package ru.sfedu.accounter.api.crud;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab1.api.AbstractDataProvider;
import ru.sfedu.accounter.lab1.model.Result;
import ru.sfedu.accounter.lab1.model.beans.Balance;
import ru.sfedu.accounter.lab1.model.beans.Income;
import ru.sfedu.accounter.lab1.model.beans.Outcome;
import ru.sfedu.accounter.lab1.model.beans.Plan;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;
import java.util.List;

public abstract class CrudTest extends SampleData {
    protected AbstractDataProvider dataProvider;

    public void cleanUp() {
        dataProvider.deleteBalance(b1.getId());
        dataProvider.deleteBalance(b2.getId());

        dataProvider.deleteIncome(i1.getId());
        dataProvider.deleteIncome(i2.getId());

        dataProvider.deleteOutcome(o1.getId());
        dataProvider.deleteOutcome(o2.getId());

        dataProvider.deletePlan(p1.getId());
        dataProvider.deletePlan(p2.getId());
    }

    @BeforeEach
    public void setUp() throws IOException {
        createObjects();
    }

    @AfterEach
    public void tearDown() {
        cleanUp();
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
        Assertions.assertEquals(new Balance(), dataProvider.getBalanceById(0));
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
        Assertions.assertEquals(result, dataProvider.deleteBalance(0));
    }

    @Test
    public void testUpdateBalancePositive() {
        b1 = dataProvider.appendBalance(b1);
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
    public void testGetAllIncomePositive() {
        List<Income> list = List.of(i1, i2);
        i1 = dataProvider.appendIncome(i1);
        i2 = dataProvider.appendIncome(i2);
        Assertions.assertEquals(list, dataProvider.getAllIncome());
    }

    @Test
    public void testGetAllIncomeNegative() {
        List<Income> list = List.of(i1, i2);
        i1 = dataProvider.appendIncome(i1);
        Assertions.assertNotEquals(list, dataProvider.getAllIncome());
    }

    @Test
    public void testGetIncomeByIdPositive() {
        i1 = dataProvider.appendIncome(i1);
        Assertions.assertEquals(i1, dataProvider.getIncomeById(i1.getId()));
    }

    @Test
    public void testGetIncomeByIdNegative() {
        Assertions.assertEquals(new Income() {}, dataProvider.getIncomeById(0));
    }

    @Test
    public void testAppendIncomePositive() {
        Assertions.assertEquals(i1, dataProvider.appendIncome(i1));
    }

    @Test
    public void testAppendIncomeNegative() {
        Assertions.assertNotEquals(i1, dataProvider.appendIncome(i2));
    }

    @Test
    public void testDeleteIncomePositive() {
        i1 = dataProvider.appendIncome(i1);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.deleteIncome(i1.getId()));
    }

    @Test
    public void testDeleteIncomeNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.deleteIncome(0));
    }

    @Test
    public void testUpdateIncomePositive() {
        i1 = dataProvider.appendIncome(i1);
        i1.setValue(500);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updateIncome(i1));
    }

    @Test
    public void testUpdateIncomeNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updateIncome(i1));
    }

    @Test
    public void testGetAllOutcomePositive() {
        List<Outcome> list = List.of(o1, o2);
        o1 = dataProvider.appendOutcome(o1);
        o2 = dataProvider.appendOutcome(o2);
        Assertions.assertEquals(list, dataProvider.getAllOutcome());
    }

    @Test
    public void testGetAllOutcomeNegative() {
        List<Outcome> list = List.of(o1, o2);
        o1 = dataProvider.appendOutcome(o1);
        Assertions.assertNotEquals(list, dataProvider.getAllOutcome());
    }

    @Test
    public void testGetOutcomeByIdPositive() {
        o1 = dataProvider.appendOutcome(o1);
        Assertions.assertEquals(o1, dataProvider.getOutcomeById(o1.getId()));
    }

    @Test
    public void testGetOutcomeByIdNegative() {
        Assertions.assertEquals(new Outcome() {}, dataProvider.getOutcomeById(0));
    }

    @Test
    public void testAppendOutcomePositive() {
        Assertions.assertEquals(o1, dataProvider.appendOutcome(o1));
    }

    @Test
    public void testAppendOutcomeNegative() {
        Assertions.assertNotEquals(o1, dataProvider.appendOutcome(o2));
    }

    @Test
    public void testDeleteOutcomePositive() {
        o1 = dataProvider.appendOutcome(o1);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.deleteOutcome(o1.getId()));
    }

    @Test
    public void testDeleteOutcomeNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.deleteOutcome(0));
    }

    @Test
    public void testUpdateOutcomePositive() {
        o1 = dataProvider.appendOutcome(o1);
        o1.setValue(500);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updateOutcome(o1));
    }

    @Test
    public void testUpdateOutcomeNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updateOutcome(o1));
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
        Assertions.assertEquals(new Plan(), dataProvider.getPlanById(0));
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
        Assertions.assertEquals(result, dataProvider.deletePlan(0));
    }

    @Test
    public void testUpdatePlanPositive() {
        p1 = dataProvider.appendPlan(p1);
        p1.setPeriod(500L);
        Result result = new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
        Assertions.assertEquals(result, dataProvider.updatePlan(p1));
    }

    @Test
    public void testUpdatePlanNegative() {
        Result result = new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        Assertions.assertEquals(result, dataProvider.updatePlan(p1));
    }
}
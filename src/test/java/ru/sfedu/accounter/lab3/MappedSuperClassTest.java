package ru.sfedu.accounter.lab3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab3.msc.bean.Income;
import ru.sfedu.accounter.lab3.msc.DataProvider;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.enums.IncomeCategory;

import javax.persistence.OptimisticLockException;

class MappedSuperClassTest {
    private final Logger log = LogManager.getLogger(this.getClass());
    private final DataProvider dataProvider = new DataProvider();
    private Income income = new Income(500, "Money", IncomeCategory.BONUS);

    @AfterEach
    void cleanUp() {
        if (dataProvider.getIncomeById(income.getId()) != null)
            dataProvider.deleteIncome(income.getId());
    }

    @Test
    void getIncomeByIdPos() {
        income = dataProvider.appendIncome(income);
        Assertions.assertEquals(income, dataProvider.getIncomeById(income.getId()));
    }

    @Test
    void getIncomeByIdNeg() {
        income = dataProvider.appendIncome(income);
        Assertions.assertNotEquals(new Income(), dataProvider.getIncomeById(income.getId()));
    }

    @Test
    void appendIncomePos() {
        Assertions.assertEquals(income, dataProvider.appendIncome(income));
    }

    @Test
    void appendIncomeNeg() {
        Assertions.assertNotEquals(new Income(), dataProvider.appendIncome(income));
    }

    @Test
    void deleteIncomePos() {
        income = dataProvider.appendIncome(income);
        Assertions.assertEquals(new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS),
                dataProvider.deleteIncome(income.getId()));
    }

    @Test
    void deleteIncomeNeg() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataProvider.deleteIncome(income.getId()));
    }

    @Test
    void updateIncomePos() {
        income = dataProvider.appendIncome(income);
        income.setName("Moar");
        dataProvider.updateIncome(income);
        Assertions.assertEquals(income, dataProvider.getIncomeById(income.getId()));
    }

    @Test
    void updateIncomeNeg() {
        income.setId(5);
        Assertions.assertThrows(
                OptimisticLockException.class,
                () -> dataProvider.updateIncome(income));
    }
}
package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.AbstractDataProvider;
import ru.sfedu.accounter.api.DataProviderJdbc;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    static Balance balance;
    static Transaction income;
    static Transaction outcome;
    static Plan plan;

    public static void main(String[] args) throws IOException {
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));
        log.info("Salam");

        balance = new Balance("21.12.2021", 500);
        income = new Income("21.12.2021", 500, "Money", balance, IncomeCategory.Bonus);
        outcome = new Outcome("21.12.2021", 500, "Money", balance, OutcomeCategory.Debt);
        plan = new Plan("21.12.2021", "Salary", "1 Month", income);

        //testXmlCrud();
        log.info("=======================================================");
        //testH2Crud();
    }

    private static void testXmlCrud() throws IOException {
        AbstractDataProvider dataProvider = new DataProviderXml();

        log.info(dataProvider.appendBalance(balance));
        log.info(dataProvider.appendTransaction(income));
        log.info(dataProvider.appendTransaction(outcome));
        log.info(dataProvider.appendPlan(plan));

        log.info(dataProvider.getBalanceById(balance.getId()));
        log.info(dataProvider.getTransactionById(income.getId()));
        log.info(dataProvider.getTransactionById(outcome.getId()));
        log.info(dataProvider.getPlanById(plan.getId()));

        Balance newBalance = dataProvider.getBalanceById(balance.getId());
        Transaction newIncome = dataProvider.getTransactionById(income.getId());
        Transaction newOutcome = dataProvider.getTransactionById(outcome.getId());
        Plan newPlan = dataProvider.getPlanById(plan.getId());

        newBalance.setValue(1000);
        newIncome.setValue(1000);
        newOutcome.setValue(1000);
        newPlan.setTransaction(outcome);

        log.info(dataProvider.updateBalance(newBalance));
        log.info(dataProvider.updateTransaction(newIncome));
        log.info(dataProvider.updateTransaction(newOutcome));
        log.info(dataProvider.updatePlan(newPlan));

        log.info(dataProvider.deleteBalance(balance.getId()));
        log.info(dataProvider.deleteTransaction(income.getId()));
        log.info(dataProvider.deleteTransaction(outcome.getId()));
        log.info(dataProvider.deletePlan(plan.getId()));
    }

    private static void testH2Crud() throws IOException, SQLException {
        AbstractDataProvider dataProvider = new DataProviderJdbc();

        log.info(dataProvider.appendBalance(balance));
        log.info(dataProvider.appendTransaction(income));
        log.info(dataProvider.appendTransaction(outcome));
        log.info(dataProvider.appendPlan(plan));

        log.info(dataProvider.getBalanceById(balance.getId()));
        log.info(dataProvider.getTransactionById(income.getId()));
        log.info(dataProvider.getTransactionById(outcome.getId()));
        log.info(dataProvider.getPlanById(plan.getId()));

        Balance newBalance = dataProvider.getBalanceById(balance.getId());
        Transaction newIncome = dataProvider.getTransactionById(income.getId());
        Transaction newOutcome = dataProvider.getTransactionById(outcome.getId());
        Plan newPlan = dataProvider.getPlanById(plan.getId());

        newBalance.setValue(1000);
        newIncome.setValue(1000);
        newOutcome.setValue(1000);
        newPlan.setTransaction(newOutcome);

        log.info(dataProvider.updateBalance(newBalance));
        log.info(dataProvider.updateTransaction(newIncome));
        log.info(dataProvider.updateTransaction(newOutcome));
        log.info(dataProvider.updatePlan(newPlan));

        log.info(dataProvider.deleteBalance(balance.getId()));
        log.info(dataProvider.deleteTransaction(income.getId()));
        log.info(dataProvider.deleteTransaction(outcome.getId()));
        log.info(dataProvider.deletePlan(plan.getId()));
    }
}

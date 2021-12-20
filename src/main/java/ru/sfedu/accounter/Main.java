package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.AbstractDataProvider;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.model.enums.*;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info("Salam");
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));

        AbstractDataProvider dataProviderXml = new DataProviderXml();

        Balance balance = new Balance("21.12.2021", 500);
        Income income = new Income("21.12.2021", 500, "Money", balance, IncomeCategory.Bonus);
        Outcome outcome = new Outcome("21.12.2021", 500, "Money", balance, OutcomeCategory.Debt);

        dataProviderXml.appendBalance(balance);
        dataProviderXml.appendTransaction(income);
        dataProviderXml.appendTransaction(outcome);

        log.info(dataProviderXml.getBalanceById(balance.getId()));
        log.info(dataProviderXml.getTransactionById(income.getId()));
        log.info(dataProviderXml.getTransactionById(outcome.getId()));
    }
}

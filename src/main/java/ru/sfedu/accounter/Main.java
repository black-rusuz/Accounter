package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.IncomeCategory;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info("Salam");
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));

        Balance balance = new Balance("10.12.2021 21:30", 1000);
        Income income = new Income("10.12.2021 21:45", 500, "Found on street", balance, IncomeCategory.Bonus);
        Plan plan = new Plan("10.12.2021 21:45", "Lucky times", "1 day", income);
        
        DataProviderXml dataProviderXml = new DataProviderXml();
        dataProviderXml.appendBalance(balance);
        dataProviderXml.appendTransaction(income);
        dataProviderXml.appendPlan(plan);
    }
}

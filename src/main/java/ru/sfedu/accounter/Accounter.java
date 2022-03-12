package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.IDataProvider;
import ru.sfedu.accounter.api.DataProviderCsv;
import ru.sfedu.accounter.api.DataProviderJdbc;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;
import java.util.Locale;

public class Accounter {
    private static final Logger log = LogManager.getLogger(Accounter.class);
    private static IDataProvider dataProvider;

    /**
     * Main function for CLI
     *
     * @param args arguments
     *             <p>
     *             Сheck README.md
     */
    public static void main(String[] args) throws IOException {
        checkArgumentsCount(args);
        dataProvider = getDataProvider(args[0]);
        if (dataProvider.getAllBalance().isEmpty()) loadSampleData();

        switch (args[1].toUpperCase(Locale.ROOT)) {
            case (Constants.CLI_ARG_MANAGE_BALANCE) -> manageBalance(args);
            case (Constants.CLI_ARG_CALCULATE_BALANCE) -> dataProvider.calculateBalance();
            case (Constants.CLI_ARG_DISPLAY_INCOMES_AND_OUTCOMES) -> dataProvider.displayIncomesAndOutcomes();
            case (Constants.CLI_ARG_REPEAT_TRANSACTION) -> repeatTransaction(args);
            case (Constants.CLI_ARG_MAKE_PLAN_BASED_ON_TRANSACTION) -> makePlanBasedOnTransaction(args);
            case (Constants.CLI_ARG_MANAGE_PLANS) -> managePlans(args);
            case (Constants.CLI_ARG_DISPLAY_PLANS) -> dataProvider.displayPlans();
            case (Constants.CLI_ARG_EXECUTE_PLAN_NOW) -> executePlanNow(args);
            default -> log.error(Constants.CLI_ERROR_INVALID_ARGUMENT);
        }
    }

    /**
     * Exiting program if number of arguments less than needed
     *
     * @param args from CLI (main function)
     */
    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.CLI_ERROR_TOO_FEW_ARGUMENTS);
            System.exit(0);
        }
    }

    /**
     * Function for getting DataProvider from source type
     *
     * @param dataProviderSource 'XML', 'CSV' or 'JDBC'
     * @return IDataProvider chosen inheritor
     */
    private static IDataProvider getDataProvider(String dataProviderSource) throws IOException {
        if (dataProviderSource.equalsIgnoreCase(Constants.CLI_ARG_XML)) return new DataProviderXml();
        else if (dataProviderSource.equalsIgnoreCase(Constants.CLI_ARG_CSV)) return new DataProviderCsv();
        else if (dataProviderSource.equalsIgnoreCase(Constants.CLI_ARG_JDBC)) return new DataProviderJdbc();
        else {
            log.error(Constants.CLI_ERROR_INVALID_DATA_PROVIDER);
            System.exit(0);
            return null;
        }
    }

    /**
     * Creates sample data objects for selected DataProvider
     */
    private static void loadSampleData() {
        SampleData sampleData = new SampleData();
        sampleData.createObjects();

        dataProvider.appendBalance(sampleData.b1);
        dataProvider.appendBalance(sampleData.b2);

        dataProvider.appendIncome(sampleData.i1);
        dataProvider.appendIncome(sampleData.i2);

        dataProvider.appendOutcome(sampleData.o1);
        dataProvider.appendOutcome(sampleData.o2);

        dataProvider.appendPlan(sampleData.p1);
        dataProvider.appendPlan(sampleData.p2);
    }


    // Helpers
    private static void manageBalance(String[] args) {
        switch (args.length) {
            case (2) -> dataProvider.manageBalance("", 0);
            case (4) -> dataProvider.manageBalance(args[2], Long.parseLong(args[3]));
            default -> log.error(Constants.CLI_ERROR_INVALID_NUMBER_OF_ARGUMENTS);
        }
    }

    private static void repeatTransaction(String[] args) {
        if (args.length == 3) dataProvider.repeatTransaction(Long.parseLong(args[2]));
        else log.error(Constants.CLI_ERROR_INVALID_NUMBER_OF_ARGUMENTS);
    }

    private static void makePlanBasedOnTransaction(String[] args) {
        if (args.length == 3) dataProvider.makePlanBasedOnTransaction(Long.parseLong(args[2]));
        else log.error(Constants.CLI_ERROR_INVALID_NUMBER_OF_ARGUMENTS);
    }

    private static void managePlans(String[] args) {
        switch (args.length) {
            case (2) -> dataProvider.managePlans(0, false);
            case (3) -> dataProvider.managePlans(Long.parseLong(args[2]), false);
            case (4) -> dataProvider.managePlans(Long.parseLong(args[2]), Boolean.parseBoolean(args[3]));
            default -> log.error(Constants.CLI_ERROR_INVALID_NUMBER_OF_ARGUMENTS);
        }
    }

    private static void executePlanNow(String[] args) {
        if (args.length == 3) dataProvider.executePlanNow(Long.parseLong(args[2]));
        else log.error(Constants.CLI_ERROR_INVALID_NUMBER_OF_ARGUMENTS);
    }
}

package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.IDataProvider;
import ru.sfedu.accounter.api.DataProviderCsv;
import ru.sfedu.accounter.api.DataProviderJdbc;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;

public class Accounter {
    private static final Logger log = LogManager.getLogger(Accounter.class);
    private static IDataProvider dataProvider;

    /**
     * Main function for CLI
     *
     * @param args arguments (check README.md)
     */
    public static void main(String[] args) throws IOException {
        checkArgumentsCount(args);
        dataProvider = getDataProvider(args[0]);
        if (dataProvider.getAllBalance().size() <= 0)
            loadSampleData();

        if (args[1].equalsIgnoreCase(Constants.MANAGE_BALANCE))
            if (args.length == 2)
                dataProvider.manageBalance("", 0);
            else if (args.length == 4)
                dataProvider.manageBalance(args[2], Long.parseLong(args[3]));
            else
                log.error(Constants.CLI_ERROR_INVALID_ARGUMENTS);

        else if (args[1].equalsIgnoreCase(Constants.MANAGE_PLANS))
            if (args.length == 2)
                dataProvider.managePlans(0, false);
            else if (args.length == 4)
                dataProvider.managePlans(Long.parseLong(args[2]), Boolean.parseBoolean(args[3]));
            else
                log.error(Constants.CLI_ERROR_INVALID_ARGUMENTS);

        else log.error(Constants.CLI_ERROR_INVALID_ARGUMENTS);
    }

    /**
     * Exiting program if number of arguments less than needed
     *
     * @param args from CLI (main function)
     */
    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.CLI_ERROR_FEW_ARGUMENTS);
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
        if (dataProviderSource.equalsIgnoreCase(Constants.XML))
            return new DataProviderXml();
        else if (dataProviderSource.equalsIgnoreCase(Constants.CSV))
            return new DataProviderCsv();
        else if (dataProviderSource.equalsIgnoreCase(Constants.JDBC))
            return new DataProviderJdbc();
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
}

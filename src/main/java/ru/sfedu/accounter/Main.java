package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.AbstractDataProvider;
import ru.sfedu.accounter.api.DataProviderCsv;
import ru.sfedu.accounter.api.DataProviderJdbc;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        checkArgumentsCount(args);
        AbstractDataProvider dataProvider = getDataProvider(args[0]);
        SampleData sampleData = new SampleData();
        sampleData.createObjects();

        dataProvider.appendBalance(sampleData.b1);
        dataProvider.appendBalance(sampleData.b2);
        dataProvider.appendPlan(sampleData.p1);
        dataProvider.appendPlan(sampleData.p2);

        dataProvider.appendTransaction(sampleData.t1);
        dataProvider.appendTransaction(sampleData.t2);
        dataProvider.appendTransaction(sampleData.t3);
        dataProvider.appendTransaction(sampleData.t4);

        if (args[1].equalsIgnoreCase(Constants.MANAGEBALANCE))
            if (args.length == 2) dataProvider.manageBalance("", 0);
            else dataProvider.manageBalance(args[2], Long.parseLong(args[3]));
        if (args[1].equalsIgnoreCase(Constants.MANAGEPLANS))
            if (args.length == 2) dataProvider.managePlans(0, false);
            else dataProvider.manageBalance(args[2], Long.parseLong(args[3]));
    }

    private static AbstractDataProvider getDataProvider(String dataProviderSource) throws IOException {
        if (dataProviderSource.equalsIgnoreCase(Constants.XML)) return new DataProviderXml();
        else if (dataProviderSource.equalsIgnoreCase(Constants.CSV)) return new DataProviderCsv();
        else if (dataProviderSource.equalsIgnoreCase(Constants.JDBC)) return new DataProviderJdbc();
        else {
            log.error(Constants.CLI_ERROR_INVALID_DATA_PROVIDER);
            System.exit(0);
            return null;
        }
    }

    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.CLI_ERROR_FEW_ARGUMENTS);
            System.exit(0);
        }
    }
}

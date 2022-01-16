package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.api.AbstractDataProvider;
import ru.sfedu.accounter.api.DataProviderCsv;
import ru.sfedu.accounter.api.DataProviderJdbc;
import ru.sfedu.accounter.api.DataProviderXml;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    // TODO: Пофиксить CLI
    public static void main(String[] args) throws SQLException, IOException {
        checkArgumentsCount(args);
        AbstractDataProvider dataProvider = getDataProvider(args[0]);
        switch (args[2].toUpperCase()) {
            case (Constants.BALANCE) -> dataProvider.manageBalance(args[3], Long.parseLong(args[4]));
            case (Constants.PLAN) -> {
                if (args[3] != null) {
                    dataProvider.managePlans(Long.parseLong(args[3]));
                    break;
                }
                dataProvider.managePlans();
            }
            default -> {
                log.error(Constants.CLI_INVALID_ARGUMENT);
                System.exit(0);
            }
        }
    }

    private static AbstractDataProvider getDataProvider(String dataProviderSource) throws IOException, SQLException {
        switch (dataProviderSource.toUpperCase()) {
            case (Constants.XML) -> {
                return new DataProviderXml();
            }
            case (Constants.JDBC) -> {
                return new DataProviderJdbc();
            }
            case (Constants.CSV) -> {
                return new DataProviderCsv();
            }
            default -> {
                log.error(Constants.CLI_INVALID_DATA_PROVIDER);
                System.exit(0);
                return null;
            }
        }
    }

    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.CLI_FEW_ARGUMENTS);
            System.exit(0);
        }
    }
}

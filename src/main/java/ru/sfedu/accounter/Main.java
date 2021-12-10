package ru.sfedu.accounter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.sfedu.accounter.api.DataProviderXml;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info("Salam");
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));

        DataProviderXml dataProviderXml = new DataProviderXml();
        dataProviderXml.manageBalance();
        dataProviderXml.managePlans();
    }
}

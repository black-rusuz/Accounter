package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.lab2.api.DataProviderHibernate;
import ru.sfedu.accounter.lab2.model.Beeean;
import ru.sfedu.accounter.lab2.model.Neeested;

import java.util.Date;

public class Lab2Test {
    private static final Logger log = LogManager.getLogger(Lab2Test.class);
    DataProviderHibernate dataProvider = new DataProviderHibernate();

    @Test
    public void test() {
        Beeean beeean = new Beeean(1,
                "New",
                new Date(2022, 2, 28),
                true,
                new Neeested("desc", "https://vk.com/"));
        log.info(dataProvider.appendEntity(beeean));
        log.info(dataProvider.getAllEntity());
        log.info(dataProvider.getEntityById(1));
        log.info(dataProvider.deleteEntity(1));
        beeean.setId(5);
        beeean.setName("Newest");
        log.info(dataProvider.updateEntity(beeean));
    }
}

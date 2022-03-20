package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.lab2.api.DataProviderHibernate;
import ru.sfedu.accounter.lab2.model.Bean;
import ru.sfedu.accounter.lab2.model.Nested;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Lab2Test {
    private static final Logger log = LogManager.getLogger(Lab2Test.class);
    DataProviderHibernate dataProvider = new DataProviderHibernate();
    Bean bean = new Bean(1,
            "New",
            new Date(2022, Calendar.MARCH, 12),
            true,
            new Nested("desc", "https://vk.com/"));

    @AfterEach
    public void cleanUp() {
        dataProvider.deleteEntity(bean.getId());
    }

    @Test
    public void testAppendPos() {
        Assertions.assertEquals(bean, dataProvider.appendEntity(bean));
    }

    @Test
    public void testAppendNeg() {
        Assertions.assertNotEquals(new Bean(), dataProvider.appendEntity(bean));
    }

    @Test
    public void testGetAllPos() {
        Bean b1 = dataProvider.appendEntity(bean);
        Bean b2 = dataProvider.appendEntity(bean);
        Assertions.assertEquals(List.of(b1, b2), dataProvider.getAllEntity());
        dataProvider.deleteEntity(b2.getId());
    }

    @Test
    public void testGetAllNeg() {
        Bean b1 = dataProvider.appendEntity(bean);
        Assertions.assertNotEquals(List.of(b1), dataProvider.getAllEntity());
    }

    @Test
    public void test3() {
        log.info(dataProvider.getEntityById(1));
    }

    @Test
    public void test4() {
        log.info(dataProvider.deleteEntity(1));
        bean.setId(5);
        bean.setName("Newest");
    }

    @Test
    public void test5() {
        log.info(dataProvider.appendEntity(bean));
        bean.setId(5);
        bean.setName("Newest");
        log.info(dataProvider.updateEntity(bean));
    }
}

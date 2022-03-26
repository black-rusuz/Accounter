package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab2.api.DataProviderHibernate;
import ru.sfedu.accounter.lab2.model.Bean;
import ru.sfedu.accounter.lab2.model.Nested;
import ru.sfedu.accounter.model.Result;

import javax.persistence.OptimisticLockException;
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
        if (dataProvider.getEntityById(bean.getId()) != null)
            dataProvider.deleteEntity(bean.getId());
    }

//    @Test
//    public void testGetAllPos() {
//        log.info(bean);
//        Bean b1 = dataProvider.appendEntity(bean);
//        log.info(b1);
//        Bean b2 = dataProvider.appendEntity(b1);
//        log.info(b2);
//        Assertions.assertEquals(List.of(dataProvider.getEntityById(b1.getId()), dataProvider.getEntityById(b2.getId())),
//                dataProvider.getAllEntity());
//        dataProvider.deleteEntity(b2.getId());
//    }
//
//    @Test
//    public void testGetAllNeg() {
//        Bean b1 = dataProvider.appendEntity(bean);
//        Assertions.assertNotEquals(List.of(b1), dataProvider.getAllEntity());
//    }

    @Test
    public void testGetPos() {
        bean = dataProvider.appendEntity(bean);
        Assertions.assertEquals(bean, dataProvider.getEntityById(bean.getId()));
    }

    @Test
    public void testGetNeg() {
        bean = dataProvider.appendEntity(bean);
        Assertions.assertNotEquals(new Bean(), dataProvider.getEntityById(bean.getId()));
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
    public void testDelPos() {
        bean = dataProvider.appendEntity(bean);
        Assertions.assertEquals(new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS),
                dataProvider.deleteEntity(bean.getId()));
    }

    @Test
    public void testDelNeg() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataProvider.deleteEntity(bean.getId()));
    }

    @Test
    public void testUpdatePos() {
        bean = dataProvider.appendEntity(bean);
        bean.setName("Newest");
        dataProvider.updateEntity(bean);
        Assertions.assertEquals(bean, dataProvider.getEntityById(bean.getId()));
    }

    @Test
    public void testUpdateNeg() {
        bean = dataProvider.appendEntity(bean);
        bean.setId(5);
        Assertions.assertThrows(
                OptimisticLockException.class,
                () -> dataProvider.updateEntity(bean));
    }
}

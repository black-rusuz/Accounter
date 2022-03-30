package ru.sfedu.accounter.hibernate.lab4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.hibernate.lab4.map.Bean;
import ru.sfedu.accounter.hibernate.lab4.map.DataProvider;
import ru.sfedu.accounter.model.Result;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public class MapTest {
    DataProvider dataProvider = new DataProvider();
    Map<String, String> hm = new HashMap<>();
    Bean bean = new Bean(1, hm);

    @AfterEach
    public void cleanUp() {
        if (dataProvider.getBeanById(bean.getId()) != null)
            dataProvider.deleteBean(bean.getId());
    }

    @Test
    void appendPos() {
        Assertions.assertEquals(bean, dataProvider.appendBean(bean));
    }

    @Test
    void appendNeg() {
        Assertions.assertNotEquals(new Bean(), dataProvider.appendBean(bean));
    }

    @Test
    public void testGetPos() {
        bean = dataProvider.appendBean(bean);
        Assertions.assertEquals(bean, dataProvider.getBeanById(bean.getId()));
    }

    @Test
    public void testGetNeg() {
        bean = dataProvider.appendBean(bean);
        Assertions.assertNotEquals(new ru.sfedu.accounter.hibernate.lab2.model.Bean(), dataProvider.getBeanById(bean.getId()));
    }

    @Test
    public void testAppendPos() {
        Assertions.assertEquals(bean, dataProvider.appendBean(bean));
    }

    @Test
    public void testAppendNeg() {
        Assertions.assertNotEquals(new ru.sfedu.accounter.hibernate.lab2.model.Bean(), dataProvider.appendBean(bean));
    }

    @Test
    public void testDelPos() {
        bean = dataProvider.appendBean(bean);
        Assertions.assertEquals(new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS),
                dataProvider.deleteBean(bean.getId()));
    }

    @Test
    public void testDelNeg() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataProvider.deleteBean(bean.getId()));
    }

    @Test
    public void testUpdatePos() {
        bean = dataProvider.appendBean(bean);
        bean.setStrings(new HashMap<>());
        dataProvider.updateBean(bean);
        Assertions.assertEquals(bean, dataProvider.getBeanById(bean.getId()));
    }

    @Test
    public void testUpdateNeg() {
        bean = dataProvider.appendBean(bean);
        bean.setStrings(new HashMap<>());
        Assertions.assertEquals(new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS),
                dataProvider.updateBean(bean));
    }
}

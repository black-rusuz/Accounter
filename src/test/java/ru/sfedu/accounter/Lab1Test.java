package ru.sfedu.accounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.lab1.DataProviderHibernate;
import ru.sfedu.accounter.utils.SampleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lab1Test extends SampleData {
    private static final Logger log = LogManager.getLogger(Lab1Test.class);
    DataProviderHibernate dataProvider = new DataProviderHibernate();

    @Test
    public void test() {
        dataProvider.getSchemas();
        dataProvider.getTables();
        dataProvider.getUsers();
        dataProvider.getConstants();
    }

    @Test
    public void getSchemasTestPos() {
        List list = List.of("INFORMATION_SCHEMA", "PUBLIC");
        Assertions.assertEquals(list, dataProvider.getSchemas());
    }

    @Test
    public void getSchemasTestNeg() {
        HashMap hm1 = new HashMap();
        hm1.put("SCHEMA_NAME", "INFORMATION_SCHEMA");
        Assertions.assertNotEquals(List.of(hm1), dataProvider.getSchemas());
    }

    @Test
    public void getTablesTestPos() {
        HashMap hm1 = new HashMap();
        hm1.put("TABLE_NAME", "BEAN");
        hm1.put("TABLE_SCHEMA", "PUBLIC");
        Assertions.assertEquals(List.of(hm1), dataProvider.getTables());
    }

    @Test
    public void getTablesTestNeg() {
        HashMap hm1 = new HashMap();
        hm1.put("TABLE_NAME", "BALANCE");
        hm1.put("TABLE_SCHEMA", "PUBLIC");
        HashMap hm2 = new HashMap();
        hm2.put("TABLE_NAME", "INCOME");
        hm2.put("TABLE_SCHEMA", "PUBLIC");
        HashMap hm3 = new HashMap();
        hm3.put("TABLE_NAME", "OUTCOME");
        hm3.put("TABLE_SCHEMA", "PUBLIC");
        Assertions.assertNotEquals(List.of(hm1, hm2, hm3), dataProvider.getTables());
    }

    @Test
    public void getUsersTestPos() {
        HashMap hm1 = new HashMap();
        hm1.put("IS_ADMIN", true);
        hm1.put("REMARKS", null);
        hm1.put("USER_NAME", "SA");
        Assertions.assertEquals(List.of(hm1), dataProvider.getUsers());
    }

    @Test
    public void getUsersTestNeg() {
        HashMap hm1 = new HashMap();
        hm1.put("IS_ADMIN", false);
        hm1.put("REMARKS", "wat?");
        hm1.put("USER_NAME", "SAlam aleykum");
        Assertions.assertNotEquals(List.of(hm1), dataProvider.getUsers());
    }

    @Test
    public void getConstantsTestPos() {
        Assertions.assertEquals(new ArrayList(), dataProvider.getConstants());
    }

    @Test
    public void getConstantsTestNeg() {
        Assertions.assertNotEquals(List.of("notes"), dataProvider.getConstants());
    }
}

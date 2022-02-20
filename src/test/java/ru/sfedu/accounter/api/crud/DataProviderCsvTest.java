package ru.sfedu.accounter.api.crud;

import org.junit.jupiter.api.BeforeEach;
import ru.sfedu.accounter.lab1.api.DataProviderCsv;

import java.io.IOException;

public class DataProviderCsvTest extends CrudTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        dataProvider = new DataProviderCsv();
        super.setUp();
    }
}

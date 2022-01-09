package ru.sfedu.accounter.api;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.sql.SQLException;

public class DataProviderCsvTest extends AbstractDataProviderTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException, SQLException {
        super.setUp();
        dataProvider = new DataProviderCsv();
    }
}

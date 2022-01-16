package ru.sfedu.accounter.api;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.sql.SQLException;

public class DataProviderXmlTest extends IDataProviderTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException, SQLException {
        super.setUp();
        dataProvider = new DataProviderXml();
    }
}

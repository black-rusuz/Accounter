package ru.sfedu.accounter.api;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class DataProviderXmlTest extends IDataProviderTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        super.setUp();
        dataProvider = new DataProviderXml();
    }
}

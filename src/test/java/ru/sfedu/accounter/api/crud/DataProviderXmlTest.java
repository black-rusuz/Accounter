package ru.sfedu.accounter.api.crud;

import org.junit.jupiter.api.BeforeEach;
import ru.sfedu.accounter.api.DataProviderXml;

import java.io.IOException;

public class DataProviderXmlTest extends CrudTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        dataProvider = new DataProviderXml();
        super.setUp();
    }
}

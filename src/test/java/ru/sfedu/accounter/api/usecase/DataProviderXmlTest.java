package ru.sfedu.accounter.api.usecase;

import org.junit.jupiter.api.BeforeEach;
import ru.sfedu.accounter.lab1.api.DataProviderXml;

import java.io.IOException;

public class DataProviderXmlTest extends UseCaseTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        dataProvider = new DataProviderXml();
        super.setUp();
    }
}

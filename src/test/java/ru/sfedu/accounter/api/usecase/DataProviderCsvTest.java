package ru.sfedu.accounter.api.usecase;

import org.junit.jupiter.api.BeforeEach;
import ru.sfedu.accounter.api.DataProviderCsv;

import java.io.IOException;

public class DataProviderCsvTest extends UseCaseTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        dataProvider = new DataProviderCsv();
        super.setUp();
    }
}

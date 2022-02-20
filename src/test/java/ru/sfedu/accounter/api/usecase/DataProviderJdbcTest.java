package ru.sfedu.accounter.api.usecase;

import org.junit.jupiter.api.BeforeEach;
import ru.sfedu.accounter.lab1.api.DataProviderJdbc;

import java.io.IOException;

public class DataProviderJdbcTest extends UseCaseTest {

    @Override
    @BeforeEach
    public void setUp() throws IOException {
        dataProvider = new DataProviderJdbc();
        super.setUp();
    }
}

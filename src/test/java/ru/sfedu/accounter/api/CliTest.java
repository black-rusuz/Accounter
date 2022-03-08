package ru.sfedu.accounter.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Accounter;

import java.io.IOException;

public class CliTest {

    @Test
    public void testMainForLogsXml() throws IOException {
        Accounter.main(new String[]{"XML", "MANAGEBALANCE"});
        Accounter.main(new String[]{"XML", "MANAGEBALANCE", "REPEAT", "21"});
        Accounter.main(new String[]{"XML", "MANAGEBALANCE", "PLAN", "22"});
        Accounter.main(new String[]{"XML", "MANAGEPLANS"});
        Accounter.main(new String[]{"XML", "MANAGEPLANS", "31", "TRUE"});
        Accounter.main(new String[]{"XML", "MANAGEPLANS", "32", "FALSE"});
    }

    @Test
    public void testMainForLogsCsv() throws IOException {
        Accounter.main(new String[]{"CSV", "MANAGEBALANCE"});
        Accounter.main(new String[]{"CSV", "MANAGEBALANCE", "REPEAT", "21"});
        Accounter.main(new String[]{"CSV", "MANAGEBALANCE", "PLAN", "22"});
        Accounter.main(new String[]{"CSV", "MANAGEPLANS"});
        Accounter.main(new String[]{"CSV", "MANAGEPLANS", "31", "TRUE"});
        Accounter.main(new String[]{"CSV", "MANAGEPLANS", "32", "FALSE"});
    }

    @Test
    public void testMainForLogsJdbc() throws IOException {
        Accounter.main(new String[]{"JDBC", "MANAGEBALANCE"});
        Accounter.main(new String[]{"JDBC", "MANAGEBALANCE", "REPEAT", "21"});
        Accounter.main(new String[]{"JDBC", "MANAGEBALANCE", "PLAN", "22"});
        Accounter.main(new String[]{"JDBC", "MANAGEPLANS"});
        Accounter.main(new String[]{"JDBC", "MANAGEPLANS", "31", "TRUE"});
        Accounter.main(new String[]{"JDBC", "MANAGEPLANS", "32", "FALSE"});
    }
}

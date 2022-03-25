package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Accounter;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;
import java.util.List;

public class MainTest extends SampleData {
    private static final Logger log = LogManager.getLogger(MainTest.class);

    @Test
    public void test() {
        testMainForLogs("XML");
        testMainForLogs("CSV");
//        testMainForLogs("JDBC");
    }

    public void testMainForLogs(String dp) {
        List<String> list = List.of(
                "MANAGEBALANCE",
                "MANAGEBALANCE REPEAT 21",
                "MANAGEBALANCE PLAN 22",

                "CALCULATEBALANCE",
                "DISPLAYINCOMESANDOUTCOMES",
                "REPEATTRANSACTION 21",
                "MAKEPLANBASEDONTRANSACTION 21",

                "MANAGEPLANS",
                "MANAGEPLANS 31",
                "MANAGEPLANS 31 TRUE",
                "MANAGEPLANS 32 FALSE",

                "DISPLAYPLANS",
                "EXECUTEPLANNOW 31"
        );
        
        list.forEach(s -> {
            try {
                Accounter.main(stringToArgs(dp, s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    private String[] stringToArgs(String dp, String args) {
        return String.format("%s %s", dp, args).split(" ");
    }
}

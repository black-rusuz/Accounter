package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.Accounter;
import ru.sfedu.accounter.utils.SampleData;

import java.io.IOException;

public class MainTest extends SampleData {
    private static final Logger log = LogManager.getLogger(MainTest.class);

    @Test
    public void test() throws IOException {
//        testMainForLogs("XML");
//        testMainForLogs("CSV");
//        testMainForLogs("JDBC");
    }

    public void testMainForLogs(String dp) throws IOException {
        Accounter.main(new String[]{dp, "MANAGEBALANCE"});
        Accounter.main(new String[]{dp, "MANAGEBALANCE", "REPEAT", "21"});
        Accounter.main(new String[]{dp, "MANAGEBALANCE", "PLAN", "22"});

        Accounter.main(new String[]{dp, "CALCULATEBALANCE"});
        Accounter.main(new String[]{dp, "DISPLAYINCOMESANDOUTCOMES"});
        Accounter.main(new String[]{dp, "REPEATTRANSACTION", "21"});
        Accounter.main(new String[]{dp, "MAKEPLANBASEDONTRANSACTION", "21"});

        Accounter.main(new String[]{dp, "MANAGEPLANS"});
        Accounter.main(new String[]{dp, "MANAGEPLANS", "31"});
        Accounter.main(new String[]{dp, "MANAGEPLANS", "31", "TRUE"});
        Accounter.main(new String[]{dp, "MANAGEPLANS", "32", "FALSE"});

        Accounter.main(new String[]{dp, "DISPLAYPLANS"});
        Accounter.main(new String[]{dp, "EXECUTEPLANNOW", "31"});
    }
}

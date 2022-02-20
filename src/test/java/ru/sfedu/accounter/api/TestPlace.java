package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.utils.HibernateUtil;
import ru.sfedu.accounter.utils.SampleData;

public class TestPlace extends SampleData {
    private static final Logger log = LogManager.getLogger(TestPlace.class);

    @Test
    public void test() {
        createObjects();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(b1);
        session.close();
    }
}

package ru.sfedu.accounter.hibernate.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.accounter.utils.HibernateUtil;

import java.util.List;

public class DataProviderHibernate {
    private static final Logger log = LogManager.getLogger(DataProviderHibernate.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List getSchemas() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery sql = session.createNativeQuery("SHOW SCHEMAS");
        List list = sql.list();
        log.info(list);

        session.close();
        return list;
    }

    public List getTables() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery sql = session.createNativeQuery("SHOW TABLES");
        sql.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = sql.list();
        log.info(list);

        session.close();
        return list;
    }

    public List getUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery sql = session.createNativeQuery("SELECT * FROM INFORMATION_SCHEMA.USERS");
        sql.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = sql.list();
        log.info(list);

        session.close();
        return list;
    }

    public List getConstants() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery sql = session.createNativeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTANTS");
        sql.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = sql.list();
        log.info(list);

        session.close();
        return list;
    }
}

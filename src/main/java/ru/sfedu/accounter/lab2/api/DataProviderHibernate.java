package ru.sfedu.accounter.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab2.model.Beeean;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.utils.HibernateUtil;

import java.util.List;

public class DataProviderHibernate {
    //TODO: Убрать после наследования
    private static final Logger log = LogManager.getLogger(DataProviderHibernate.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Beeean> getAllEntity() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createCriteria(Beeean.class).list();
        session.close();
        return list;
    }

    public Beeean getEntityById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Beeean beeean = session.get(Beeean.class, id);
        session.close();
        return beeean;
    }

    public Beeean appendEntity(Beeean beeean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(beeean);
        session.getTransaction().commit();
        session.close();
        return beeean;
    }

    public Result deleteEntity(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateEntity(Beeean beeean) {
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

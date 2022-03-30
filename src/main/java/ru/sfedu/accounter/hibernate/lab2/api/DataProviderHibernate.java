package ru.sfedu.accounter.hibernate.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.hibernate.lab2.model.Bean;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.utils.HibernateUtil;

public class DataProviderHibernate {
    private static final Logger log = LogManager.getLogger(DataProviderHibernate.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Bean getEntityById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bean bean = session.get(Bean.class, id);
        session.close();
        return bean;
    }

    public Bean appendEntity(Bean bean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        bean.setId((Long) session.save(bean));
        session.getTransaction().commit();
        session.close();
        return bean;
    }

    public Result deleteEntity(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(getEntityById(id));
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateEntity(Bean bean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(bean);
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

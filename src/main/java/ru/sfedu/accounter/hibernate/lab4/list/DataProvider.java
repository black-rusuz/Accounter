package ru.sfedu.accounter.hibernate.lab4.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;

public class DataProvider {
    private final Logger log = LogManager.getLogger(this.getClass());
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Bean getBeanById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bean bean = session.get(Bean.class, id);
        session.close();
        return bean;
    }

    public Bean appendBean(Bean bean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        bean.setId((Long) session.save(bean));
        session.getTransaction().commit();
        session.close();
        return bean;
    }

    public Result deleteBean(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(getBeanById(id));
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateBean(Bean bean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(bean);
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}


package ru.sfedu.accounter.hibernate.lab3.st;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.hibernate.lab2.api.DataProviderHibernate;
import ru.sfedu.accounter.hibernate.lab3.st.bean.Income;
import ru.sfedu.accounter.model.Result;

public class DataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderHibernate.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Income getIncomeById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Income income = session.get(Income.class, id);
        session.close();
        return income;
    }

    public Income appendIncome(Income income) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        income.setId((Long) session.save(income));
        session.getTransaction().commit();
        session.close();
        return income;
    }

    public Result deleteIncome(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(getIncomeById(id));
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateIncome(Income income) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(income);
        session.getTransaction().commit();
        session.close();
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}


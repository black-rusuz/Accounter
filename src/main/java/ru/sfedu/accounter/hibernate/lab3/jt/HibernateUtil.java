package ru.sfedu.accounter.hibernate.lab3.jt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.hibernate.lab3.jt.bean.Income;
import ru.sfedu.accounter.hibernate.lab3.jt.bean.Transaction;

import java.io.File;
import java.util.Arrays;

public class HibernateUtil {
    private static final Logger log = LogManager.getLogger(ru.sfedu.accounter.utils.HibernateUtil.class);
    private static SessionFactory sessionFactory;
    private static MetadataSources metadataSources;

    /**
     * Creates SessionFactory
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(getConfiguration().getProperties()).build();
            metadataSources = new MetadataSources(serviceRegistry);
            addAnnotatedClass(Transaction.class, Income.class);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Creates configuration for SessionFactory using system variable from CLI (if specified)
     *
     * @return Configuration
     */
    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration().configure();
        if (System.getProperty(Constants.HIBERNATE_VARIABLE) != null) {
            File file = new File(System.getProperty(Constants.HIBERNATE_VARIABLE));
            if (file.exists())
                configuration = new Configuration().configure(file);
            else
                log.error("Your Hibernate configuration file not found. Default loaded.");
        }
        return configuration;
    }

    private static void addAnnotatedClass(Class... classes) {
        Arrays.stream(classes).forEach(c -> metadataSources.addAnnotatedClass(c));
    }
}

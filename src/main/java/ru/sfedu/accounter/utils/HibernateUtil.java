package ru.sfedu.accounter.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab1.model.beans.Balance;

import java.io.File;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static final Logger log = LogManager.getLogger(HibernateUtil.class);

    /**
     * Creates SessionFactory
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(getConfiguration().getProperties()).build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(Balance.class);
            // TODO: этачо?
            // metadataSources.addResource("named-queries.hbm.xml");
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
}

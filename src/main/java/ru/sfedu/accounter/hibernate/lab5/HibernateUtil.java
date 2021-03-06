package ru.sfedu.accounter.hibernate.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.hibernate.lab5.models.*;

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
            metadataSources.addAnnotatedClass(HResources.class);
            metadataSources.addAnnotatedClass(HArmy.class);
            metadataSources.addAnnotatedClass(HUnit.class);
            metadataSources.addAnnotatedClass(HGame.class);
            metadataSources.addAnnotatedClass(HPlanet.class);
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

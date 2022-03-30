package ru.sfedu.accounter.hibernate.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.accounter.hibernate.lab5.models.*;
import ru.sfedu.accounter.Constants;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class HibernateDataProvider {
    private Logger log = LogManager.getLogger(HibernateDataProvider.class);
    private Session session;

    private void initSession() throws IOException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (sessionFactory.isClosed()) {
            log.error("Session is closed");
        }
        session = sessionFactory.openSession();
    }

    private void close() {
        session.close();
        session = null;
    }

    public HPlanet createHPlanet(HPlanet planet) throws IOException {
        initSession();
        Transaction tx = session.beginTransaction();
        planet.setId((Long) session.save(planet));
        tx.commit();
        log.debug(planet);
        close();
        return planet;
    }

    public List<HPlanet> getHPlanets () throws IOException {
        String query = String.format(Constants.QUERY, HPlanet.class.getSimpleName());
        initSession();
        Transaction tx = session.beginTransaction();
        List<HPlanet> planetList = session.createQuery(query).list();
        tx.commit();
        log.debug(planetList);
        close();
        return planetList;
    }

    public HPlanet getHPlanetById(long id) throws IOException {
        initSession();
        try {
            HPlanet planet = session.get(HPlanet.class, id);
            log.debug(planet);
            close();
            if (planet == null) {
                log.error("Not found.");
                planet = new HPlanet();
            }
            return planet;
        }
        catch(Exception e) {
            close();
            return new HPlanet();
        }
    }

    public Boolean deleteHPlanet(long id) throws IOException {
        initSession();
        HPlanet planet = new HPlanet();
        planet.setId(id);
        try {
            Transaction tr = session.beginTransaction();
            session.delete(planet);
            tr.commit();
            close();
            return true;
        } catch (Exception e) {
            log.error(e);
            close();
            return false;
        }
    }


    public HPlanet updateHPlanet (HPlanet planet) throws IOException {
        initSession();
        Transaction tr = session.beginTransaction();
        session.update(planet);
        tr.commit();
        close();
        return planet;
    }

    public List<HResources> getHResourcess () throws IOException {
        String query = String.format(Constants.QUERY, HResources.class.getSimpleName());
        initSession();
        Transaction tx = session.beginTransaction();
        List<HResources> resourcesList = session.createQuery(query).list();
        tx.commit();
        log.debug(resourcesList);
        close();
        return resourcesList;
    }


    public HResources createHResources(HResources resources) throws IOException {
        initSession();
        Transaction tx = session.beginTransaction();
        resources.setId((long) session.save(resources));
        tx.commit();
        log.debug(resources);
        close();
        return resources;
    }


    public HResources getHResourcesById(long id) throws IOException {
        initSession();
        try {
            HResources resources = session.get(HResources.class, id);
            log.debug(resources);
            close();
            if (resources == null) {
                log.error("Not found.");
                resources = new HResources();
            }
            return resources;
        }
        catch(Exception e) {
            close();
            return new HResources();
        }
    }


    public Boolean deleteHResources(long id) throws IOException {
        initSession();
        HResources resources = new HResources();
        resources.setId(id);
        try {
            Transaction tr = session.beginTransaction();
            session.delete(resources);
            tr.commit();
            close();
            return true;
        } catch (Exception e) {
            log.error(e);
            close();
            return false;
        }
    }


    public HResources updateHResources (HResources resources) throws IOException {
        initSession();
        Transaction tr = session.beginTransaction();
        session.update(resources);
        tr.commit();
        close();
        return resources;
    }


    public List<HUnit> getHUnits () throws IOException {
        String query = String.format(Constants.QUERY, HUnit.class.getSimpleName());
        initSession();
        Transaction tx = session.beginTransaction();
        List<HUnit> unitList = session.createQuery(query).list();
        tx.commit();
        log.debug(unitList);
        close();
        return unitList;
    }


    public HUnit createHUnit(HUnit unit) throws IOException {
        initSession();
        Transaction tx = session.beginTransaction();
        unit.setId((long) session.save(unit));
        tx.commit();
        log.debug(unit);
        close();
        return unit;
    }


    public HUnit getHUnitById(long id) throws IOException {
        initSession();
        try {
            HUnit unit = session.get(HUnit.class, id);
            log.debug(unit);
            close();
            if (unit == null) {
                log.error("Not found.");
                unit = new HUnit();
            }
            return unit;
        }
        catch(Exception e) {
            close();
            return new HUnit();
        }
    }


    public Boolean deleteHUnit(long id) throws IOException {
        initSession();
        HUnit unit = new HUnit();
        unit.setId(id);
        try {
            Transaction tr = session.beginTransaction();
            session.delete(unit);
            tr.commit();
            close();
            return true;
        } catch (Exception e) {
            log.error(e);
            close();
            return false;
        }
    }


    public HUnit updateHUnit (HUnit unit) throws IOException {
        initSession();
        Transaction tr = session.beginTransaction();
        session.update(unit);
        tr.commit();
        close();
        return unit;
    }


    public List<HArmy> getHArmy () throws IOException {
        String query = String.format(Constants.QUERY, HArmy.class.getSimpleName());
        initSession();
        Transaction tx = session.beginTransaction();
        List<HArmy> armyList = session.createQuery(query).list();
        tx.commit();
        log.debug(armyList);
        close();
        return armyList;
    }


    public HArmy createHArmy(HArmy army) throws IOException {
        for (HUnit unit : army.getUnits()) {
            createHUnit(unit);
        }
        initSession();
        Transaction tx = session.beginTransaction();
        army.setId((long) session.save(army));
        tx.commit();
        log.debug(army);
        close();
        return army;
    }


    public HArmy getHArmyById(long id) throws IOException {
        initSession();
        try {
            HArmy army = session.get(HArmy.class, id);
            log.debug(army);
            close();
            if (army == null) {
                log.error("Not found.");
                army = new HArmy();
            }
            return army;
        }
        catch(Exception e) {
            close();
            return new HArmy();
        }
    }


    public Boolean deleteHArmy(long id) throws IOException {
        initSession();
        HArmy army = new HArmy();
        army.setId(id);
        try {
            Transaction tr = session.beginTransaction();
            session.delete(army);
            tr.commit();
            close();
            return true;
        } catch (Exception e) {
            log.error(e);
            close();
            return false;
        }
    }


    public HArmy updateHArmy (HArmy army) throws IOException {
        initSession();
        Transaction tr = session.beginTransaction();
        session.update(army);
        tr.commit();
        close();
        return army;
    }


    public List<HGame> getHGames () throws IOException {
        String query = String.format(Constants.QUERY, HGame.class.getSimpleName());
        initSession();
        Transaction tx = session.beginTransaction();
        List<HGame> gameList = session.createQuery(query).list();
        tx.commit();
        log.debug(gameList);
        close();
        return gameList;
    }


    public HGame createHGame(HGame game) throws IOException {
        for (HArmy army : game.getArmies()) {
            if (army.getId() == null) {
                log.debug(army.getId());
                createHArmy(army);
            }
        }
        for (HPlanet planet : game.getPlanetList()) {
            if ((Long)planet.getId() == null) {
                log.info(planet.getId());
                createHPlanet(planet);
            }
        }
        if (game.getResources().getId() == null) {
            createHResources(game.getResources());
        }
        initSession();
        Transaction tx = session.beginTransaction();
        game.setId((long) session.save(game));
        tx.commit();
        close();
        return game;
    }


    public HGame getHGameById(long id) throws IOException {
        initSession();
        try {
            HGame game = session.get(HGame.class, id);
            log.debug(game);
            close();
            if (game == null) {
                log.error("Not found.");
                game = new HGame();
            }
            return game;
        }
        catch(Exception e) {
            close();
            return new HGame();
        }
    }


    public Boolean deleteHGame(long id) throws IOException {
        initSession();
        HGame game = new HGame();
        game.setId(id);
        try {
            Transaction tr = session.beginTransaction();
            session.delete(game);
            tr.commit();
            close();
            return true;
        } catch (Exception e) {
            log.error(e);
            close();
            return false;
        }
    }

    public HGame updateHGame (HGame game) throws IOException {
        for (HArmy army : game.getArmies()) {
            if (army.getId() == null) {
                createHArmy(army);
            }
        }
        for (HPlanet planet : game.getPlanetList()) {
            if ((Long)planet.getId() == null) {
                createHPlanet(planet);
            }
        }
        initSession();
        Transaction tr = session.beginTransaction();
        tr.commit();
        session.update(game);
        close();
        return game;
    }

    public BigInteger getHGameCountNative() throws IOException {
        initSession();
        String query = String.format(Constants.GET_COUNT, Constants.HGAME);
        BigInteger count = (BigInteger) session.createSQLQuery(query).list().get(0);
        log.debug(count);
        close();
        return count;
    }

    public Long getHGameCountHQL() throws IOException {
        initSession();
        String query = String.format(Constants.GET_COUNT, HGame.class.getSimpleName());
        Long count = (Long) session.createQuery(query).list().get(0);
        log.debug(count);
        close();
        return count;
    }

    public Long getHGameCountCriteria() throws IOException {
        initSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        Transaction tr = session.beginTransaction();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<HGame> root = query.from(HGame.class);
        query.select(builder.count(root.get("id")));
        Long count = (Long) session.createQuery(query).getSingleResult();
        tr.commit();
        log.debug(count);
        close();
        return count;
    }

    public long checkTimeHQL() throws IOException {
        long timeStart = System.currentTimeMillis();
        getHGameCountHQL();
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    public long checkTimeNative() throws IOException {
        long timeStart = System.currentTimeMillis();
        getHGameCountNative();
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    public long checkTimeCriteria() throws IOException {
        long timeStart = System.currentTimeMillis();
        getHGameCountCriteria();
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

}

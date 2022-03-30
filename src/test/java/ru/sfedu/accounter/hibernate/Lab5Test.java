package ru.sfedu.accounter.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.accounter.hibernate.lab5.GenerateEntity;
import ru.sfedu.accounter.hibernate.lab5.HibernateDataProvider;
import ru.sfedu.accounter.hibernate.lab5.models.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

class Lab5Test {

    private HibernateDataProvider dp = new HibernateDataProvider();
    private Logger log = LogManager.getLogger(Lab5Test.class);

    @Test
    public void getHResourcess() throws IOException {
        List<HResources> resourcesList = dp.getHResourcess();
        log.info(resourcesList);
    }

    @Test
    public void createHResources() throws IOException {
        HResources resources = new HResources();
        resources.setFood(50);
        resources.setGold(50);
        resources.setMetal(50);
        dp.createHResources(resources);
        log.info(resources);
    }

    @Test
    public void getHResourcesById() throws IOException {
        HResources resources = new HResources();
        resources.setFood(50);
        resources.setGold(50);
        resources.setMetal(50);
        dp.createHResources(resources);
        log.info(resources);
        resources = dp.getHResourcesById(resources.getId());
        log.info(resources);
    }

    @Test
    public void deleteHResources() throws IOException {
        List<HResources> resourcesList = dp.getHResourcess();
        long id = resourcesList.get(0).getId();
        Boolean hasDeleted = dp.deleteHResources(id);
        log.info(hasDeleted);
    }

    @Test
    public void updateHResources() throws IOException {
        List<HResources> resourcesList = dp.getHResourcess();
        HResources resources = resourcesList.get(0);
        resources.setGold(100);
        dp.updateHResources(resources);
        log.info(resources);
    }

    @Test
    public void getHUnits() throws IOException {
        List<HUnit> unitList = dp.getHUnits();
        log.info(unitList);
    }

    @Test
    public void createHUnit() throws IOException {
        HUnit unit = new HUnit();
        unit.setUnitType("Marksman");
        unit = dp.createHUnit(unit);
        log.info(unit);
    }

    @Test
    public void getHUnitById() throws IOException {
        HUnit unit = dp.getHUnitById(1);
        log.info(unit);
    }

    @Test
    public void deleteHUnit() throws IOException {
        HUnit unit = new HUnit();
        unit.setUnitType("Marksman");
        unit = dp.createHUnit(unit);
        List<HUnit> unitList = dp.getHUnits();
        long id = unitList.get(0).getId();
        Boolean hasDeleted = dp.deleteHUnit(id);
        log.info(hasDeleted);
    }

    @Test
    public void updateHUnit() throws IOException {
        HUnit unit = new HUnit();
        unit.setUnitType("Marksman");
        unit = dp.createHUnit(unit);
        List<HUnit> unitList = dp.getHUnits();
        unit = unitList.get(0);
        unit.setUnitType("Tank");
        unit = dp.updateHUnit(unit);
        log.info(unit);
    }

    @Test
    public void getHArmy() throws IOException {
        List<HArmy> armyList = dp.getHArmy();
        log.info(armyList);
    }

    @Test
    public void createHArmy() throws IOException {
        List<HUnit> unitList = GenerateEntity.generateHUnits(5);
        HArmy army = new HArmy();
        army.setArmyHealthPoints(100);
        army.setUnits(unitList);
        army = dp.createHArmy(army);
        log.info(army);
    }

    @Test
    public void getHArmyById() throws IOException {
        HArmy army = dp.getHArmyById(2);
        log.info(army);
    }

    @Test
    public void deleteHArmy() throws IOException {
        List<HUnit> unitList = GenerateEntity.generateHUnits(5);
        HArmy army = new HArmy();
        army.setArmyHealthPoints(100);
        army.setUnits(unitList);
        army = dp.createHArmy(army);
        List<HArmy> armyList = dp.getHArmy();
        long id = armyList.get(0).getId();
        Boolean hasDeleted = dp.deleteHArmy(id);
        log.info(hasDeleted);
    }

    @Test
    public void updateHArmy() throws IOException {
        List<HUnit> unitList = GenerateEntity.generateHUnits(5);
        HArmy army = new HArmy();
        army.setArmyHealthPoints(100);
        army.setUnits(unitList);
        army = dp.createHArmy(army);
        List<HArmy> armyList = dp.getHArmy();
        army = armyList.get(0);
        army.setArmyHealthPoints(150);
        army = dp.updateHArmy(army);
        log.info(army);
    }

    @Test
    public void getHGames() throws IOException {
        List<HGame> gameList = dp.getHGames();
        log.info(gameList);
    }

    @Test
    public void createHGame() throws IOException {
        HGame game = new HGame();

        List<HPlanet> planets = GenerateEntity.generateHPlanets(3);
        game.setPlanetList(planets);

        List<HArmy> armyList = GenerateEntity.generateHArmys(5, 2);
        game.setArmies(armyList);

        HResources resources = new HResources();
        resources.setGold(50);
        game.setResources(resources);

        game.setGameName("OAOAOAMMMM");

        game = dp.createHGame(game);
        log.info(game);
    }

    @Test
    public void getHGameById() throws IOException {
        HGame game = dp.getHGameById(1);
        log.info(game);
    }

    @Test
    public void deleteHGame() throws IOException {
        HGame game = new HGame();

        List<HPlanet> planets = GenerateEntity.generateHPlanets(3);
        game.setPlanetList(planets);

        List<HArmy> armyList = GenerateEntity.generateHArmys(5, 2);
        game.setArmies(armyList);

        HResources resources = new HResources();
        resources.setGold(50);
        game.setResources(resources);

        game.setGameName("OAOAOAMMMM");

        game = dp.createHGame(game);
        long id = game.getId();
        Boolean hasDeleted = dp.deleteHGame(id);
        log.info(hasDeleted);
    }

    @Test
    public void updateHGame() throws IOException {
        HGame game = new HGame();

        List<HPlanet> planets = GenerateEntity.generateHPlanets(3);
        game.setPlanetList(planets);

        List<HArmy> armyList = GenerateEntity.generateHArmys(5, 2);
        game.setArmies(armyList);

        HResources resources = new HResources();
        resources.setGold(50);
        game.setResources(resources);

        game.setGameName("OAOAOAMMMM");

        game = dp.createHGame(game);
        log.info(game);
        game.setGameName(game.getGameName() + "_new");
        game = dp.updateHGame(game);
        log.info(game);
    }

    @Test
    public void createHPlanet() throws IOException {
        HPlanet planet = new HPlanet();
        planet.setPlanetName("Delta");
        dp.createHPlanet(planet);
        log.info(planet);
    }

    @Test
    public void getHPlanets() throws IOException {
        List<HPlanet> planetList = dp.getHPlanets();
        log.info(planetList);
    }

    @Test
    public void getHPlanetById() throws IOException {
        HPlanet planet = dp.getHPlanetById(1);
        log.info(planet);
    }

    @Test
    public void deleteHPlanet() throws IOException {
        HPlanet planet = new HPlanet();
        planet.setPlanetName("Gamma");
        planet = dp.createHPlanet(planet);
        long id = planet.getId();
        Boolean hasDeleted = dp.deleteHPlanet(id);
        log.info(hasDeleted);
    }

    @Test
    public void updateHPlanet() throws IOException {
        HPlanet planet = new HPlanet();
        planet.setPlanetName("Alpha");
        planet = dp.createHPlanet(planet);
        log.info(planet.getPlanetName());
        planet.setPlanetName(planet.getPlanetName() + "_new");
        planet = dp.updateHPlanet(planet);
        log.info(planet.getPlanetName());
    }

    @Test
    public void getHGameCountNative() throws IOException {
        createHGame();
        BigInteger count = dp.getHGameCountNative();
        log.info(count);
    }

    @Test
    public void getHGameCountHQL() throws IOException {
        createHGame();
        Long count = dp.getHGameCountHQL();
        log.info(count);
    }

    @Test
    public void getHGameCountCriteria() throws IOException {
        createHGame();
        Long count = dp.getHGameCountCriteria();
        log.info(count);
    }
}
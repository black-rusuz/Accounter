package ru.sfedu.accounter.hibernate.lab5;


import ru.sfedu.accounter.hibernate.lab5.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateEntity {

    public static List<HPlanet> generateHPlanets(int count) {
        List<HPlanet> planets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            HPlanet planet = new HPlanet();
            planet.setPlanetName(generateString());
            planets.add(planet);
        }
        return planets;
    }

    public static List<HResources> generateHResourcess(int count) {
        List<HResources> resourcesList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            HResources resources = new HResources();
            resources.setGold(generateInt());
            resources.setId(generateLong());
            resourcesList.add(resources);
        }
        return resourcesList;
    }

    public static List<HUnit> generateHUnits(int count) {
        List<HUnit> unitList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            HUnit unit = new HUnit();
            unit.setUnitType(generateString());
            unit.setId(generateLong());
            unitList.add(unit);
        }
        return unitList;
    }

    public static List<HGame> generateHGames(int count, int armysCount) {
        List<HGame> gameList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            HGame game = new HGame();
            game.setId(generateLong());
            game.setGameName(generateString());
            game.setArmies(generateHArmys(armysCount, 5));
            gameList.add(game);
        }
        return gameList;
    }

    public static List<HArmy> generateHArmys(int count, int unitsCount) {
        List<HArmy> armyList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            HArmy army = new HArmy();
            army.setArmyHealthPoints(100);
            army.setUnits(generateHUnits(unitsCount));
            armyList.add(army);
        }
        return armyList;
    }

    private static String generateString() {
        Random random = new Random();
        char[] word = new char[random.nextInt(9) + 5];
        for (int i = 0; i < word.length; i++) {
            word[i] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    private static int generateInt() {
        return new Random().nextInt(10000000);
    }

    public static Long generateLong() {
        return Long.valueOf(generateInt());
    }

    private static boolean generateBoolean() {
        return new Random().nextInt(100) % 2 == 1;
    }
}

package ru.sfedu.accounter.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.Constants;

import java.util.LinkedHashMap;
import java.util.List;

public class JdbcUtil {
    protected static final Logger log = LogManager.getLogger(JdbcUtil.class);

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL
    public static final String CREATE_TABLE_BALANCE =
            "CREATE TABLE IF NOT EXISTS balance(" +
                    Constants.COLUMN_NAME_ID + " LONG PRIMARY KEY, " +
                    Constants.COLUMN_NAME_TIME + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_VALUE + " NUMERIC" +
                    ");";
    public static final String CREATE_TABLE_TRANSACTION =
            "CREATE TABLE IF NOT EXISTS transaction(" +
                    Constants.COLUMN_NAME_ID + " LONG PRIMARY KEY, " +
                    Constants.COLUMN_NAME_TIME + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_VALUE + " NUMERIC, " +
                    Constants.COLUMN_NAME_NAME + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_NEW_BALANCE + " LONG, " +
                    Constants.COLUMN_NAME_INCOME_CATEGORY + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_OUTCOME_CATEGORY + " VARCHAR(255)" +
                    ");";
    public static final String CREATE_TABLE_PLAN =
            "CREATE TABLE IF NOT EXISTS plan(" +
                    Constants.COLUMN_NAME_ID + " LONG PRIMARY KEY, " +
                    Constants.COLUMN_NAME_START_DATE + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_NAME + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_PERIOD + " VARCHAR(255), " +
                    Constants.COLUMN_NAME_TRANSACTION + " LONG" +
                    ");";

    public static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    public static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    public static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES('%s');";
    public static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    public static final String UPDATE_TABLE_SET = "UPDATE %s SET %s WHERE id = %d;";

    public static String selectAllFromTable(String tableName) {
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }
    public static String selectFromTableById(String tableName, long id) {
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }
    public static String insertIntoTableValues(String tableName, List<String> values) {
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, String.join("', '", values));
    }
    public static String deleteFromTableById(String tableName, long id) {
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }
    public static String updateTableSet(String tableName, LinkedHashMap<String, Object> set, long id) {
        return String.format(UPDATE_TABLE_SET, tableName, mapToString(set), id);
    }

    private static String mapToString(LinkedHashMap<String, Object> set) {
        StringBuilder mapAsString = new StringBuilder();
        for(String key: set.keySet()) {
            mapAsString.append(key).append(" = '").append(set.get(key)).append("', ");
        }
        mapAsString.deleteCharAt(mapAsString.length() - 2);
        return mapAsString.toString();
    }

}

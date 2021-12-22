package ru.sfedu.accounter.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;

public class JdbcUtil {
    protected static final Logger log = LogManager.getLogger(JdbcUtil.class);

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_VALUE = "cost";        // Because "value" is reserved word in SQL

    public static final String COLUMN_NAME_START_DATE = "start_date";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PERIOD = "period";
    public static final String COLUMN_NAME_TRANSACTION = "transaction_id";

    public static final String COLUMN_NAME_NEW_BALANCE = "new_balance_id";
    public static final String COLUMN_NAME_INCOME_CATEGORY = "income_category";
    public static final String COLUMN_NAME_OUTCOME_CATEGORY = "outcome_category";

    public static final String COLUMN_TYPE_PK = " PRIMARY KEY";
    public static final String COLUMN_TYPE_LONG = " LONG";
    public static final String COLUMN_TYPE_STRING = " VARCHAR";
    public static final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    public static final String CREATE_TABLE_BALANCE =
            "CREATE TABLE IF NOT EXISTS balance(" +
                    COLUMN_NAME_ID + COLUMN_TYPE_LONG + COLUMN_TYPE_PK + ", " +
                    COLUMN_NAME_TIME + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_VALUE + COLUMN_TYPE_DOUBLE +
                    ");";
    public static final String CREATE_TABLE_PLAN =
            "CREATE TABLE IF NOT EXISTS plan(" +
                    COLUMN_NAME_ID + COLUMN_TYPE_LONG + COLUMN_TYPE_PK + ", " +
                    COLUMN_NAME_START_DATE + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_NAME + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_PERIOD + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_TRANSACTION + COLUMN_TYPE_LONG +
                    ");";
    public static final String CREATE_TABLE_TRANSACTION =
            "CREATE TABLE IF NOT EXISTS transaction(" +
                    COLUMN_NAME_ID + COLUMN_TYPE_LONG + COLUMN_TYPE_PK + ", " +
                    COLUMN_NAME_TIME + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_VALUE + COLUMN_TYPE_DOUBLE + ", " +
                    COLUMN_NAME_NAME + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_NEW_BALANCE + COLUMN_TYPE_LONG + ", " +
                    COLUMN_NAME_INCOME_CATEGORY + COLUMN_TYPE_STRING + ", " +
                    COLUMN_NAME_OUTCOME_CATEGORY + COLUMN_TYPE_STRING +
                    ");";

    public static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    public static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    public static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES('%s');";
    public static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    public static final String UPDATE_TABLE_SET = "UPDATE %s SET %s WHERE id = %d;";

    // SQL expressions to String builders
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
    public static String updateTableSet(String tableName, LinkedHashMap<String, Object> map, long id) {
        return String.format(UPDATE_TABLE_SET, tableName, mapToString(map), id);
    }

    private static String mapToString(LinkedHashMap<String, Object> map) {
        StringBuilder mapAsString = new StringBuilder();
        for(String key: map.keySet()) {
            mapAsString.append(key).append(" = '").append(map.get(key)).append("', ");
        }
        mapAsString.deleteCharAt(mapAsString.length() - 2);
        return mapAsString.toString();
    }

}

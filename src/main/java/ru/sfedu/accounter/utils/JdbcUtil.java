package ru.sfedu.accounter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Plan;

import java.util.LinkedHashMap;

public class JdbcUtil {
    private static final Logger log = LogManager.getLogger(JdbcUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL
    public static final String SQL_QUOTE = "'";
    public static final String SQL_COMMA = ", ";
    public static final String SQL_DELIMITER = "', '";
    public static final String SQL_QUOTE_START = " = '";
    public static final String SQL_QUOTE_END = "', ";
    public static final String SQL_STATEMENT_START = "(";
    public static final String SQL_STATEMENT_END = ");";

    public static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";

    public static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    public static final String COLUMN_TYPE_LONG = " LONG";
    public static final String COLUMN_TYPE_STRING = " VARCHAR";
    public static final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    public static final String CREATE_TABLE_BALANCE = mapToCreateTableString(new Balance());
    public static final String CREATE_TABLE_PLAN = mapToCreateTableString(new Income());
    public static final String CREATE_TABLE_TRANSACTION = mapToCreateTableString(new Plan());

    public static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    public static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    public static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES(%s);";
    public static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    public static final String UPDATE_TABLE_SET = "UPDATE %s SET %s WHERE id = %d;";


    public static String selectAllFromTable(String tableName) {
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    public static String selectFromTableById(String tableName, long id) {
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String insertIntoTableValues(String tableName, T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        String values = String.join(SQL_DELIMITER, mapToInsertString(map));
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, values);
    }

    public static String deleteFromTableById(String tableName, long id) {
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String updateTableSet(String tableName, T bean, long id) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        String set = mapToUpdateString(map);
        return String.format(UPDATE_TABLE_SET, tableName, set, id);
    }


    private static <T> String mapToCreateTableString(T bean) {
        LinkedHashMap<String, Object> set = objectMapper.convertValue(bean, LinkedHashMap.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SQL_CREATE_TABLE_IF_NOT_EXISTS);
        stringBuilder.append(bean.getClass().getSuperclass().equals(Object.class)
                ? bean.getClass().getSimpleName()
                : bean.getClass().getSuperclass().getSimpleName());
        stringBuilder.append(SQL_STATEMENT_START);

        int count = 0;
        for (String key : set.keySet()) {
            if (count == 0)
                stringBuilder.append(key).append(COLUMN_TYPE_LONG).append(COLUMN_PRIMARY_KEY).append(SQL_COMMA);
            else {
                stringBuilder.append(key);
                if (set.get(key).getClass() == Long.class)
                    stringBuilder.append(COLUMN_TYPE_LONG);
                else if (set.get(key).getClass() == String.class)
                    stringBuilder.append(COLUMN_TYPE_STRING);
                else if (set.get(key).getClass() == Double.class)
                    stringBuilder.append(COLUMN_TYPE_DOUBLE);
                else if (set.get(key).getClass() == LinkedHashMap.class)
                    stringBuilder.append(COLUMN_TYPE_LONG);
                stringBuilder.append(SQL_COMMA);
            }
            count++;
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2).append(SQL_STATEMENT_END);
        return stringBuilder.toString();
    }

    private static String mapToInsertString(LinkedHashMap<String, Object> set) {
        StringBuilder stringBuilder = new StringBuilder().append(SQL_QUOTE);
        for (String key : set.keySet())
            stringBuilder.append(set.get(key)).append(SQL_DELIMITER);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    private static String mapToUpdateString(LinkedHashMap<String, Object> set) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : set.keySet())
            stringBuilder.append(key).append(SQL_QUOTE_START).append(set.get(key)).append(SQL_QUOTE_END);
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
}

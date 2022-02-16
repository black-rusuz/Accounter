package ru.sfedu.accounter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Plan;

import java.util.LinkedHashMap;

public class JdbcUtil {
    private final Logger log = LogManager.getLogger(JdbcUtil.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL
    private final String SQL_QUOTE = "'";
    private final String SQL_COMMA = ", ";
    private final String SQL_DELIMITER = "', '";
    private final String SQL_QUOTE_START = " = '";
    private final String SQL_QUOTE_END = "', ";
    private final String SQL_STATEMENT_START = "(";
    private final String SQL_STATEMENT_END = ");";

    private final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    private final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    private final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES(%s);";
    private final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    private final String UPDATE_TABLE_SET = "UPDATE %s SET %s WHERE id = %d;";

    // Reserved words
    public final String INNER_DELIMITER = "::";
    private final String VALUE = "value";
    private final String SIZE = "size";
    private final String TRANSACTION = "transaction";

    public String selectAllFromTable(String tableName) {
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    public String selectFromTableById(String tableName, long id) {
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    public <T> String insertIntoTableValues(String tableName, T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        String values = String.join(SQL_DELIMITER, mapToInsertString(map));
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, values);
    }

    public String deleteFromTableById(String tableName, long id) {
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    public <T> String updateTableSet(String tableName, T bean, long id) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        String set = mapToUpdateString(map);
        return String.format(UPDATE_TABLE_SET, tableName, set, id);
    }

    private String mapToInsertString(LinkedHashMap<String, Object> set) {
        StringBuilder stringBuilder = new StringBuilder().append(SQL_QUOTE);
        for (String key : set.keySet()) {
            if (key.equals(TRANSACTION)) {
                LinkedHashMap<String, Object> inner = (LinkedHashMap<String, Object>) set.get(TRANSACTION);
                StringBuilder innerStringBuilder = new StringBuilder();
                for (String innerKey : inner.keySet())
                    innerStringBuilder.append(inner.get(innerKey)).append(INNER_DELIMITER);
                innerStringBuilder.deleteCharAt(innerStringBuilder.length() - 2);
                innerStringBuilder.deleteCharAt(innerStringBuilder.length() - 1);
                stringBuilder.append(innerStringBuilder).append(SQL_DELIMITER);
            } else
                stringBuilder.append(set.get(key)).append(SQL_DELIMITER);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 3);
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String mapToUpdateString(LinkedHashMap<String, Object> set) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : set.keySet()) {
            if (key.equals(VALUE))
                stringBuilder.append(SIZE);
            else
                stringBuilder.append(key);
            stringBuilder.append(SQL_QUOTE_START).append(set.get(key)).append(SQL_QUOTE_END);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }


    private final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";

    private final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private final String COLUMN_TYPE_LONG = " LONG";
    private final String COLUMN_TYPE_STRING = " VARCHAR";
    private final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    public final String CREATE_TABLE_BALANCE = mapToCreateTableString(new Balance());
    public final String CREATE_TABLE_INCOME = mapToCreateTableString(new Income());
    public final String CREATE_TABLE_OUTCOME = mapToCreateTableString(new Outcome());
    public final String CREATE_TABLE_PLAN = mapToCreateTableString(new Plan());

    private <T> String mapToCreateTableString(T bean) {
        LinkedHashMap<String, Object> set = objectMapper.convertValue(bean, LinkedHashMap.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SQL_CREATE_TABLE_IF_NOT_EXISTS);
        stringBuilder.append(bean.getClass().getSimpleName());
        stringBuilder.append(SQL_STATEMENT_START);

        int count = 0;
        for (String key : set.keySet()) {
            if (count == 0)
                stringBuilder.append(key).append(COLUMN_TYPE_LONG).append(COLUMN_PRIMARY_KEY).append(SQL_COMMA);
            else {
                if (key.equals(VALUE))
                    stringBuilder.append(SIZE);
                else stringBuilder.append(key);
                if (set.get(key).getClass() == Long.class)
                    stringBuilder.append(COLUMN_TYPE_LONG);
                else if (set.get(key).getClass() == String.class)
                    stringBuilder.append(COLUMN_TYPE_STRING);
                else if (set.get(key).getClass() == Double.class)
                    stringBuilder.append(COLUMN_TYPE_DOUBLE);
                else if (set.get(key).getClass() == LinkedHashMap.class)
                    stringBuilder.append(COLUMN_TYPE_STRING);
                stringBuilder.append(SQL_COMMA);
            }
            count++;
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        stringBuilder.append(SQL_STATEMENT_END);
        return stringBuilder.toString();
    }
}

package ru.sfedu.accounter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sfedu.accounter.model.beans.Transaction;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class JdbcUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL
    private static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    private static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    private static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES (%s);";
    private static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    private static final String UPDATE_TABLE_SET = "UPDATE %s SET %s WHERE id = %d;";

    private static final String SQL_COMMA = ", ";
    private static final String SQL_VALUE_WRAPPER = "'%s'";
    private static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";


    public static String selectAllFromTable(String tableName) {
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    public static String selectFromTableById(String tableName, long id) {
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String insertIntoTableValues(String tableName, T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, mapToInsertString(map));
    }

    public static String deleteFromTableById(String tableName, long id) {
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String updateTableSet(String tableName, T bean, long id) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        return String.format(UPDATE_TABLE_SET, tableName, mapToUpdateString(map), id);
    }


    // Reserved words
    private static final String VALUE = "value";
    private static final String SIZE = "size";
    private static final String TRANSACTION = "transaction";

    private static String mapToInsertString(LinkedHashMap<String, Object> values) {
        return values.entrySet().stream().map(e -> {
            if (e.getKey().equals(TRANSACTION))
                return String.format(SQL_VALUE_WRAPPER, innerTransactionMapToString((LinkedHashMap<String, Object>) e.getValue()));
            else
                return String.format(SQL_VALUE_WRAPPER, e.getValue());
        }).collect(Collectors.joining(SQL_COMMA));
    }

    private static String mapToUpdateString(LinkedHashMap<String, Object> set) {
        return set.entrySet().stream().map(e -> {
            if (e.getKey().equals(TRANSACTION))
                return String.format(SQL_KEY_VALUE_WRAPPER, e.getKey(), innerTransactionMapToString((LinkedHashMap<String, Object>) e.getValue()));
            else if (e.getKey().equals(VALUE))
                return String.format(SQL_KEY_VALUE_WRAPPER, SIZE, e.getValue());
            else
                return String.format(SQL_KEY_VALUE_WRAPPER, e.getKey(), e.getValue());
        }).collect(Collectors.joining(SQL_COMMA));
    }

    private static String innerTransactionMapToString(LinkedHashMap<String, Object> transactionMap) {
        return transactionMap.values().stream().map(Object::toString)
                .collect(Collectors.joining(TransactionConverter.fieldsDelimiter));
    }

    public static Transaction stringToInnerTransaction(String string) {
        return new TransactionConverter().convert(string);
    }


    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String ID = "id";
    private static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private static final String COLUMN_TYPE_LONG = " LONG";
    private static final String COLUMN_TYPE_STRING = " VARCHAR";
    private static final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    public static <T> String createTable(T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        String columns = mapToCreateTableString(map);
        return String.format(SQL_CREATE_TABLE_IF_NOT_EXISTS, bean.getClass().getSimpleName(), columns);
    }

    private static <T> String mapToCreateTableString(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream().map(e -> {
            if (e.getKey().equals(ID))
                return e.getKey() + COLUMN_TYPE_LONG + COLUMN_PRIMARY_KEY;
            else if (e.getKey().equals(VALUE))
                return SIZE + COLUMN_TYPE_DOUBLE;
            else if (e.getValue().getClass().equals(Long.class))
                return e.getKey() + COLUMN_TYPE_LONG;
            else if (e.getValue().getClass().equals(Double.class))
                return e.getKey() + COLUMN_TYPE_DOUBLE;
            else
                return e.getKey() + COLUMN_TYPE_STRING;
        }).collect(Collectors.joining(SQL_COMMA));
    }
}

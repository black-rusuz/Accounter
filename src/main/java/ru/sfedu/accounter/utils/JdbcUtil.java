package ru.sfedu.accounter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sfedu.accounter.model.beans.Transaction;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "javadoc"})
public class JdbcUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // SOME CONSTANTS USED ONLY FOR SQL. NOT GOOD, BUT LEGAL

    // ==============================
    //              DML
    // ==============================
    private static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    private static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = %d;";
    private static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES (%s);";
    private static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE id = %d;";
    private static final String UPDATE_TABLE_SET_BY_ID = "UPDATE %s SET %s WHERE id = %d;";

    private static final String SQL_COMMA = ", ";
    private static final String SQL_VALUE_WRAPPER = "'%s'";
    private static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";

    /**
     * @param tableName
     * @return Native SQL command for getting all recordset
     */
    public static String selectAllFromTable(String tableName) {
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    /**
     * @param tableName
     * @param id
     * @return Native SQL command for getting record by id
     */
    public static String selectFromTableById(String tableName, long id) {
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    /**
     * @param tableName
     * @param bean      Serializable (recommended)
     * @return Native SQL command for appending record
     */
    public static <T> String insertIntoTableValues(String tableName, T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, mapToValues(map));
    }

    /**
     * @param tableName
     * @param id
     * @return Native SQL command for deleting record
     */
    public static String deleteFromTableById(String tableName, long id) {
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    /**
     * @param tableName
     * @param bean      Serializable (recommended)
     * @param id
     * @return Native SQL command for updating recordset by id
     */
    public static <T> String updateTableSetById(String tableName, T bean, long id) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        return String.format(UPDATE_TABLE_SET_BY_ID, tableName, mapToKeyValues(map), id);
    }


    // =========================
    //          Helpers
    // =========================

    // Reserved words
    private static final String VALUE = "value";
    private static final String SIZE = "size";
    private static final String TRANSACTION = "transaction";

    /**
     * Generates String of VALUES() for bean converted to HashMap
     *
     * @param values objectMapper.convertValue(bean, LinkedHashMap.class)
     * @return String of values separated by commas
     */
    private static String mapToValues(LinkedHashMap<String, Object> values) {
        return values.entrySet().stream().map(e -> {
            if (e.getKey().equals(TRANSACTION))
                return String.format(SQL_VALUE_WRAPPER,
                        innerTransactionMapToString((LinkedHashMap<String, Object>) e.getValue()));
            else
                return String.format(SQL_VALUE_WRAPPER, e.getValue());
        }).collect(Collectors.joining(SQL_COMMA));
    }

    /**
     * Generates String of SET for bean converted to HashMap
     *
     * @param set objectMapper.convertValue(bean, LinkedHashMap.class)
     * @return String of keys and values separated by commas
     */
    private static String mapToKeyValues(LinkedHashMap<String, Object> set) {
        return set.entrySet().stream().map(e -> {
            if (e.getKey().equals(VALUE))
                return String.format(SQL_KEY_VALUE_WRAPPER, SIZE, e.getValue());
            else if (e.getKey().equals(TRANSACTION))
                return String.format(SQL_KEY_VALUE_WRAPPER,
                        e.getKey(), innerTransactionMapToString((LinkedHashMap<String, Object>) e.getValue()));
            else
                return String.format(SQL_KEY_VALUE_WRAPPER, e.getKey(), e.getValue());
        }).collect(Collectors.joining(SQL_COMMA));
    }

    /**
     * Transforms Transaction converted to HashMap to String
     *
     * @param transactionMap
     * @return String like 'id::value::name::category'
     */
    private static String innerTransactionMapToString(LinkedHashMap<String, Object> transactionMap) {
        return transactionMap.values().stream().map(Object::toString)
                .collect(Collectors.joining(TransactionConverter.fieldsDelimiter));
    }

    /**
     * Transforms HashMap converted to String back to Transaction
     *
     * @param string like 'id::value::name::category'
     * @return Transaction
     */
    public static Transaction stringToInnerTransaction(String string) {
        return new TransactionConverter().convert(string);
    }


    // ==============================
    //              DDL
    // ==============================
    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String ID = "id";
    private static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private static final String COLUMN_TYPE_LONG = " LONG";
    private static final String COLUMN_TYPE_STRING = " VARCHAR";
    private static final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    /**
     * @param bean Serializable (recommended)
     *             NOT CLASS because need to know fields datatype
     * @return Native SQL command for creating table
     */
    public static <T> String createTable(T bean) {
        LinkedHashMap<String, Object> map = objectMapper.convertValue(bean, LinkedHashMap.class);
        return String.format(SQL_CREATE_TABLE_IF_NOT_EXISTS, bean.getClass().getSimpleName(), mapToColumns(map));
    }

    /**
     * Generates String of fields for table using bean converted to HashMap
     *
     * @param map objectMapper.convertValue(bean, LinkedHashMap.class)
     * @return String of column names with datatypes separated by commas
     */
    private static String mapToColumns(LinkedHashMap<String, Object> map) {
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

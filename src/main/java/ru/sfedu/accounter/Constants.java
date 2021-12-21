package ru.sfedu.accounter;

public class Constants {

    // General
    public static final String APP_NAME = "APP_NAME";

    // MongoDB
    public static final String MONGO_DB_DEFAULT_ACTOR = "MONGO_DB_DEFAULT_ACTOR";
    public static final String MONGO_DB_CONNECTION_STRING = "MONGO_DB_CONNECTION_STRING";
//    public static final String MONGO_DB_PREFIX = "MONGO_DB_PREFIX";
//    public static final String MONGO_DB_USERNAME = "MONGO_DB_USERNAME";
//    public static final String MONGO_DB_PASSWORD = "MONGO_DB_PASSWORD";
//    public static final String MONGO_DB_HOSTNAME = "MONGO_DB_HOSTNAME";
    public static final String MONGO_DB_DATABASE = "MONGO_DB_DATABASE";
    public static final String MONGO_DB_COLLECTION = "MONGO_DB_COLLECTION";

    // XML
    public static final String XML_PATH = "XML_PATH";
    public static final String XML_EXTENSION = "XML_EXTENSION";

    // JDBC
    public static final String H2_HOSTNAME = "H2_HOSTNAME";
    public static final String H2_USERNAME = "H2_USERNAME";
    public static final String H2_PASSWORD = "H2_PASSWORD";



    // Internal
    public static final String ENVIROMENT_VARIABLE = "props";

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

    public static final String METHOD_NAME_WRITE = "Write";
    public static final String METHOD_NAME_APPEND = "Append";
    public static final String METHOD_NAME_DELETE = "Delete";
    public static final String METHOD_NAME_UPDATE = "Update";

    public static final String RESULT_MESSAGE_WRITING_SUCCESS = "Records wrote successfully";
    public static final String RESULT_MESSAGE_WRITING_ERROR = "Error while adding: ";
    public static final String RESULT_MESSAGE_NOT_FOUND = "Record with this ID not found";
}

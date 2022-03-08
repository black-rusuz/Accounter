package ru.sfedu.accounter;

public class Constants {
    // General
    public static final String APP_NAME = "APP_NAME";

    // MongoDB
    public static final String MONGO_DB_ENABLE_LOGGING = "MONGO_DB_ENABLE_LOGGING";
    public static final String MONGO_DB_DEFAULT_ACTOR = "MONGO_DB_DEFAULT_ACTOR";
    public static final String MONGO_DB_CONNECTION_STRING = "MONGO_DB_CONNECTION_STRING";
    public static final String MONGO_DB_DATABASE = "MONGO_DB_DATABASE";
    public static final String MONGO_DB_COLLECTION = "MONGO_DB_COLLECTION";

    // XML
    public static final String XML_PATH = "XML_PATH";
    public static final String XML_EXTENSION = "XML_EXTENSION";

    // CSV
    public static final String CSV_PATH = "CSV_PATH";
    public static final String CSV_EXTENSION = "CSV_EXTENSION";

    // JDBC
    public static final String H2_HOSTNAME = "H2_HOSTNAME";
    public static final String H2_USERNAME = "H2_USERNAME";
    public static final String H2_PASSWORD = "H2_PASSWORD";


    // Internal
    public static final String ENVIRONMENT_VARIABLE = "props";

    // CRUD
    public static final String RESULT_MESSAGE_WRITING_SUCCESS = "Records wrote successfully";
    public static final String RESULT_MESSAGE_WRITING_ERROR = "Error while writing: ";
    public static final String RESULT_MESSAGE_NOT_FOUND = "Record with this ID not found";

    public static final String METHOD_NAME_APPEND = "Append";
    public static final String METHOD_NAME_DELETE = "Delete";
    public static final String METHOD_NAME_UPDATE = "Update";

    // CLI
    public static final String CLI_ERROR_INVALID_DATA_PROVIDER = "Invalid data provider";
    public static final String CLI_ERROR_INVALID_ARGUMENTS = "Invalid number of arguments";
    public static final String CLI_ERROR_FEW_ARGUMENTS = "Too few arguments";
    public static final String CLI_ERROR_NOTHING_FOUND = "Nothing found";

    public static final String CLI_CURRENT_BALANCE = "Current balance: ";
    public static final String CLI_ALL_TRANSACTIONS = "All your transactions: \n";
    public static final String CLI_ALL_PLANS = "All your plans: \n";
    public static final String CLI_CREATED_PLAN = "Created plan: \n";

    public static final String XML = "XML";
    public static final String JDBC = "JDBC";
    public static final String CSV = "CSV";

    public static final String MANAGE_BALANCE = "MANAGEBALANCE";
    public static final String MANAGE_PLANS = "MANAGEPLANS";

    public static final String REPEAT = "REPEAT";
    public static final String PLAN = "PLAN";

}

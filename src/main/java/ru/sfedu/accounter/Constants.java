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

    // CSV
    public static final String CSV_PATH = "CSV_PATH";
    public static final String CSV_EXTENSION = "CSV_EXTENSION";

    // JDBC
    public static final String H2_HOSTNAME = "H2_HOSTNAME";
    public static final String H2_USERNAME = "H2_USERNAME";
    public static final String H2_PASSWORD = "H2_PASSWORD";



    // Internal
    public static final String ENVIROMENT_VARIABLE = "props";

    public static final String METHOD_NAME_WRITE = "Write";
    public static final String METHOD_NAME_APPEND = "Append";
    public static final String METHOD_NAME_DELETE = "Delete";
    public static final String METHOD_NAME_UPDATE = "Update";

    public static final String RESULT_MESSAGE_WRITING_SUCCESS = "Records wrote successfully";
    public static final String RESULT_MESSAGE_WRITING_ERROR = "Error while writing: ";
    public static final String RESULT_MESSAGE_NOT_FOUND = "Record with this ID not found";
}

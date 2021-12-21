package ru.sfedu.accounter.api;

import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;
import ru.sfedu.accounter.utils.ConfigurationUtil;
import ru.sfedu.accounter.utils.JdbcUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataProviderJdbc extends AbstractDataProvider implements IDataProvider {
    String hostname = ConfigurationUtil.getConfigurationEntry(Constants.H2_HOSTNAME);
    String username = ConfigurationUtil.getConfigurationEntry(Constants.H2_USERNAME);
    String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderJdbc() throws IOException, SQLException {
        initDb();
    }

    private void initDb() throws SQLException {
        write(JdbcUtil.CREATE_TABLE_BALANCE);
        write(JdbcUtil.CREATE_TABLE_PLAN);
        write(JdbcUtil.CREATE_TABLE_TRANSACTION);
    }

    private List<Balance> readBalance(String sql) throws SQLException {
        List<Balance> list = new ArrayList<Balance>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Balance balance = new Balance(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3));
            list.add(balance);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private List<Plan> readPlan(String sql) throws SQLException {
        List<Plan> list = new ArrayList<Plan>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Plan plan = new Plan(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    getTransactionById(resultSet.getLong(5)));
            list.add(plan);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private List<Transaction> readTransaction(String sql) throws SQLException {
        List<Transaction> list = new ArrayList<Transaction>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Transaction transaction = new Transaction() {};
            if (resultSet.getString(7).isBlank())
                transaction = new Income(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        getBalanceById(resultSet.getLong(5)),
                        IncomeCategory.valueOf(resultSet.getString(6)));
            if (resultSet.getString(6).isBlank())
                transaction = new Outcome(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        getBalanceById(resultSet.getLong(5)),
                        OutcomeCategory.valueOf(resultSet.getString(7)));
            list.add(transaction);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private void write(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
        statement.close();
    }

    public List<Balance> getAllBalance() {
        List<Balance> list = new ArrayList<Balance>();
        try {
            list = readBalance(JdbcUtil.selectAllFromTable(Balance.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    public Balance getBalanceById(long id) {
        List<Balance> list = new ArrayList<Balance>();
        Balance balance = null;
        try {
            list = readBalance(JdbcUtil.selectFromTableById(Balance.class.getSimpleName(), id));
            if (list.isEmpty())
                return null;
            balance = list.get(0);
        } catch (Exception ignored) {
        }
        return balance;
    }

    public Balance appendBalance(Balance balance) {
        try {
            if (getBalanceById(balance.getId()) != null)
                balance.setId();
        } catch (Exception ignored) {
        }
        try {
            List<String> list = new ArrayList<String>();
            list.add(String.valueOf(balance.getId()));
            list.add(String.valueOf(balance.getTime()));
            list.add(String.valueOf(balance.getValue()));
            write(JdbcUtil.insertIntoTableValues(Balance.class.getSimpleName(), list));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, balance, Result.State.Error);
            return new Balance();
        }
        sendLogs(Constants.METHOD_NAME_APPEND, balance, Result.State.Success);
        return balance;
    }

    public Result deleteBalance(long id) {
        Balance balance = getBalanceById(id);
        if (balance == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(Balance.class.getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, balance, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, balance, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(JdbcUtil.COLUMN_NAME_ID, balance.getId());
            map.put(JdbcUtil.COLUMN_NAME_TIME, balance.getTime());
            map.put(JdbcUtil.COLUMN_NAME_VALUE, balance.getValue());
            write(JdbcUtil.updateTableSet(Balance.class.getSimpleName(), map, id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, balance, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, balance, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public List<Plan> getAllPlan() {
        List<Plan> list = new ArrayList<Plan>();
        try {
            list = readPlan(JdbcUtil.selectAllFromTable(Plan.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    public Plan getPlanById(long id) {
        List<Plan> list = new ArrayList<Plan>();
        Plan plan = null;
        try {
            list = readPlan(JdbcUtil.selectFromTableById(Plan.class.getSimpleName(), id));
            if (list.isEmpty())
                return null;
            plan = list.get(0);
        } catch (Exception ignored) {
        }
        return plan;
    }

    public Plan appendPlan(Plan plan) {
        try {
            if (getPlanById(plan.getId()) != null)
                plan.setId();
        } catch (Exception ignored) {
        }
        try {
            List<String> list = new ArrayList<String>();
            list.add(String.valueOf(plan.getId()));
            list.add(String.valueOf(plan.getStartDate()));
            list.add(String.valueOf(plan.getName()));
            list.add(String.valueOf(plan.getPeriod()));
            list.add(String.valueOf(plan.getTransaction().getId()));
            write(JdbcUtil.insertIntoTableValues(Plan.class.getSimpleName(), list));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, plan, Result.State.Error);
            return new Plan();
        }
        sendLogs(Constants.METHOD_NAME_APPEND, plan, Result.State.Success);
        return plan;
    }

    public Result deletePlan(long id) {
        Plan plan = getPlanById(id);
        if (plan == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(Plan.class.getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, plan, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, plan, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(JdbcUtil.COLUMN_NAME_ID, plan.getId());
            map.put(JdbcUtil.COLUMN_NAME_START_DATE, plan.getStartDate());
            map.put(JdbcUtil.COLUMN_NAME_NAME, plan.getName());
            map.put(JdbcUtil.COLUMN_NAME_PERIOD, plan.getPeriod());
            map.put(JdbcUtil.COLUMN_NAME_TRANSACTION, plan.getTransaction().getId());
            write(JdbcUtil.updateTableSet(Plan.class.getSimpleName(), map, id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, plan, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, plan, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> list = new ArrayList<Transaction>();
        try {
            list = readTransaction(JdbcUtil.selectAllFromTable(Transaction.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    public Transaction getTransactionById(long id) {
        List<Transaction> list = new ArrayList<Transaction>();
        Transaction transaction = null;
        try {
            list = readTransaction(JdbcUtil.selectFromTableById(Transaction.class.getSimpleName(), id));
            if (list.isEmpty())
                return null;
            transaction = list.get(0);
        } catch (Exception ignored) {
        }
        return transaction;
    }

    public Transaction appendTransaction(Transaction transaction) {
        try {
            if (getTransactionById(transaction.getId()) != null)
                transaction.setId();
        } catch (Exception ignored) {
        }
        try {
            if (transaction.getClass().equals(Income.class)) {
                Income income = (Income) transaction;
                List<String> list = new ArrayList<String>();
                list.add(String.valueOf(income.getId()));
                list.add(String.valueOf(income.getTime()));
                list.add(String.valueOf(income.getValue()));
                list.add(String.valueOf(income.getName()));
                list.add(String.valueOf(income.getNewBalance().getId()));
                list.add(String.valueOf(income.getIncomeCategory()));
                list.add("");
                write(JdbcUtil.insertIntoTableValues(Transaction.class.getSimpleName(), list));
            }
            if (transaction.getClass().equals(Outcome.class)) {
                Outcome outcome = (Outcome) transaction;
                List<String> list = new ArrayList<String>();
                list.add(String.valueOf(outcome.getId()));
                list.add(String.valueOf(outcome.getTime()));
                list.add(String.valueOf(outcome.getValue()));
                list.add(String.valueOf(outcome.getName()));
                list.add(String.valueOf(outcome.getNewBalance().getId()));
                list.add("");
                list.add(String.valueOf(outcome.getOutcomeCategory()));
                write(JdbcUtil.insertIntoTableValues(Transaction.class.getSimpleName(), list));
            }
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, transaction, Result.State.Error);
            return new Transaction(){};
        }
        sendLogs(Constants.METHOD_NAME_APPEND, transaction, Result.State.Success);
        return transaction;
    }

    public Result deleteTransaction(long id) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(Transaction.class.getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, transaction, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, transaction, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            if (transaction.getClass().equals(Income.class)) {
                Income income = (Income) transaction;
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put(JdbcUtil.COLUMN_NAME_ID, income.getId());
                map.put(JdbcUtil.COLUMN_NAME_TIME, income.getTime());
                map.put(JdbcUtil.COLUMN_NAME_VALUE, income.getValue());
                map.put(JdbcUtil.COLUMN_NAME_NAME, income.getName());
                map.put(JdbcUtil.COLUMN_NAME_NEW_BALANCE, income.getNewBalance().getId());
                map.put(JdbcUtil.COLUMN_NAME_INCOME_CATEGORY, income.getIncomeCategory());
                map.put(JdbcUtil.COLUMN_NAME_OUTCOME_CATEGORY, "");
                write(JdbcUtil.updateTableSet(Transaction.class.getSimpleName(), map, id));
            }
            if (transaction.getClass().equals(Outcome.class)) {
                Outcome outcome = (Outcome) transaction;
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put(JdbcUtil.COLUMN_NAME_ID, outcome.getId());
                map.put(JdbcUtil.COLUMN_NAME_TIME, outcome.getTime());
                map.put(JdbcUtil.COLUMN_NAME_VALUE, outcome.getValue());
                map.put(JdbcUtil.COLUMN_NAME_NAME, outcome.getName());
                map.put(JdbcUtil.COLUMN_NAME_NEW_BALANCE, outcome.getNewBalance().getId());
                map.put(JdbcUtil.COLUMN_NAME_INCOME_CATEGORY, "");
                map.put(JdbcUtil.COLUMN_NAME_OUTCOME_CATEGORY, outcome.getOutcomeCategory());
                write(JdbcUtil.updateTableSet(Transaction.class.getSimpleName(), map, id));
            }
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, transaction, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, transaction, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

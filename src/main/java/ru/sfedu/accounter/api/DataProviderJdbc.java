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

public class DataProviderJdbc extends AbstractDataProvider {
    private final String hostname = ConfigurationUtil.getConfigurationEntry(Constants.H2_HOSTNAME);
    private final String username = ConfigurationUtil.getConfigurationEntry(Constants.H2_USERNAME);
    private final String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderJdbc() throws IOException, SQLException {
        initDb();
    }

    private void initDb() throws SQLException {
        write(JdbcUtil.CREATE_TABLE_BALANCE);
        write(JdbcUtil.CREATE_TABLE_PLAN);
        write(JdbcUtil.CREATE_TABLE_TRANSACTION);
    }

    private List<Balance> readBalance(String sql) throws SQLException {
        List<Balance> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Balance balance = new Balance(
                    resultSet.getLong(1),
                    resultSet.getDouble(2));
            list.add(balance);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private List<Plan> readPlan(String sql) throws SQLException {
        List<Plan> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Plan plan = new Plan(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    getTransactionById(resultSet.getLong(4)));
            list.add(plan);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private List<Transaction> readTransaction(String sql) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Transaction transaction = new Transaction() {
            };
            if (resultSet.getString(5).isBlank())
                transaction = new Income(
                        resultSet.getLong(1),
                        resultSet.getDouble(2),
                        resultSet.getString(3),
                        IncomeCategory.valueOf(resultSet.getString(4)));
            if (resultSet.getString(4).isBlank())
                transaction = new Outcome(
                        resultSet.getLong(1),
                        resultSet.getDouble(2),
                        resultSet.getString(3),
                        OutcomeCategory.valueOf(resultSet.getString(5)));
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

    @Override
    public List<Balance> getAllBalance() {
        List<Balance> list = new ArrayList<>();
        try {
            list = readBalance(JdbcUtil.selectAllFromTable(Balance.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    public Balance getBalanceById(long id) {
        Balance balance = new Balance();
        try {
            List<Balance> list = readBalance(JdbcUtil.selectFromTableById(balance.getClass().getSimpleName(), id));
            if (!list.isEmpty())
                balance = list.get(0);
        } catch (Exception ignored) {
        }
        return balance;
    }

    @Override
    public Balance appendBalance(Balance balance) {
        if (getBalanceById(balance.getId()) != null)
            balance.setId();
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(balance.getId()));
            list.add(String.valueOf(balance.getValue()));
            write(JdbcUtil.insertIntoTableValues(balance.getClass().getSimpleName(), list));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, balance, Result.State.Error);
        }
        sendLogs(Constants.METHOD_NAME_APPEND, balance, Result.State.Success);
        return balance;
    }

    @Override
    public Result deleteBalance(long id) {
        Balance balance = getBalanceById(id);
        if (balance == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(balance.getClass().getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, balance, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, balance, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(JdbcUtil.COLUMN_NAME_ID, balance.getId());
            map.put(JdbcUtil.COLUMN_NAME_VALUE, balance.getValue());
            map.put(JdbcUtil.COLUMN_NAME_TRANSACTION, balance.getValue());
            write(JdbcUtil.updateTableSet(balance.getClass().getSimpleName(), map, id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, balance, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, balance, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Plan> getAllPlan() {
        List<Plan> list = new ArrayList<>();
        try {
            list = readPlan(JdbcUtil.selectAllFromTable(Plan.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    public Plan getPlanById(long id) {
        Plan plan = new Plan();
        try {
            List<Plan> list = readPlan(JdbcUtil.selectFromTableById(plan.getClass().getSimpleName(), id));
            if (!list.isEmpty())
                plan = list.get(0);
        } catch (Exception ignored) {
        }
        return plan;
    }

    @Override
    public Plan appendPlan(Plan plan) {
        try {
            if (getPlanById(plan.getId()) != null)
                plan.setId();
        } catch (Exception ignored) {
        }
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(plan.getId()));
            list.add(String.valueOf(plan.getPeriod()));
            list.add(String.valueOf(plan.getTransaction().getId()));
            write(JdbcUtil.insertIntoTableValues(plan.getClass().getSimpleName(), list));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, plan, Result.State.Error);
        }
        sendLogs(Constants.METHOD_NAME_APPEND, plan, Result.State.Success);
        return plan;
    }

    @Override
    public Result deletePlan(long id) {
        Plan plan = getPlanById(id);
        if (plan == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(plan.getClass().getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, plan, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, plan, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(JdbcUtil.COLUMN_NAME_ID, plan.getId());
            map.put(JdbcUtil.COLUMN_NAME_PERIOD, plan.getPeriod());
            map.put(JdbcUtil.COLUMN_NAME_TRANSACTION, plan.getTransaction().getId());
            write(JdbcUtil.updateTableSet(plan.getClass().getSimpleName(), map, id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, plan, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, plan, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> list = new ArrayList<>();
        try {
            list = readTransaction(JdbcUtil.selectAllFromTable(Transaction.class.getSimpleName()));
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    public Transaction getTransactionById(long id) {
        Transaction transaction = new Transaction() {
        };
        try {
            List<Transaction> list = readTransaction(JdbcUtil.selectFromTableById(transaction.getClass().getSimpleName(), id));
            if (!list.isEmpty())
                transaction = list.get(0);
        } catch (Exception ignored) {
        }
        return transaction;
    }

    @Override
    public Transaction appendTransaction(Transaction transaction) {
        try {
            if (getTransactionById(transaction.getId()) != null)
                transaction.setId();
        } catch (Exception ignored) {
        }
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(transaction.getId()));
            list.add(String.valueOf(transaction.getValue()));
            list.add(String.valueOf(transaction.getName()));
            if (transaction.getClass().equals(Income.class)) {
                list.add(String.valueOf(((Income) transaction).getCategory()));
                list.add("");
            }
            if (transaction.getClass().equals(Outcome.class)) {
                list.add("");
                list.add(String.valueOf(((Outcome) transaction).getCategory()));
            }
            write(JdbcUtil.insertIntoTableValues(transaction.getClass().getSimpleName(), list));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_APPEND, transaction, Result.State.Error);
        }
        sendLogs(Constants.METHOD_NAME_APPEND, transaction, Result.State.Success);
        return transaction;
    }

    @Override
    public Result deleteTransaction(long id) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write(JdbcUtil.deleteFromTableById(transaction.getClass().getSimpleName(), id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_DELETE, transaction, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_DELETE, transaction, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public Result updateTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(JdbcUtil.COLUMN_NAME_ID, transaction.getId());
            map.put(JdbcUtil.COLUMN_NAME_VALUE, transaction.getValue());
            map.put(JdbcUtil.COLUMN_NAME_NAME, transaction.getName());
            if (transaction.getClass().equals(Income.class)) {
                map.put(JdbcUtil.COLUMN_NAME_INCOME_CATEGORY, ((Income) transaction).getCategory());
                map.put(JdbcUtil.COLUMN_NAME_OUTCOME_CATEGORY, "");
            }
            if (transaction.getClass().equals(Outcome.class)) {
                map.put(JdbcUtil.COLUMN_NAME_INCOME_CATEGORY, "");
                map.put(JdbcUtil.COLUMN_NAME_OUTCOME_CATEGORY, ((Outcome) transaction).getCategory());
            }
            write(JdbcUtil.updateTableSet(transaction.getClass().getSimpleName(), map, id));
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_UPDATE, transaction, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_UPDATE, transaction, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

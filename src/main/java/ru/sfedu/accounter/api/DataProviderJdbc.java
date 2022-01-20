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

    public DataProviderJdbc() throws IOException {
        write(JdbcUtil.CREATE_TABLE_BALANCE);
        write(JdbcUtil.CREATE_TABLE_PLAN);
        write(JdbcUtil.CREATE_TABLE_TRANSACTION);
    }

    private <T> List<T> read(Class<T> type) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(JdbcUtil.selectAllFromTable(type.getSimpleName()));
            list = getData(resultSet, type);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    private <T> List<T> read(Class<T> type, long id) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(JdbcUtil.selectFromTableById(type.getSimpleName(), id));
            list = getData(resultSet, type);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    private <T> List<T> getData(ResultSet resultSet, Class<T> type) throws SQLException {
        List list = new ArrayList<>();
        if (type.equals(Balance.class)) {
            while (resultSet.next()) {
                Balance balance = new Balance();
                balance.setId(resultSet.getLong(1));
                balance.setValue(resultSet.getDouble(2));
                list.add(balance);
            }
        } else if (type.equals(Transaction.class)) {
            while (resultSet.next()) {
                try {
                    Income income = new Income();
                    income.setId(resultSet.getLong(1));
                    income.setValue(resultSet.getDouble(2));
                    income.setName(resultSet.getString(3));
                    income.setCategory(IncomeCategory.valueOf(resultSet.getString(4)));
                    list.add(income);
                } catch (Exception ignored) {
                    try {
                        Outcome outcome = new Outcome();
                        outcome.setId(resultSet.getLong(1));
                        outcome.setValue(resultSet.getDouble(2));
                        outcome.setName(resultSet.getString(3));
                        outcome.setCategory(OutcomeCategory.valueOf(resultSet.getString(4)));
                        list.add(outcome);
                    } catch (Exception ignored1) {
                    }
                }
            }
        } else if (type.equals(Plan.class)) {
            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setId(resultSet.getLong(1));
                plan.setPeriod(resultSet.getLong(2));
                plan.setTransaction(getTransactionById(resultSet.getLong(3)));
                list.add(plan);
            }
        }
        return list;
    }

    private void write(String sql) {
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private <T> Result write(String methodName, T bean, long id) {
        String tableName = bean.getClass().getSuperclass().equals(Object.class)
                ? bean.getClass().getSimpleName()
                : bean.getClass().getSuperclass().getSimpleName();
        String sql = switch (methodName) {
            case Constants.METHOD_NAME_APPEND -> JdbcUtil.insertIntoTableValues(tableName, bean);
            case Constants.METHOD_NAME_DELETE -> JdbcUtil.deleteFromTableById(tableName, id);
            case Constants.METHOD_NAME_UPDATE -> JdbcUtil.updateTableSet(tableName, bean, id);
            default -> "";
        };

        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            sendLogs(methodName, bean, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(methodName, bean, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Balance> getAllBalance() {
        return read(Balance.class);
    }

    @Override
    public Balance getBalanceById(long id) {
        List<Balance> list = read(Balance.class, id);
        return list.isEmpty() ? new Balance() : list.get(0);
    }

    @Override
    public Balance appendBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() != 0)
            balance.setId();
        write(Constants.METHOD_NAME_APPEND, balance, balance.getId());
        return balance;
    }

    @Override
    public Result deleteBalance(long id) {
        if (getBalanceById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_DELETE, getBalanceById(id), id);
    }

    @Override
    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_UPDATE, balance, balance.getId());
    }

    @Override
    public List<Plan> getAllPlan() {
        return read(Plan.class);
    }

    @Override
    public Plan getPlanById(long id) {
        List<Plan> list = read(Plan.class, id);
        return list.isEmpty() ? new Plan() : list.get(0);
    }

    @Override
    public Plan appendPlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() != 0)
            plan.setId();
        write(Constants.METHOD_NAME_APPEND, plan, plan.getId());
        return plan;
    }

    @Override
    public Result deletePlan(long id) {
        if (getPlanById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_DELETE, getPlanById(id), id);
    }

    @Override
    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_UPDATE, plan, plan.getId());
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return read(Transaction.class);
    }

    @Override
    public Transaction getTransactionById(long id) {
        List<Transaction> list = read(Transaction.class, id);
        return list.isEmpty() ? new Transaction(){} : list.get(0);
    }

    @Override
    public Transaction appendTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id).getId() != 0)
            transaction.setId();
        write(Constants.METHOD_NAME_APPEND, transaction, transaction.getId());
        return transaction;
    }

    @Override
    public Result deleteTransaction(long id) {
        if (getTransactionById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_DELETE, getTransactionById(id), id);
    }

    @Override
    public Result updateTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        return write(Constants.METHOD_NAME_UPDATE, transaction, transaction.getId());
    }
}

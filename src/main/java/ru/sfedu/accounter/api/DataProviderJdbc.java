package ru.sfedu.accounter.api;

import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;
import ru.sfedu.accounter.utils.ConfigurationUtil;
import ru.sfedu.accounter.utils.JdbcUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderJdbc extends AbstractDataProvider {
    private final JdbcUtil jdbcUtil = new JdbcUtil();
    private final String hostname = ConfigurationUtil.getConfigurationEntry(Constants.H2_HOSTNAME);
    private final String username = ConfigurationUtil.getConfigurationEntry(Constants.H2_USERNAME);
    private final String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderJdbc() throws IOException {
        try {
            write(jdbcUtil.CREATE_TABLE_BALANCE);
            write(jdbcUtil.CREATE_TABLE_INCOME);
            write(jdbcUtil.CREATE_TABLE_OUTCOME);
            write(jdbcUtil.CREATE_TABLE_PLAN);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private <T> List<T> read(Class<T> type) {
        return read(type, jdbcUtil.selectAllFromTable(type.getSimpleName()));
    }

    private <T> List<T> read(Class<T> type, long id) {
        return read(type, jdbcUtil.selectFromTableById(type.getSimpleName(), id));
    }

    private <T> List<T> read(Class<T> type, String sql) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = getData(type, resultSet);
            log.debug(sql);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    private <T> List<T> getData(Class<T> type, ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        if (type.equals(Balance.class))
            list = getBalance(resultSet);
        else if (type.equals(Income.class))
            list = getIncome(resultSet);
        else if (type.equals(Outcome.class))
            list = getOutcome(resultSet);
        else if (type.equals(Plan.class))
            list = getPlan(resultSet);
        return list;
    }

    private List<Balance> getBalance(ResultSet resultSet) throws SQLException {
        List<Balance> list = new ArrayList<>();
        while (resultSet.next()) {
            Balance balance = new Balance();
            balance.setId(resultSet.getLong(1));
            balance.setValue(resultSet.getDouble(2));
            list.add(balance);
        }
        return list;
    }

    private List<Income> getIncome(ResultSet resultSet) throws SQLException {
        List<Income> list = new ArrayList<>();
        while (resultSet.next()) {
            Income income = new Income();
            income.setId(resultSet.getLong(1));
            income.setValue(resultSet.getDouble(2));
            income.setName(resultSet.getString(3));
            income.setCategory(IncomeCategory.valueOf(resultSet.getString(4)));
            list.add(income);
        }
        return list;
    }

    private List<Outcome> getOutcome(ResultSet resultSet) throws SQLException {
        List<Outcome> list = new ArrayList<>();
        while (resultSet.next()) {
            Outcome outcome = new Outcome();
            outcome.setId(resultSet.getLong(1));
            outcome.setValue(resultSet.getDouble(2));
            outcome.setName(resultSet.getString(3));
            outcome.setCategory(OutcomeCategory.valueOf(resultSet.getString(4)));
            list.add(outcome);
        }
        return list;
    }

    private List<Plan> getPlan(ResultSet resultSet) throws SQLException {
        List<Plan> list = new ArrayList<>();
        while (resultSet.next()) {
            Plan plan = new Plan();
            plan.setId(resultSet.getLong(1));
            plan.setPeriod(resultSet.getLong(2));
            plan.setTransaction(jdbcUtil.stringToInnerTransaction(resultSet.getString(3)));
            list.add(plan);
        }
        return list;
    }

    private void write(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        log.debug(sql);
        connection.close();
        statement.close();
    }

    private <T> Result write(String methodName, T bean, long id) {
        String tableName = bean.getClass().getSimpleName();
        String sql = switch (methodName) {
            case Constants.METHOD_NAME_APPEND -> jdbcUtil.insertIntoTableValues(tableName, bean);
            case Constants.METHOD_NAME_DELETE -> jdbcUtil.deleteFromTableById(tableName, id);
            case Constants.METHOD_NAME_UPDATE -> jdbcUtil.updateTableSet(tableName, bean, id);
            default -> "";
        };

        try {
            write(sql);
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
        if (getBalanceById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_DELETE, getBalanceById(id), id);
    }

    @Override
    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_UPDATE, balance, balance.getId());
    }

    @Override
    public List<Income> getAllIncome() {
        return read(Income.class);
    }

    @Override
    public Income getIncomeById(long id) {
        List<Income> list = read(Income.class, id);
        return list.isEmpty() ? new Income() : list.get(0);
    }

    @Override
    public Income appendIncome(Income income) {
        long id = income.getId();
        if (getIncomeById(id).getId() != 0 || getOutcomeById(id).getId() != 0)
            income.setId();
        write(Constants.METHOD_NAME_APPEND, income, income.getId());
        return income;
    }

    @Override
    public Result deleteIncome(long id) {
        if (getIncomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_DELETE, getIncomeById(id), id);
    }

    @Override
    public Result updateIncome(Income income) {
        long id = income.getId();
        if (getIncomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_UPDATE, income, income.getId());
    }

    @Override
    public List<Outcome> getAllOutcome() {
        return read(Outcome.class);
    }

    @Override
    public Outcome getOutcomeById(long id) {
        List<Outcome> list = read(Outcome.class, id);
        return list.isEmpty() ? new Outcome() : list.get(0);
    }

    @Override
    public Outcome appendOutcome(Outcome outcome) {
        long id = outcome.getId();
        if (getOutcomeById(id).getId() != 0 || getIncomeById(id).getId() != 0)
            outcome.setId();
        write(Constants.METHOD_NAME_APPEND, outcome, outcome.getId());
        return outcome;
    }

    @Override
    public Result deleteOutcome(long id) {
        if (getOutcomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_DELETE, getOutcomeById(id), id);
    }

    @Override
    public Result updateOutcome(Outcome outcome) {
        long id = outcome.getId();
        if (getOutcomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_UPDATE, outcome, outcome.getId());
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
        if (getPlanById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_DELETE, getPlanById(id), id);
    }

    @Override
    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        return write(Constants.METHOD_NAME_UPDATE, plan, plan.getId());
    }
}

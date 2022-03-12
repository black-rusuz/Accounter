package ru.sfedu.accounter.api;

import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Plan;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class FileDataProvider extends AbstractDataProvider {

    public FileDataProvider() throws IOException {
    }

    /**
     * Reads bean list from file.
     *
     * @param type class that needed to read
     * @param <T>  generic class of list entries
     * @return list of read beans
     */
    protected abstract <T> List<T> read(Class<T> type);

    /**
     * Writes list of any beans to file.
     *
     * @param list list of beans to write
     * @param <T>  generic class of list entries
     * @return reading Result (Success/Warning/Error and message)
     */
    protected abstract <T> Result write(List<T> list, Class<T> type, String methodName);

    /**
     * Generates full file name by filePath, bean and fileExtension.
     *
     * @param filePath      path to file declared in environment.properties
     * @param type          bean to work with
     * @param fileExtension file extension declared in environment.properties
     * @param <T>           generic class of bean
     * @return full filename string
     */
    protected <T> String getName(String filePath, Class<T> type, String fileExtension) {
        return filePath + type.getSimpleName() + fileExtension;
    }

    /**
     * Creates File variable to read from/write in. Creates file in filesystem if not exists.
     */
    protected File initFile(String fullFileName) throws IOException {
        File file = new File(fullFileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    @Override
    public List<Balance> getAllBalance() {
        return read(Balance.class) != null ? read(Balance.class) : new ArrayList<>();
    }

    @Override
    public Balance getBalanceById(long id) {
        List<Balance> list = getAllBalance().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? new Balance() : list.get(0);
    }

    @Override
    public Balance appendBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() != 0)
            balance.setId();
        List<Balance> list = getAllBalance();
        list.add(balance);
        write(list, Balance.class, Constants.METHOD_NAME_APPEND);
        return balance;
    }

    @Override
    public Result deleteBalance(long id) {
        if (getBalanceById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Balance> list = getAllBalance();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Balance.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        deleteBalance(id);
        appendBalance(balance);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Income> getAllIncome() {
        return read(Income.class) != null ? read(Income.class) : new ArrayList<>();
    }

    @Override
    public Income getIncomeById(long id) {
        List<Income> list = getAllIncome().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? new Income() : list.get(0);
    }

    @Override
    public Income appendIncome(Income income) {
        long id = income.getId();
        if (getIncomeById(id).getId() != 0 || getOutcomeById(id).getId() != 0)
            income.setId();
        List<Income> list = getAllIncome();
        list.add(income);
        write(list, Income.class, Constants.METHOD_NAME_APPEND);
        return income;
    }

    @Override
    public Result deleteIncome(long id) {
        if (getIncomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Income> list = getAllIncome();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Income.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public Result updateIncome(Income income) {
        long id = income.getId();
        if (getIncomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        deleteIncome(id);
        appendIncome(income);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Outcome> getAllOutcome() {
        return read(Outcome.class) != null ? read(Outcome.class) : new ArrayList<>();
    }

    @Override
    public Outcome getOutcomeById(long id) {
        List<Outcome> list = getAllOutcome().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? new Outcome() : list.get(0);
    }

    @Override
    public Outcome appendOutcome(Outcome outcome) {
        long id = outcome.getId();
        if (getOutcomeById(id).getId() != 0 || getIncomeById(id).getId() != 0)
            outcome.setId();
        List<Outcome> list = getAllOutcome();
        list.add(outcome);
        write(list, Outcome.class, Constants.METHOD_NAME_APPEND);
        return outcome;
    }

    @Override
    public Result deleteOutcome(long id) {
        if (getOutcomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Outcome> list = getAllOutcome();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Outcome.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public Result updateOutcome(Outcome outcome) {
        long id = outcome.getId();
        if (getOutcomeById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        deleteOutcome(id);
        appendOutcome(outcome);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Plan> getAllPlan() {
        return read(Plan.class) != null ? read(Plan.class) : new ArrayList<>();
    }

    @Override
    public Plan getPlanById(long id) {
        List<Plan> list = getAllPlan().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? new Plan() : list.get(0);
    }

    @Override
    public Plan appendPlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() != 0)
            plan.setId();
        List<Plan> list = getAllPlan();
        list.add(plan);
        write(list, Plan.class, Constants.METHOD_NAME_APPEND);
        return plan;
    }

    @Override
    public Result deletePlan(long id) {
        if (getPlanById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Plan> list = getAllPlan();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Plan.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() == 0) {
            log.warn(Constants.RESULT_MESSAGE_NOT_FOUND);
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        deletePlan(id);
        appendPlan(plan);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
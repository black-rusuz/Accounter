package ru.sfedu.accounter.api;

import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileDataProvider extends AbstractDataProvider {

    public FileDataProvider() throws IOException {
    }

    /**
     * Reads bean list from file.
     *
     * @param bean class that needed to read
     * @param <T>  generic class of list entries
     * @return list of read beans
     */
    protected abstract <T> List<T> read(Class<T> bean);

    /**
     * Writes list of any beans to file.
     *
     * @param list list of beans to write
     * @param <T>  generic class of list entries
     * @return reading Result (Success/Warning/Error and message)
     */
    protected abstract <T> Result write(List<T> list, Class<T> bean);

    /**
     * Generates full file name by filePath, bean and fileExtension.
     *
     * @param filePath      path to file declared in environment.properties
     * @param bean          bean to work with
     * @param fileExtension file extension declared in environment.properties
     * @param <T>           generic class of bean
     * @return full filename string
     */
    protected <T> String classToFullFileName(String filePath, Class<T> bean, String fileExtension) {
        String fileName = bean.getSimpleName();
        if (!bean.getSuperclass().equals(Object.class))
            fileName = bean.getSuperclass().getSimpleName();
        return filePath + fileName + fileExtension;
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
        write(list, Balance.class);
        return balance;
    }

    @Override
    public Result deleteBalance(long id) {
        if (getBalanceById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        List<Balance> list = getAllBalance();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Balance.class);
    }

    @Override
    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        deleteBalance(id);
        appendBalance(balance);
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
        write(list, Plan.class);
        return plan;
    }

    @Override
    public Result deletePlan(long id) {
        if (getPlanById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        List<Plan> list = getAllPlan();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Plan.class);
    }

    @Override
    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        deletePlan(id);
        appendPlan(plan);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return read(Transaction.class) != null ? read(Transaction.class) : new ArrayList<>();
    }

    @Override
    public Transaction getTransactionById(long id) {
        List<Transaction> list = getAllTransaction().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? new Transaction() {
        } : list.get(0);
    }

    @Override
    public Transaction appendTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id).getId() != 0)
            transaction.setId();
        List<Transaction> list = getAllTransaction();
        list.add(transaction);
        write(list, Transaction.class);
        return transaction;
    }

    @Override
    public Result deleteTransaction(long id) {
        if (getTransactionById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        List<Transaction> list = getAllTransaction();
        list.removeIf(a -> (a.getId() == id));
        return write(list, Transaction.class);
    }

    @Override
    public Result updateTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id).getId() == 0)
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        deleteTransaction(id);
        appendTransaction(transaction);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

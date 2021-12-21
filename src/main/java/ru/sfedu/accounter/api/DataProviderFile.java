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

public abstract class DataProviderFile extends AbstractDataProvider{

    public DataProviderFile() throws IOException {
    }

    protected abstract <T> List<T> read(Class<T> bean);

    protected abstract <T> Result write(List<T> list);

    protected <T> String classToFullFileName(String filePath, Class<T> bean, String fileExtension) {
        String fileName = bean.getSimpleName();
        if (!bean.getSuperclass().equals(Object.class))
            fileName = bean.getSuperclass().getSimpleName();
        return filePath + fileName + fileExtension;
    }

    protected File initFile(String fullFileName) throws IOException {
        File file = new File(fullFileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        }
        return file;
    }

    public List<Balance> getAllBalance() {
        List<Balance> list = new ArrayList<>();
        try {
            list = read(Balance.class);
        } catch (Exception ignored) {
        }
        return list;
    }

    public Balance getBalanceById(long id) {
        List<Balance> list = getAllBalance().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Balance appendBalance(Balance balance) {
        try {
            if (getBalanceById(balance.getId()) != null)
                balance.setId();
        } catch (Exception ignored) {
        }
        List<Balance> list = getAllBalance();
        list.add(balance);
        write(list);
        return balance;
    }

    public Result deleteBalance(long id) {
        Balance balance = getBalanceById(id);
        if (balance == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Balance> list;
        list = getAllBalance();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    public Result updateBalance(Balance balance) {
        long id = balance.getId();
        if (getBalanceById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deleteBalance(id);
            appendBalance(balance);
        } catch (Exception e) {
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public List<Plan> getAllPlan() {
        List<Plan> list = new ArrayList<>();
        try {
            list = read(Plan.class);
        } catch (Exception ignored) {
        }
        return list;
    }

    public Plan getPlanById(long id) {
        List<Plan> list = getAllPlan().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Plan appendPlan(Plan plan) {
        try {
            if (getPlanById(plan.getId()) != null)
                plan.setId();
        } catch (Exception ignored) {
        }
        List<Plan> list = getAllPlan();
        list.add(plan);
        write(list);
        return plan;
    }

    public Result deletePlan(long id) {
        Plan plan = getPlanById(id);
        if (plan == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Plan> list;
        list = getAllPlan();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    public Result updatePlan(Plan plan) {
        long id = plan.getId();
        if (getPlanById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deletePlan(id);
            appendPlan(plan);
        } catch (Exception e) {
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> list = new ArrayList<>();
        try {
            list = read(Transaction.class);
        } catch (Exception ignored) {
        }
        return list;
    }

    public Transaction getTransactionById(long id) {
        List<Transaction> list = getAllTransaction().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Transaction appendTransaction(Transaction transaction) {
        try {
            if (getTransactionById(transaction.getId()) != null)
                transaction.setId();
        } catch (Exception ignored) {
        }
        List<Transaction> list = getAllTransaction();
        list.add(transaction);
        write(list);
        return transaction;
    }

    public Result deleteTransaction(long id) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Transaction> list;
        list = getAllTransaction();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    public Result updateTransaction(Transaction transaction) {
        long id = transaction.getId();
        if (getTransactionById(id) == null) {
            return new Result(Result.State.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deleteTransaction(id);
            appendTransaction(transaction);
        } catch (Exception e) {
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

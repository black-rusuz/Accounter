package ru.sfedu.accounter.api;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderCsv extends AbstractDataProvider implements IDataProvider {
    private final String CSV_PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    private final String CSV_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.CSV_EXTENSION);


    protected DataProviderCsv() throws IOException {
    }

    private File initFile(String name) throws IOException {
        String path = CSV_PATH + name + CSV_EXTENSION;
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        }
        return file;
    }

    private <T> List<T> read(Class<T> bean) {
        String name = bean.getSimpleName();
        List<T> list = new ArrayList<>();
        if (!bean.getSuperclass().equals(Object.class))
            name = bean.getSuperclass().getSimpleName();
        try {
            File file = initFile(name);
            FileReader fileReader = new FileReader(file);
            list = new CsvToBeanBuilder<T>(fileReader).withType(bean).build().parse();
            fileReader.close();
        } catch (Exception ignored) {
        }
        return list;
    }

    private <T> Result write(List<T> list) {
        String name = list.get(0).getClass().getSimpleName();
        if (!list.get(0).getClass().getSuperclass().equals(Object.class))
            name = list.get(0).getClass().getSuperclass().getSimpleName();
        try {
            File file = initFile(name);
            FileWriter fileWriter = new FileWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(fileWriter).build();
            beanToCsv.write(list);
            fileWriter.close();
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_WRITE, list.get(list.size() - 1), Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_WRITE, list.get(list.size() - 1), Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
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

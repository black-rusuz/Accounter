package ru.sfedu.accounter.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.XmlWrapper;
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

public class DataProviderXml extends AbstractDataProvider implements IDataProvider {
    private final String XML_PATH = ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH);
    private final String XML_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.XML_EXTENSION);
    private final Serializer serializer = new Persister();

    public DataProviderXml() throws IOException {
    }

    private File initFile(String name) throws IOException {
        String path = XML_PATH + name + XML_EXTENSION;
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
        List<T> list = new ArrayList<T>();
        if (!bean.getSuperclass().equals(Object.class))
            name = bean.getSuperclass().getSimpleName();
        try {
            File file = initFile(name);
            FileReader fileReader = new FileReader(file);
            XmlWrapper<T> xmlWrapper = serializer.read(XmlWrapper.class, fileReader);
            fileReader.close();
            list = xmlWrapper.getList();
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
            Serializer serializer = new Persister();
            XmlWrapper<T> xmlWrapper = new XmlWrapper<T>(list);
            serializer.write(xmlWrapper, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_WRITE, list.get(list.size() - 1), Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_WRITE, list.get(list.size() - 1), Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public List<Balance> getAllBalance() {
        ArrayList<Balance> list = new ArrayList<>();
        try {
            list = (ArrayList<Balance>) read(Balance.class);
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
        ArrayList<Plan> list = new ArrayList<>();
        try {
            list = (ArrayList<Plan>) read(Plan.class);
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
        ArrayList<Transaction> list = new ArrayList<>();
        try {
            list = (ArrayList<Transaction>) read(Transaction.class);
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

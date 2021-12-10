package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.ResultType;
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

public class DataProviderXml implements IDataProvider {
    private final String PATH_TO_XML = ConfigurationUtil.getConfigurationEntry(Constants.PATH_TO_XML);
    private final String XML_FILE_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE_EXTENSION);

    private static final Serializer serializer = new Persister();
    private static final Logger log = LogManager.getLogger(DataProviderXml.class);

    public DataProviderXml() throws IOException {}

    private File initFile(String name) throws IOException {
        String path = PATH_TO_XML + name + XML_FILE_EXTENSION;
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
        if (!bean.getSuperclass().equals(Object.class))
            name = bean.getSuperclass().getSimpleName();
        try {
            File file = initFile(name);
            FileReader fileReader = new FileReader(file);
            XmlWrapper<T> xmlWrapper = serializer.read(XmlWrapper.class, fileReader);
            fileReader.close();
            return xmlWrapper.getList();
        } catch (Exception ignored) {}
        return new ArrayList<T>();
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
            return new Result(ResultType.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        return new Result(ResultType.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Balance> getAllBalance() {
        try {
            return read(Balance.class);
        } catch (Exception ignored) {}
        return new ArrayList<Balance>();
    }

    @Override
    public Balance getBalanceById(long id) {
        List<Balance> list = getAllBalance().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Balance appendBalance(Balance balance) {
        try {
            if (getBalanceById(balance.getId()) != null)
                balance.setId();
        } catch (Exception ignored) {}
        List<Balance> list = getAllBalance();
        list.add(balance);
        write(list);
        return balance;
    }

    @Override
    public Result deleteBalance(long id) {
        if (getBalanceById(id) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Balance> list;
        list = getAllBalance();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    @Override
    public Result updateBalance(Balance balance) {
        if (getBalanceById(balance.getId()) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deleteBalance(balance.getId());
            appendBalance(balance);
        } catch (Exception e) {
            return new Result(ResultType.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(ResultType.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Plan> getAllPlan() {
        try {
            return read(Plan.class);
        } catch (Exception ignored) {}
        return new ArrayList<Plan>();
    }

    @Override
    public Plan getPlanById(long id) {
        List<Plan> list = getAllPlan().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Plan appendPlan(Plan plan) {
        try {
            if (getPlanById(plan.getId()) != null)
                plan.setId();
        } catch (Exception ignored) {}
        List<Plan> list = getAllPlan();
        list.add(plan);
        write(list);
        return plan;
    }

    @Override
    public Result deletePlan(long id) {
        if (getPlanById(id) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Plan> list;
        list = getAllPlan();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    @Override
    public Result updatePlan(Plan plan) {
        if (getPlanById(plan.getId()) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deletePlan(plan.getId());
            appendPlan(plan);
        } catch (Exception e) {
            return new Result(ResultType.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(ResultType.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        try {
            return read(Transaction.class);
        } catch (Exception ignored) {}
        return new ArrayList<Transaction>();
    }

    @Override
    public Transaction getTransactionById(long id) {
        List<Transaction> list = getAllTransaction().stream().filter(a -> a.getId() == id).toList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Transaction appendTransaction(Transaction transaction) {
        try {
            if (getTransactionById(transaction.getId()) != null)
                transaction.setId();
        } catch (Exception ignored) {}
        List<Transaction> list = getAllTransaction();
        list.add(transaction);
        write(list);
        return transaction;
    }

    @Override
    public Result deleteTransaction(long id) {
        if (getTransactionById(id) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Transaction> list;
        list = getAllTransaction();
        list.removeIf(a -> (a.getId() == id));
        return write(list);
    }

    @Override
    public Result updateTransaction(Transaction transaction) {
        if (getTransactionById(transaction.getId()) == null) {
            return new Result(ResultType.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            deleteTransaction(transaction.getId());
            appendTransaction(transaction);
        } catch (Exception e) {
            return new Result(ResultType.Error, Constants.RESULT_MESSAGE_WRITING_ERROR);
        }
        return new Result(ResultType.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}

















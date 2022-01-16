package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.HistoryContent;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.*;
import ru.sfedu.accounter.utils.ConfigurationUtil;
import ru.sfedu.accounter.utils.MongoUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractDataProvider implements IDataProvider {
    protected static final Logger log = LogManager.getLogger(AbstractDataProvider.class);
    private final String MONGO_DB_DEFAULT_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB_DEFAULT_ACTOR);

    protected AbstractDataProvider() throws IOException {
    }

    /**
     * Sends logs to MongoDB cluster declared in environment.properties
     *
     * @param methodName method that called sending
     * @param bean       last bean working with
     * @param state      method result
     */
    protected void sendLogs(String methodName, Object bean, Result.State state) {
        HistoryContent historyContent = new HistoryContent(
                UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_DB_DEFAULT_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                state);
        // TODO: Раскомментировать
        //MongoUtil.saveToLog(historyContent);
    }



    // TODO: Сделать дефолтные значения
    private final String DEFAULT_PLAN_NAME = ConfigurationUtil.getConfigurationEntry(Constants.DEFAULT_PLAN_NAME);
    private final long DEFAULT_PLAN_PERIOD = 1L;

    // TODO: Пофиксить функции, таблицу детализации, добавить логгирование и чота ещё
    /**
     * Root use case for managing current balance
     *
     * @param action        what you want to do next: repeat or plan transaction
     * @param transactionId ID of chosen transaction to apply your action
     */
    public void manageBalance(String action, long transactionId) {
        calculateBalance();
        displayIncomesAndOutcomes();
        if (action.equalsIgnoreCase(Constants.REPEAT))
            repeatTransaction(transactionId);
        if (action.equalsIgnoreCase(Constants.PLAN))
            makePlanBasedOnTransaction(transactionId);
    }

    /**
     * Calculates current balance using all written transactions and appends it to Balance list
     *
     * @return current balance value
     */
    public double calculateBalance() {
        List<Transaction> list = getAllTransaction();
        double balanceValue = 0;
        for (Transaction transaction : list) {
            if (transaction.getClass().equals(Income.class))
                balanceValue += transaction.getValue();
            if (transaction.getClass().equals(Outcome.class))
                balanceValue -= transaction.getValue();
        }
        Balance balance = new Balance(balanceValue);
        appendBalance(balance);
        return balance.getValue();
    }

    /**
     * Displays all written transactions
     *
     * @return list of all transactions
     */
    public String displayIncomesAndOutcomes() {
        List<Transaction> list = getAllTransaction();
        List<String> strings = list.stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
        return String.join("\n", strings);
    }

    /**
     * Repeats selected transaction
     *
     * @param transactionId chosen ID of transaction to repeat
     * @return true if transaction appended successfully
     */
    public boolean repeatTransaction(long transactionId) {
        return appendTransaction(getTransactionById(transactionId)) != null;
    }

    /**
     * Creates plan based on selected transaction
     *
     * @param transactionId chosen ID of transaction to plan
     * @return true if transaction appended successfully
     */
    public boolean makePlanBasedOnTransaction(long transactionId) {
        return appendPlan(new Plan(1L, getTransactionById(transactionId))) != null;
    }

    /**
     * Root use case for managing current balance
     *
     * @param planId chosen ID of plan to repeat now
     */
    public void managePlans(long planId) {
        displayPlans();
        executePlanNow(planId);
    }
    public void managePlans() {
        displayPlans();
    }

    /**
     * Displays all written plans
     *
     * @return formatted string of them
     */
    public String displayPlans() {
        List<Plan> list = getAllPlan();
        List<String> strings = list.stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
        return String.join("\n", strings);
    }

    /**
     * Append transaction of selected plan
     *
     * @param planId chosen plan ID to execute now
     * @return true if plan appended successfully
     */
    public boolean executePlanNow(long planId) {
        return appendTransaction(getPlanById(planId).getTransaction()) != null;
    }
}

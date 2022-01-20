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
import java.util.Optional;
import java.util.UUID;

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

    // TODO: Сделать функции перевода ms в Period и выводить красиво
    private final long DEFAULT_PLAN_PERIOD = (long) 1000 * 60 * 60 * 24 * 30;       // One month

    // TODO: Пофиксить функции, таблицу детализации, добавить логгирование и чота ещё

    /**
     * Root use case for managing current balance
     *
     * @param action        what you want to do next: repeat or plan transaction
     * @param transactionId ID of chosen transaction to apply your action
     * @return list of balances history
     */
    public List<Balance> manageBalance(String action, long transactionId) {
        List<Balance> balancesHistory = getAllBalance();
        calculateBalance();
        displayIncomesAndOutcomes();
        if (action.equalsIgnoreCase(Constants.REPEAT))
            repeatTransaction(transactionId);
        if (action.equalsIgnoreCase(Constants.PLAN))
            makePlanBasedOnTransaction(transactionId);
        return balancesHistory;
    }

    /**
     * Calculates current balance using all written transactions and appends it to Balance list
     *
     * @return Optional of new balance
     */
    public Optional<Balance> calculateBalance() {
        List<Transaction> transactions = getAllTransaction();
        double balanceValue = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getClass().equals(Income.class))
                balanceValue += transaction.getValue();
            if (transaction.getClass().equals(Outcome.class))
                balanceValue -= transaction.getValue();
        }
        Balance newBalance = appendBalance(new Balance(balanceValue));
        log.info(Constants.CLI_CURRENT_BALANCE + newBalance.getValue());
        return Optional.of(newBalance);
    }

    /**
     * Displays all written transactions
     *
     * @return list of all existing transactions
     */
    public List<Transaction> displayIncomesAndOutcomes() {
        List<Transaction> transactions = getAllTransaction();
        // TODO: Выводить красиво
        log.info(Constants.CLI_ALL_TRANSACTIONS + transactions);
        return transactions;
    }

    /**
     * Repeats selected transaction
     *
     * @param transactionId chosen ID of transaction to repeat
     * @return true if transaction appended successfully
     */
    public Optional<Transaction> repeatTransaction(long transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        transaction = appendTransaction(transaction);
        return Optional.of(transaction);
    }

    /**
     * Creates plan based on selected transaction
     *
     * @param transactionId chosen ID of transaction to plan
     * @return Optional of new plan
     */
    public Optional<Plan> makePlanBasedOnTransaction(long transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        Plan newPlan = new Plan(DEFAULT_PLAN_PERIOD, transaction);
        newPlan = appendPlan(newPlan);
        return Optional.of(newPlan);
    }

    /**
     * Root use case for managing existing plans
     *
     * @param planId  ID of chosen plan
     * @param execute if true plan will be executed now
     * @return List of all existing plans
     */
    public List<Plan> managePlans(long planId, boolean execute) {
        List<Plan> plans = displayPlans();
        if (execute)
            executePlanNow(planId);
        return plans;
    }

    /**
     * Displays all written plans
     *
     * @return formatted string of them
     */
    public List<Plan> displayPlans() {
        List<Plan> plans = getAllPlan();
        // TODO: Выводить красиво
        log.info(Constants.CLI_ALL_PLANS + plans);
        return plans;
    }

    /**
     * Append transaction of selected plan
     *
     * @param planId chosen plan ID to execute now
     * @return Optional of transaction of executed plan
     */
    public Optional<Transaction> executePlanNow(long planId) {
        Transaction transaction = getPlanById(planId).getTransaction();
        transaction = appendTransaction(transaction);
        return Optional.of(transaction);
    }
}

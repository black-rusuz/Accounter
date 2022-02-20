package ru.sfedu.accounter.lab1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.lab1.model.HistoryContent;
import ru.sfedu.accounter.lab1.model.Result;
import ru.sfedu.accounter.lab1.model.beans.*;
import ru.sfedu.accounter.utils.ConfigurationUtil;
import ru.sfedu.accounter.utils.MongoUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDataProvider {
    protected static final Logger log = LogManager.getLogger(AbstractDataProvider.class);
    private final Boolean MONGO_DB_ENABLE_LOGGING = Boolean.parseBoolean(
            ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB_ENABLE_LOGGING));
    private final String MONGO_DB_DEFAULT_ACTOR =
            ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB_DEFAULT_ACTOR);
    private final long DEFAULT_PLAN_PERIOD = (long) 1000 * 60 * 60 * 24 * 30;       // One month

    protected AbstractDataProvider() throws IOException {
    }

    public abstract List<Balance> getAllBalance();
    public abstract Balance getBalanceById(long id);
    public abstract Balance appendBalance(Balance balance);
    public abstract Result deleteBalance(long id);
    public abstract Result updateBalance(Balance balance);

    public abstract List<Income> getAllIncome();
    public abstract Income getIncomeById(long id);
    public abstract Income appendIncome(Income income);
    public abstract Result deleteIncome(long id);
    public abstract Result updateIncome(Income income);

    public abstract List<Outcome> getAllOutcome();
    public abstract Outcome getOutcomeById(long id);
    public abstract Outcome appendOutcome(Outcome outcome);
    public abstract Result deleteOutcome(long id);
    public abstract Result updateOutcome(Outcome outcome);

    public abstract List<Plan> getAllPlan();
    public abstract Plan getPlanById(long id);
    public abstract Plan appendPlan(Plan plan);
    public abstract Result deletePlan(long id);
    public abstract Result updatePlan(Plan plan);

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
        if (MONGO_DB_ENABLE_LOGGING) MongoUtil.saveToLog(historyContent);
    }

    /**
     * Root use case for managing current balance
     *
     * @param action        what you want to do next: repeat or plan transaction
     * @param transactionId ID of chosen transaction to apply your action
     * @return list of balances history
     */
    public List<Balance> manageBalance(String action, long transactionId) {
        if (action.equalsIgnoreCase(Constants.REPEAT))
            repeatTransaction(transactionId);
        if (action.equalsIgnoreCase(Constants.PLAN))
            makePlanBasedOnTransaction(transactionId);
        List<Balance> balancesHistory = getAllBalance();
        calculateBalance();
        displayIncomesAndOutcomes();
        return balancesHistory;
    }

    /**
     * Calculates current balance using all written transactions and appends it to Balance list
     *
     * @return Optional of new balance
     */
    public Optional<Balance> calculateBalance() {
        double balanceValue = 0;
        for (Income income : getAllIncome())
            balanceValue += income.getValue();
        for (Outcome outcome : getAllOutcome())
            balanceValue -= outcome.getValue();
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
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(getAllIncome());
        transactions.addAll(getAllOutcome());
        transactions.sort(Comparator.comparing(Transaction::getId));
        log.info(Constants.CLI_ALL_TRANSACTIONS
                + transactions.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n")));
        return transactions;
    }

    /**
     * Repeats selected transaction
     *
     * @param transactionId chosen ID of transaction to repeat
     * @return true if transaction appended successfully
     */
    public Optional<Transaction> repeatTransaction(long transactionId) {
        Transaction transaction = new Transaction() {
        };
        if (!getIncomeById(transactionId).equals(new Income())) {
            transaction = getIncomeById(transactionId);
            transaction = appendIncome((Income) transaction);
        } else if (!getOutcomeById(transactionId).equals(new Outcome())) {
            transaction = getOutcomeById(transactionId);
            transaction = appendOutcome((Outcome) transaction);
        }
        return Optional.of(transaction);
    }

    /**
     * Creates plan based on selected transaction
     *
     * @param transactionId chosen ID of transaction to plan
     * @return Optional of new plan
     */
    public Optional<Plan> makePlanBasedOnTransaction(long transactionId) {
        Transaction transaction = new Transaction() {
        };
        if (!getIncomeById(transactionId).equals(new Income()))
            transaction = getIncomeById(transactionId);
        else if (!getOutcomeById(transactionId).equals(new Outcome()))
            transaction = getOutcomeById(transactionId);
        else {
            log.error(Constants.RESULT_MESSAGE_NOT_FOUND);
            System.exit(0);
        }
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
        if (execute) executePlanNow(planId);
        return plans;
    }

    /**
     * Displays all written plans
     *
     * @return formatted string of them
     */
    public List<Plan> displayPlans() {
        List<Plan> plans = getAllPlan();
        log.info(Constants.CLI_ALL_PLANS
                + plans.stream()
                .map(Plan::toString)
                .collect(Collectors.joining("\n")));
        return plans;
    }

    /**
     * Appends transaction of selected plan
     *
     * @param planId chosen plan ID to execute now
     * @return Optional of transaction of executed plan
     */
    public Optional<Transaction> executePlanNow(long planId) {
        Transaction transaction = getPlanById(planId).getTransaction();
        if (transaction.getClass().equals(Income.class))
            transaction = appendIncome((Income) transaction);
        else if (transaction.getClass().equals(Outcome.class))
            transaction = appendOutcome((Outcome) transaction);
        return Optional.of(transaction);
    }
}

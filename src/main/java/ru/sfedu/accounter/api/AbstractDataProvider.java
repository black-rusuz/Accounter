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
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDataProvider implements IDataProvider {
    protected static final Logger log = LogManager.getLogger(IDataProvider.class);
    private final Boolean MONGO_DB_ENABLE_LOGGING = Boolean.parseBoolean(
            ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB_ENABLE_LOGGING));
    private final String MONGO_DB_DEFAULT_ACTOR =
            ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB_DEFAULT_ACTOR);
    private final long DEFAULT_PLAN_PERIOD = (long) 1000 * 60 * 60 * 24 * 30;       // One month

    protected AbstractDataProvider() throws IOException {
    }

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

    @Override
    public List<Balance> manageBalance(String action, long transactionId) {
        if (action.equalsIgnoreCase(Constants.CLI_ARG_REPEAT))
            repeatTransaction(transactionId);
        List<Balance> balancesHistory = getAllBalance();
        calculateBalance();
        displayIncomesAndOutcomes();
        if (action.equalsIgnoreCase(Constants.CLI_ARG_PLAN))
            makePlanBasedOnTransaction(transactionId);
        return balancesHistory;
    }

    @Override
    public Optional<Balance> calculateBalance() {
        double balanceValue = 0;
        for (Income income : getAllIncome())
            balanceValue += income.getValue();
        for (Outcome outcome : getAllOutcome())
            balanceValue -= outcome.getValue();
        Balance newBalance = appendBalance(new Balance(balanceValue));
        log.info(Constants.CLI_MESSAGE_CURRENT_BALANCE + newBalance.getValue());
        return Optional.of(newBalance);
    }

    @Override
    public List<Transaction> displayIncomesAndOutcomes() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(getAllIncome());
        transactions.addAll(getAllOutcome());
        transactions.sort(Comparator.comparing(Transaction::getId));
        log.info(Constants.CLI_MESSAGE_ALL_TRANSACTIONS
                + transactions.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n")));
        return transactions;
    }

    @Override
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

    @Override
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
        log.info(Constants.CLI_MESSAGE_CREATED_PLAN + newPlan);
        return Optional.of(newPlan);
    }

    @Override
    public List<Plan> managePlans(long planId, boolean execute) {
        List<Plan> plans = displayPlans();
        if (execute) executePlanNow(planId);
        return plans;
    }

    @Override
    public List<Plan> displayPlans() {
        List<Plan> plans = getAllPlan();
        log.info(Constants.CLI_MESSAGE_ALL_PLANS
                + plans.stream()
                .map(Plan::toString)
                .collect(Collectors.joining("\n")));
        return plans;
    }

    @Override
    public Optional<Transaction> executePlanNow(long planId) {
        Transaction transaction = getPlanById(planId).getTransaction();
        if (transaction.getClass().equals(Income.class))
            transaction = appendIncome((Income) transaction);
        else if (transaction.getClass().equals(Outcome.class))
            transaction = appendOutcome((Outcome) transaction);
        log.info(Constants.CLI_MESSAGE_APPENDED_TRANSACTION + transaction);
        return Optional.of(transaction);
    }
}

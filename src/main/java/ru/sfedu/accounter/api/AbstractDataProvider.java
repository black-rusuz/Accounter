package ru.sfedu.accounter.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.HistoryContent;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;
import ru.sfedu.accounter.utils.MongoUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public abstract class AbstractDataProvider implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderXml.class);

    protected void sendLogs(String methodName, Object bean, Result.State state) {
        HistoryContent historyContent = new HistoryContent(
                UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                Constants.MONGO_DB_DEFAULT_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                state);
        MongoUtil.saveToLog(historyContent);
    }

    public void manageBalance() {
        log.info(calculateBalance());
        log.info(displayIncomesAndOutcomes());
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        long transactionId = scanner.nextLong();
        switch (option) {
            case 1: log.info(repeatTransaction(transactionId));
            case 2: log.info(makePlanBasedOnTransaction(transactionId));
        }
    }

    public Balance calculateBalance() {
        List<Balance> list = getAllBalance();
        return list.get(list.size() - 1);
    }

    public List<Transaction> displayIncomesAndOutcomes() {
        return getAllTransaction();
    }

    public Transaction repeatTransaction(long transactionId) {
        return appendTransaction(getTransactionById(transactionId));
    }

    public Plan makePlanBasedOnTransaction(long transactionId) {
        Scanner scanner = new Scanner(System.in);
        String startDate = scanner.next();
        String name = scanner.next();
        String period = scanner.next();
        return appendPlan(new Plan(startDate, name, period, getTransactionById(transactionId)));
    }

    public void managePlans() {
        log.info(displayPlans());
        Scanner scanner = new Scanner(System.in);
        boolean execute = scanner.nextBoolean();
        long planId = scanner.nextLong();
        if (execute)
            executePlanNow(planId);
    }

    public List<Plan> displayPlans() {
        return getAllPlan();
    }

    public Transaction executePlanNow(long planId) {
        return appendTransaction(getPlanById(planId).getTransaction());
    }
}

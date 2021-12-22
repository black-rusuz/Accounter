package ru.sfedu.accounter.api;

import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;

import java.util.List;

public interface IDataProvider {

    List<Balance> getAllBalance();
    Balance getBalanceById(long id);
    Balance appendBalance(Balance balance);
    Result deleteBalance(long id);
    Result updateBalance(Balance balance);

    List<Plan> getAllPlan();
    Plan getPlanById(long id);
    Plan appendPlan(Plan plan);
    Result deletePlan(long id);
    Result updatePlan(Plan plan);

    List<Transaction> getAllTransaction();
    Transaction getTransactionById(long id);
    Transaction appendTransaction(Transaction transaction);
    Result deleteTransaction(long id);
    Result updateTransaction(Transaction transaction);

}

package ru.sfedu.accounter.api;

import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.beans.*;

import java.util.List;
import java.util.Optional;

public interface IDataProvider {

    List<Balance> getAllBalance();
    Balance getBalanceById(long id);
    Balance appendBalance(Balance balance);
    Result deleteBalance(long id);
    Result updateBalance(Balance balance);

    List<Income> getAllIncome();
    Income getIncomeById(long id);
    Income appendIncome(Income income);
    Result deleteIncome(long id);
    Result updateIncome(Income income);

    List<Outcome> getAllOutcome();
    Outcome getOutcomeById(long id);
    Outcome appendOutcome(Outcome outcome);
    Result deleteOutcome(long id);
    Result updateOutcome(Outcome outcome);

    List<Plan> getAllPlan();
    Plan getPlanById(long id);
    Plan appendPlan(Plan plan);
    Result deletePlan(long id);
    Result updatePlan(Plan plan);

    /**
     * Root use case for managing current balance
     *
     * @param action        what you want to do next: repeat or plan transaction
     * @param transactionId ID of chosen transaction to apply your action
     * @return list of balances history
     */
    List<Balance> manageBalance(String action, long transactionId);

    /**
     * Calculates current balance using all written transactions and appends it to Balance list
     *
     * @return Optional of new balance
     */
    Optional<Balance> calculateBalance();

    /**
     * Displays all written transactions
     *
     * @return list of all existing transactions
     */
    List<Transaction> displayIncomesAndOutcomes();

    /**
     * Repeats selected transaction
     *
     * @param transactionId chosen ID of transaction to repeat
     * @return true if transaction appended successfully
     */
    Optional<Transaction> repeatTransaction(long transactionId);

    /**
     * Creates plan based on selected transaction
     *
     * @param transactionId chosen ID of transaction to plan
     * @return Optional of new plan
     */
    Optional<Plan> makePlanBasedOnTransaction(long transactionId);

    /**
     * Root use case for managing existing plans
     *
     * @param planId  ID of chosen plan
     * @param execute if true plan will be executed now
     * @return List of all existing plans
     */
    List<Plan> managePlans(long planId, boolean execute);

    /**
     * Displays all written plans
     *
     * @return formatted string of them
     */
    List<Plan> displayPlans();

    /**
     * Appends transaction of selected plan
     *
     * @param planId chosen plan ID to execute now
     * @return Optional of transaction of executed plan
     */
    Optional<Transaction> executePlanNow(long planId);
}

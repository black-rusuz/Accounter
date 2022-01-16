package ru.sfedu.accounter.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Transaction;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

public class TransactionConverter extends AbstractBeanField<Transaction, String> {
    private final String fieldsDelimiter = "::";

    @Override
    protected Transaction convert(String s) {
        String[] parsed = s.split(fieldsDelimiter);
        try {
            return new Income(Long.parseLong(parsed[0]), Long.parseLong(parsed[1]), parsed[2], IncomeCategory.valueOf(parsed[3]));
        } catch (Exception ignored) {
            try {
                return new Outcome(Long.parseLong(parsed[0]), Long.parseLong(parsed[1]), parsed[2], OutcomeCategory.valueOf(parsed[3]));
            } catch (Exception ignored1) {
            }
        }
        return new Transaction() {
        };
    }

    @Override
    public String convertToWrite(Object object) {
        if (object.getClass().equals(Income.class)) {
            Income income = (Income) object;
            return income.getId() + fieldsDelimiter +
                    income.getValue() + fieldsDelimiter +
                    income.getName() + fieldsDelimiter +
                    income.getCategory();
        } else if (object.getClass().equals(Outcome.class)) {
            Outcome outcome = (Outcome) object;
            return outcome.getId() + fieldsDelimiter +
                    outcome.getValue() + fieldsDelimiter +
                    outcome.getName() + fieldsDelimiter +
                    outcome.getCategory();
        } else return "";
    }
}

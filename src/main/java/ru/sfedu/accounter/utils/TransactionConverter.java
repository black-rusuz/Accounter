package ru.sfedu.accounter.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Transaction;
import ru.sfedu.accounter.model.enums.IncomeCategory;
import ru.sfedu.accounter.model.enums.OutcomeCategory;

public class TransactionConverter extends AbstractBeanField<Transaction, String> {
    public static final String fieldsDelimiter = "::";

    @Override
    public Transaction convert(String s) {
        String[] parsed = s.split(fieldsDelimiter);
        try {
            Income income = new Income();
            income.setId(Long.parseLong(parsed[0]));
            income.setValue(Double.parseDouble(parsed[1]));
            income.setName(parsed[2]);
            income.setCategory(IncomeCategory.valueOf(parsed[3]));
            return income;
        } catch (Exception ignored) {
            try {
                Outcome outcome = new Outcome();
                outcome.setId(Long.parseLong(parsed[0]));
                outcome.setValue(Double.parseDouble(parsed[1]));
                outcome.setName(parsed[2]);
                outcome.setCategory(OutcomeCategory.valueOf(parsed[3]));
                return outcome;
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
            return income.getId() + fieldsDelimiter
                    + income.getValue() + fieldsDelimiter
                    + income.getName() + fieldsDelimiter
                    + income.getCategory();
        } else if (object.getClass().equals(Outcome.class)) {
            Outcome outcome = (Outcome) object;
            return outcome.getId() + fieldsDelimiter
                    + outcome.getValue() + fieldsDelimiter
                    + outcome.getName() + fieldsDelimiter
                    + outcome.getCategory();
        } else return "";
    }
}

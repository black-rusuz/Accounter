package ru.sfedu.accounter.lab4.bean;

import java.io.Serializable;
import java.util.List;

public class Balance implements Serializable {
    private long id;
    private List<Balance> balanceList;

    public Balance() {
    }

    public Balance(List<Balance> balanceList) {
        setId();
        setBalanceList(balanceList);
    }

    public Balance(long id, List<Balance> incomeList) {
        setId(id);
        setBalanceList(incomeList);
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = System.currentTimeMillis();
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }
}

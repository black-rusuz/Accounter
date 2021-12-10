package ru.sfedu.accounter.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Plan;
import ru.sfedu.accounter.model.beans.Transaction;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(entry = "Balance", inline = true, type = Balance.class),
            @ElementList(entry = "Plan", inline = true, type = Plan.class),
            @ElementList(entry = "Transaction", inline = true, type = Transaction.class),
    })
    private List<T> list;

    public XmlWrapper() {};

    public XmlWrapper(List<T> list) {
        setList(list);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

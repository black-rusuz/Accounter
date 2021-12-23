package ru.sfedu.accounter.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.accounter.model.beans.Balance;
import ru.sfedu.accounter.model.beans.Income;
import ru.sfedu.accounter.model.beans.Outcome;
import ru.sfedu.accounter.model.beans.Plan;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(entry = "Balance", inline = true, required = false, type = Balance.class),
            @ElementList(entry = "Plan", inline = true, required = false, type = Plan.class),
            @ElementList(entry = "Income", inline = true, required = false, type = Income.class),
            @ElementList(entry = "Outcome", inline = true, required = false, type = Outcome.class),
    })
    private List<T> list;

    public XmlWrapper() {
    }

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

package ru.sfedu.accounter.hibernate.lab5.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table
@Entity
public class HArmy {
    @Id
    @GeneratedValue(generator = "increment")
    @Column
    private Long id;
    @Column
    private int armyHealthPoints;
    @Column
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    //отношение один ко многим (много юнитов в одной таблице с армией)
    private List<HUnit> units;

    public HArmy() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getArmyHealthPoints() {
        return armyHealthPoints;
    }

    public void setArmyHealthPoints(int armyHealthPoints) {
        this.armyHealthPoints = armyHealthPoints;
    }

    public List<HUnit> getUnits() {
        return units;
    }

    public void setUnits(List<HUnit> units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HArmy hArmy = (HArmy) o;
        return Objects.equals(id, hArmy.id) && Objects.equals(units, hArmy.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, units);
    }

    @Override
    public String toString() {
        return "HArmy{" +
                "id=" + id +
                ", units=" + units +
                '}';
    }
}

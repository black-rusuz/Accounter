package ru.sfedu.accounter.hibernate.lab5.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class HUnit {
    @Id
    @GeneratedValue(generator = "increment")
    @Column
    private Long id;
    @Column
    private String unitType;
    @Column
    private int unitAttackPoints;
    @Column
    private int unitHealthPoints;
    @Column
    private int goldRequired;
    @Column
    private int metalRequired;
    @Column
    private int foodRequired;

    public HUnit(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public int getUnitAttackPoints() {
        return unitAttackPoints;
    }

    public void setUnitAttackPoints(int unitAttackPoints) {
        this.unitAttackPoints = unitAttackPoints;
    }

    public int getUnitHealthPoints() {
        return unitHealthPoints;
    }

    public void setUnitHealthPoints(int unitHealthPoints) {
        this.unitHealthPoints = unitHealthPoints;
    }

    public int getGoldRequired() {
        return goldRequired;
    }

    public void setGoldRequired(int goldRequired) {
        this.goldRequired = goldRequired;
    }

    public int getMetalRequired() {
        return metalRequired;
    }

    public void setMetalRequired(int metalRequired) {
        this.metalRequired = metalRequired;
    }

    public int getFoodRequired() {
        return foodRequired;
    }

    public void setFoodRequired(int foodRequired) {
        this.foodRequired = foodRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HUnit hUnit = (HUnit) o;
        return unitAttackPoints == hUnit.unitAttackPoints && unitHealthPoints == hUnit.unitHealthPoints && goldRequired == hUnit.goldRequired && metalRequired == hUnit.metalRequired && foodRequired == hUnit.foodRequired && Objects.equals(id, hUnit.id) && Objects.equals(unitType, hUnit.unitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitType, unitAttackPoints, unitHealthPoints, goldRequired, metalRequired, foodRequired);
    }

    @Override
    public String toString() {
        return "HUnit{" +
                "id=" + id +
                ", unitType='" + unitType + '\'' +
                ", unitAttackPoints=" + unitAttackPoints +
                ", unitHealthPoints=" + unitHealthPoints +
                ", goldRequired=" + goldRequired +
                ", metalRequired=" + metalRequired +
                ", foodRequired=" + foodRequired +
                '}';
    }
}

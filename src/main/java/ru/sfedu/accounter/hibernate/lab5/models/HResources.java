package ru.sfedu.accounter.hibernate.lab5.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class HResources {
    @Id
    @GeneratedValue(generator = "increment")
    @Column
    private Long id;
    @Column
    private int food;
    @Column
    private int metal;
    @Column
    private int gold;
    @OneToOne(mappedBy = "resources", cascade = CascadeType.ALL)
    //один ресурс привязан к одной игре
    private HGame game;

    public HGame getGame() {
        return game;
    }

    public void setGame(HGame game) {
        this.game = game;
    }

    public HResources() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HResources that = (HResources) o;
        return food == that.food && metal == that.metal && gold == that.gold && Objects.equals(id, that.id) && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, food, metal, gold, game);
    }

    @Override
    public String toString() {
        return "HResources{" +
                "id=" + id +
                ", food=" + food +
                ", metal=" + metal +
                ", gold=" + gold +
                '}';
    }
}

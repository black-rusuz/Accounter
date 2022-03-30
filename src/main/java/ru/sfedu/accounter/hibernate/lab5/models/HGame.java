package ru.sfedu.accounter.hibernate.lab5.models;



import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game")
public class HGame {
    @Id
    @GeneratedValue(generator = "increment")
    @Column (name = "Game_id")
    private Long id;

    @Column
    private String gameName;

    @Column
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<HArmy> armies;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resources_id", referencedColumnName = "id")
    private HResources resources;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GAME_PLANETS",
            joinColumns = @JoinColumn(name = "Game_id"),
            inverseJoinColumns = @JoinColumn(name = "Planet_id")
    )
    //используем вспомогательную таблицу GAME_PLANETS для хранения отношения id игры и планеты
    //JoinColumn - указываем на внешние ключи этих таблиц
    private List<HPlanet> planets;

    public HGame() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<HArmy> getArmies() {
        return armies;
    }

    public void setArmies(List<HArmy> armies) {
        this.armies = armies;
    }

    public HResources getResources() {
        return resources;
    }

    public void setResources(HResources resources) {
        this.resources = resources;
    }

    public List<HPlanet> getPlanetList() {
        return planets;
    }

    public void setPlanetList(List<HPlanet> planetList) {
        this.planets = planetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HGame hGame = (HGame) o;
        return Objects.equals(id, hGame.id) && Objects.equals(gameName, hGame.gameName) && Objects.equals(armies, hGame.armies) && Objects.equals(resources, hGame.resources) && Objects.equals(planets, hGame.planets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameName, armies, resources, planets);
    }

    @Override
    public String toString() {
        return "HGame{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", armies=" + armies +
                ", resources=" + resources +
                ", planetList=" + planets +
                '}';
    }
}

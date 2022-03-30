package ru.sfedu.accounter.hibernate.lab5.models;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class HPlanet {
    @Id
    @GeneratedValue(generator = "increment")
    @Column (name = "Planet_id")
    private Long id;
    @Column
    private String planetName;
    @Column
    private String type;
    @ManyToMany(mappedBy = "planets")
    //в любой игре могут быть любые планеты
    private List<HGame> gameList;

    public HPlanet() {super();}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HGame> getGameList() {
        return gameList;
    }

    public void setGameList(List<HGame> gameList) {
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HPlanet hPlanet = (HPlanet) o;
        return Objects.equals(id, hPlanet.id) && Objects.equals(planetName, hPlanet.planetName) && Objects.equals(type, hPlanet.type) && Objects.equals(gameList, hPlanet.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planetName, type, gameList);
    }

    @Override
    public String toString() {
        return "HPlanet{" +
                "id=" + id +
                ", planetName='" + planetName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

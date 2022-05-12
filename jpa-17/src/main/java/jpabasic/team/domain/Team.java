package jpabasic.team.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {
    @Id
    private String id;
    private String name;
    @OneToMany(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "team_id")
    private Set<Player> players = new HashSet<>();

    protected Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String id, String name, Set<Player> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public void removeAllPlayers() {
        players.clear();
    }
}

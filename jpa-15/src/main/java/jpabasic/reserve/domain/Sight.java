package jpabasic.reserve.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sight")
public class Sight {
    @Id
    private String id;
    private String name;

    protected Sight() {
    }

    public Sight(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

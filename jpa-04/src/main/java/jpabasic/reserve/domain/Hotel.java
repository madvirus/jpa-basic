package jpabasic.reserve.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_info")
public class Hotel {
    @Id
    @Column(name = "hotel_id")
    private String id;

    @Column(name = "nm")
    private String name;

    private int year;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime lastModified;

    protected Hotel() {
    }

    public Hotel(String id, String name, int year, Grade grade) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.grade = grade;
        this.created = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Grade getGrade() {
        return grade;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", grade=" + grade +
                ", created=" + created +
                ", lastModified=" + lastModified +
                '}';
    }
}

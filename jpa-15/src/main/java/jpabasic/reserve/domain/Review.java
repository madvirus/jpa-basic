package jpabasic.reserve.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "sight_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sight_id")
    private Sight sight;
    private int grade;
    private String comment;

    protected Review() {
    }

    public Review(Sight sight, int grade, String comment) {
        this.sight = sight;
        this.grade = grade;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Sight getSight() {
        return sight;
    }

    public int getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }
}

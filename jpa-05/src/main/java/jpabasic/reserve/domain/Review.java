package jpabasic.reserve.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hotel_id")
    private String hotelId;
    private int mark;
    @Column(name = "writer_name")
    private String writerName;
    private String comment;
    private LocalDateTime created;

    protected Review() {
    }

    public Review(int mark, String hotelId, String writerName, String comment) {
        this.mark = mark;
        this.hotelId = hotelId;
        this.writerName = writerName;
        this.comment = comment;
        this.created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}

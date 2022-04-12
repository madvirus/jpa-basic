package jpabasic.reserve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @Column(name = "review_id")
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

    public Review(int mark,
                  String writerName,
                  String comment,
                  LocalDateTime created) {
        this.mark = mark;
        this.writerName = writerName;
        this.comment = comment;
        this.created = created;
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

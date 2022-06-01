package jpabasic.notice.query;

import jakarta.persistence.*;
import jpabasic.notice.domain.BooleanYesNoConverter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "notice")
@Immutable
public class NoticeReadonly {
    @Id
    @Column(name = "notice_id")
    private Long id;
    private String title;
    private String content;
    @Column(name = "open_yn")
    @Convert(converter = BooleanYesNoConverter.class)
    private boolean opened;
    @Column(name = "cat")
    private String categoryCode;
    @Formula("(select c.name from category c where c.cat_id = cat)")
    private String categoryName;

    protected NoticeReadonly() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isOpened() {
        return opened;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

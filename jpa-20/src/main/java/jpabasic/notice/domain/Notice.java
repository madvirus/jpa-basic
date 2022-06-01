package jpabasic.notice.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "notice")
@DynamicUpdate
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    protected Notice() {
    }

    public Notice(String title, String content, boolean opened, String categoryCode) {
        this.title = title;
        this.content = content;
        this.opened = opened;
        this.categoryCode = categoryCode;
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

    public void open() {
        this.opened = true;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", opened=" + opened +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}

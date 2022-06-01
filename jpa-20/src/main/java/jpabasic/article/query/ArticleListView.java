package jpabasic.article.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDateTime;

@Subselect(
    """
    select a.article_id, a.title, w.name as writer, a.written_at
    from article a left join writer w on a.writer_id = w.id
    """)
@Entity
@Immutable
public class ArticleListView {
    @Id
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String writer;
    @Column(name = "written_at")
    private LocalDateTime writtenAt;

    protected ArticleListView() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getWrittenAt() {
        return writtenAt;
    }

    @Override
    public String toString() {
        return "ArticleListView{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", writtenAt=" + writtenAt +
                '}';
    }
}

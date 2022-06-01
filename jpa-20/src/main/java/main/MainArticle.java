package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpabasic.jpa.EMF;
import jpabasic.article.query.ArticleListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class MainArticle {
    private static Logger logger = LoggerFactory.getLogger(MainArticle.class);

    public static void main(String[] args) {
        EMF.init();
        initData();
        try {
            printArticleListViews();
        } finally {
            EMF.close();
        }
    }

    private static void printArticleListViews() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<ArticleListView> query = em.createQuery(
                    "select a from ArticleListView a " +
                            "where a.writtenAt >= :since " +
                            "order by a.id desc",
                    ArticleListView.class);
            query.setParameter("since", LocalDateTime.now().minusDays(7));
            List<ArticleListView> list = query.getResultList();
            list.forEach(article -> {
                logger.info("article: {}", article);
            });
            tx.commit();
        } finally {
            em.close();
        }
    }

    private static void initData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("truncate table article");
            stmt.executeUpdate("truncate table writer");
            stmt.executeUpdate("insert into writer values (1, '작성자1')");
            stmt.executeUpdate("insert into writer values (2, '작성자2')");
            stmt.executeUpdate("insert into article (title, writer_id, written_at, content) values ('제목1', 1, now(), '내용1')");
            stmt.executeUpdate("insert into article (title, writer_id, written_at, content) values ('제목2', 2, now(), '내용2')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

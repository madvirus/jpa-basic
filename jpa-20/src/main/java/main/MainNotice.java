package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.notice.domain.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainNotice {
    private static Logger logger = LoggerFactory.getLogger(MainNotice.class);

    public static void main(String[] args) {
        EMF.init();
        initData();
        try {
            Long id1 = insertNotice("t1", "c1", false, "C1");
            Long id2 = insertNotice("t2", "c2", true, null);
            printNotice(id1);
            printNotice(id2);
            openNotice(id1);
        } finally {
            EMF.close();
        }
    }

    private static Long insertNotice(String title, String content, boolean opened, String categoryCode) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Notice notice = new Notice(title, content, opened, categoryCode);
            em.persist(notice);
            tx.commit();
            return notice.getId();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void printNotice(Long id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Notice notice = em.find(Notice.class, id);
            if (notice == null) {
                logger.info("Notice not found: id = {}", id);
            } else {
                logger.info("Notice: {}", notice);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void openNotice(Long id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Notice notice = em.find(Notice.class, id);
            notice.open();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
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
            stmt.executeUpdate("truncate table notice");
            stmt.executeUpdate("truncate table category");
            stmt.executeUpdate("insert into category values ('C1', '카테고리1')");
            stmt.executeUpdate("insert into category values ('C2', '카테고리2')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

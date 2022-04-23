package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.question2.domain.Choice;
import jpabasic.question2.domain.Question2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainQuestion2 {
    private static Logger logger = LoggerFactory.getLogger(MainQuestion2.class);

    public static void main(String[] args) throws Exception {
        EMF.init();
        clearAll();
        saveQuestion("A01");
        readQuestion("A01");
        changeChoices("A01");
        removeQuestion("A01");
        EMF.close();
    }

    private static void saveQuestion(String id) {
        logger.info("saveQuestion");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question2 q = new Question2(id, "질문",
                    List.of(
                            new Choice("보기1", false),
                            new Choice("보기2", false)
                    ));
            em.persist(q);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void readQuestion(String id) {
        logger.info("readQuestion");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question2 q = em.find(Question2.class, id);
            logger.info("보기 개수: {}", q.getChoices().size());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void changeChoices(String id) {
        logger.info("changeChoices");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question2 q = em.find(Question2.class, id);
            q.setChoices(List.of(new Choice("답1", false),
                    new Choice("답2", false)));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void removeQuestion(String id) {
        logger.info("removeQuestion");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question2 q = em.find(Question2.class, id);
            em.remove(q);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    private static void clearAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("delete from question_choice where question_id != ''");
            stmt.executeUpdate("delete from question where id != ''");
        }
    }
}

package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.question.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainQuestion1 {
    private static Logger logger = LoggerFactory.getLogger(MainQuestion1.class);

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
            Question q = new Question(id, "질문", List.of("보기1", "보기2"));
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
            Question q = em.find(Question.class, id);
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
            Question q = em.find(Question.class, id);
            q.setChoices(List.of("답1", "답2"));
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
            Question q = em.find(Question.class, id);
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

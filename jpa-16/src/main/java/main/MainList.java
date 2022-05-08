package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.survey.domain.Question;
import jpabasic.survey.domain.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainList {
    private static Logger logger = LoggerFactory.getLogger(MainList.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            createQuestions();
            createSurvey();
            addRemove();
            // removeAll();
        } finally {
            EMF.close();
        }
    }

    private static void createQuestions() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Question("Q1", "질문1"));
            em.persist(new Question("Q2", "질문2"));
            em.persist(new Question("Q3", "질문3"));
            em.persist(new Question("Q4", "질문4"));
            em.persist(new Question("Q5", "질문5"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void createSurvey() {
        logger.info("createSurvey");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question q1 = em.find(Question.class, "Q1");
            Question q2 = em.find(Question.class, "Q2");
            Question q3 = em.find(Question.class, "Q3");
            List<Question> qs = new ArrayList<>();
            qs.add(q1);
            qs.add(q2);
            qs.add(q3);
            em.persist(new Survey("S1", "설문", qs));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void addRemove() {
        logger.info("addRemove");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Question q1 = em.find(Question.class, "Q1");
            Question q4 = em.find(Question.class, "Q4");
            Survey survey = em.find(Survey.class, "S1");
            survey.removeQuestion(q1);
            survey.addQuestion(q4);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void removeAll() {
        logger.info("removeAll");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Survey survey = em.find(Survey.class, "S1");
            survey.removeAllQuestions();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void clearAll() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("truncate table survey_question");
            stmt.executeUpdate("truncate table survey");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

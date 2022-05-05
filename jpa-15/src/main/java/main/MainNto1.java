package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.Review;
import jpabasic.reserve.domain.Sight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainNto1 {
    private static Logger logger = LoggerFactory.getLogger(MainNto1.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            saveSight();
            saveReview(5, "최고");
            saveReview(4, "좋음");
            printReview();
            printReviewsBySightId();
            printReviewsBySight();
        } finally {
            EMF.close();
        }
    }

    private static void saveSight() {
        logger.info("saveSight");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sight sight = new Sight("S-01", "명소1");
            em.persist(sight);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void saveReview(int grade, String comment) {
        logger.info("saveReview");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sight sight = em.find(Sight.class, "S-01");
            Review review = new Review(sight, grade, comment);
            em.persist(review);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void printReview() {
        logger.info("printReview");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            logger.info("find(Review.class, 1L)");
            Review review = em.find(Review.class, 1L);
            logger.info("review.getSight()");
            Sight sight = review.getSight();
            logger.info("sight.getName()");
            String name = sight.getName();
            logger.info("name: {}", name);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void printReviewsBySightId() {
        logger.info("printReviews");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Review> query = em.createQuery("select r from Review r where r.sight.id = :sightId order by r.id desc", Review.class);
            query.setParameter("sightId", "S-01");
            List<Review> results = query.getResultList();
            results.forEach(r -> {
                Sight sight = r.getSight();
                logger.info("id: {}, sight: {}", r.getId(), sight.getName());
            });
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void printReviewsBySight() {
        logger.info("printReviews2");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sight sight = em.find(Sight.class, "S-01");
            TypedQuery<Review> query = em.createQuery("select r from Review r where r.sight = :sight order by r.id desc", Review.class);
            query.setParameter("sight", sight);
            List<Review> results = query.getResultList();
            results.forEach(r -> {
                logger.info("id: {}, sight: {}", r.getId(), r.getSight().getName());
            });
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
            stmt.executeUpdate("truncate table sight_review");
            stmt.executeUpdate("truncate table sight");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

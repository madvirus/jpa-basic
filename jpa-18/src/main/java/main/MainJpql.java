package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainJpql {
    private static Logger logger = LoggerFactory.getLogger(MainJpql.class);

    public static void main(String[] args) {
        EMF.init();
        initData();
        try {
            selectReviewOrderBy();
        } finally {
            EMF.close();
        }
    }

    private static void selectReviewOrderBy() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Review> query = em.createQuery(
                    "select r from Review r where r.hotelId = :hotelId order by r.id desc",
                    Review.class);
            query.setParameter("hotelId", "H-001");
            query.setFirstResult(8); // 0λΆν° μμ
            query.setMaxResults(4);
            List<Review> reviews = query.getResultList();

            reviews.forEach(r -> logger.info("Review: {}", r.getId()));
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
            stmt.executeUpdate("truncate table review");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Review(5, "H-001", "μμ±μ1", "λκΈ1"));
            em.persist(new Review(5, "H-001", "μμ±μ2", "λκΈ2"));
            em.persist(new Review(5, "H-001", "μμ±μ3", "λκΈ3"));
            em.persist(new Review(5, "H-001", "μμ±μ4", "λκΈ4"));
            em.persist(new Review(5, "H-001", "μμ±μ5", "λκΈ5"));
            em.persist(new Review(5, "H-001", "μμ±μ6", "λκΈ6"));
            em.persist(new Review(5, "H-001", "μμ±μ7", "λκΈ7"));
            em.persist(new Review(5, "H-001", "μμ±μ8", "λκΈ8"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

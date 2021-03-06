package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class MainCriteria {
    private static Logger logger = LoggerFactory.getLogger(MainCriteria.class);

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
        String hotelId = "H-001";
        int mark = 3;

        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> root = cq.from(Review.class);
            cq.select(root);
            Predicate p = cb.conjunction();
            if (hotelId != null) {
                p = cb.and(p, cb.equal(root.get("hotelId"), hotelId));
            }
            p = cb.and(p, cb.greaterThan(root.get("created"), LocalDateTime.now().minusDays(10)));
            if (mark >= 0) {
                p = cb.and(p, cb.ge(root.get("mark"), mark));
            }
            cq.where(p);
            cq.orderBy(
                    cb.asc(root.get("hotelId")),
                    cb.desc(root.get("id"))
            );

            TypedQuery<Review> query = em.createQuery(cq);
            query.setFirstResult(4); // 0λΆν° μμ
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

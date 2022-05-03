package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.BestPick;
import jpabasic.reserve.domain.MembershipCard;
import jpabasic.reserve.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainPk {
    private static Logger logger = LoggerFactory.getLogger(MainPk.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            saveUser();
            saveBestPick();
            printBestPick();
        } finally {
            EMF.close();
        }
    }

    private static void saveUser() {
        logger.info("saveUser");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new User("a@a.com", "A", LocalDateTime.now()));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void saveBestPick() {
        logger.info("saveBestPick");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, "a@a.com");
            BestPick bestPick = new BestPick(user, "제목");
            em.persist(bestPick);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void printBestPick() {
        logger.info("printBestPick");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BestPick bestPick = em.find(BestPick.class, "a@a.com");
            logger.info("card: {}, {}", bestPick.getEmail(), bestPick.getTitle());
            User user = bestPick.getUser();
            logger.info("user: {}", user);
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
            stmt.executeUpdate("delete from best_pick where user_email != ''");
            stmt.executeUpdate("delete from user where email != ''");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

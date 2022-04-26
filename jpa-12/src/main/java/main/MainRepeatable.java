package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class MainRepeatable {
    private static Logger logger = LoggerFactory.getLogger(MainRepeatable.class);

    public static void main(String[] args) {
        EMF.init();
        clearAll();
        saveUser("a@a.com", "name");
        readTwice("a@a.com");
        EMF.close();
    }

    private static void saveUser(String email, String name) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new User(email, name, LocalDateTime.now()));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void readTwice(String email) {
        logger.info("readTwice");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            logger.info("first find");
            User first = em.find(User.class, email);
            logger.info("second find");
            User second = em.find(User.class, email);
            logger.info("same object: {}", (first == second));
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
            stmt.executeUpdate("delete from user where email != ''");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

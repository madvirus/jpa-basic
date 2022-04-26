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

public class MainLifecycle {
    private static Logger logger = LoggerFactory.getLogger(MainLifecycle.class);

    public static void main(String[] args) {
        EMF.init();
        clearAll();
        saveUser("a@a.com", "name");
        updateUser("a@a.com");
        detachedUser1("a@a.com");
        detachedUser2("a@a.com");
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

    private static void updateUser(String email) {
        logger.info("updateUser");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, email);
            u.changeName("변경");
            logger.info("committing");
            tx.commit();
            logger.info("committed");
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            logger.info("closing");
            em.close();
            logger.info("closed");
        }
    }

    private static void detachedUser1(String email) {
        logger.info("detachedUser1");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, email);
            em.detach(u);
            logger.info("em.detach(u) 실행함");
            u.changeName("변경");
            logger.info("committing");
            tx.commit();
            logger.info("committed");
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void detachedUser2(String email) {
        logger.info("detachedUser2");
        User u = null;
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            u = em.find(User.class, email);
            logger.info("committing");
            tx.commit();
            logger.info("committed");
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        logger.info("detached user 변경전");
        u.changeName("변경");
        logger.info("detached user 변경함");
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

package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabasic.reserve.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class UserSaveMain {
    private static Logger logger = LoggerFactory.getLogger(UserSaveMain.class);

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabegin");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = new User("user@user.com", "user", LocalDateTime.now());
            entityManager.persist(user);
            logger.info("EntityManager.persist 호출함");
            transaction.commit();
            logger.info("EntityTransaction.commit 호출함");
        } catch (Exception ex) {
            logger.error("에러 발생: " + ex.getMessage(), ex);
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}

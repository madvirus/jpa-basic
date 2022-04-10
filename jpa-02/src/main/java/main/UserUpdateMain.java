package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabasic.reserve.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserUpdateMain {
    private static Logger logger = LoggerFactory.getLogger(UserUpdateMain.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabegin");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, "user@user.com");
            if (user == null) {
                System.out.println("User 없음");
            } else {
                String newName = "이름" + (System.currentTimeMillis() % 100);
                user.changeName(newName);
                logger.info("User.changeName 호출함");
            }
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

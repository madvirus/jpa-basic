package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.reserve.domain.Grade;
import jpabasic.reserve.domain.Hotel;
import jpabasic.reserve.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main04 {
    private static Logger logger = LoggerFactory.getLogger(Main04.class);

    public static void main(String[] args) {
        EMF.init();

        Hotel entity = new Hotel("H-01", "말", 2022, Grade.S5);

        save(entity);
        Hotel h2 = read("H-01");
        if (h2 == null) {
            logger.info("없음");
        } else {
            logger.info("있음: {}", h2);
        }
        EMF.close();
    }

    private static void save(Hotel entity) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static Hotel read(String id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Hotel hotel = em.find(Hotel.class, id);
            tx.commit();
            return hotel;
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}

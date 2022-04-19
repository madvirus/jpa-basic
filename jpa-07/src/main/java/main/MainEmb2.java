package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.article.domain.Address;
import jpabasic.article.domain.Intro;
import jpabasic.article.domain.Writer;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainEmb2 {
    private static Logger logger = LoggerFactory.getLogger(MainEmb2.class);

    public static void main(String[] args) {
        EMF.init();
        Long id = save();
        update(id);
//        print(id);
//        delete(id);
        EMF.close();
    }

    private static Long save() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Writer w = new Writer("name",
                    new Address("주소1", "주소2", "12345"),
                    new Intro("text/plain", "소개글"));

            em.persist(w);

            tx.commit();

            return w.getId();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void print(Long id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Writer writer = em.find(Writer.class, id);
            logger.info("writer: {}", writer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void update(Long id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Writer writer = em.find(Writer.class, id);
            writer.getIntro().setContent("변경");
            writer.setAddress(new Address("새주소1", "새주소2", "11111"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void delete(Long id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Writer writer = em.find(Writer.class, id);
            em.remove(writer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

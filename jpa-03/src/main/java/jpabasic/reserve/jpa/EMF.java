package jpabasic.reserve.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMF {
    private static EntityManagerFactory emf;

    public static void init() {
        emf = Persistence.createEntityManagerFactory("jpabegin");
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}

package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpabasic.jpa.EMF;
import jpabasic.role.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

public class Main11 {
    private static Logger logger = LoggerFactory.getLogger(Main11.class);

    public static void main(String[] args) {
        EMF.init();
        clearAll();
        for (int i = 11 ; i <= 20 ; i++) {
            saveRole("R" + i, "name" + i);
        }
        selectNoFetchMode();
        selectFetchMode();
        EMF.close();
    }

    private static void clearAll() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("delete from role_perm where role_id != ''");
            stmt.executeUpdate("delete from role where id != ''");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveRole(String roleId, String name) {
        logger.info("saveRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = new Role(roleId, name, Set.of("F1", "F2"));
            em.persist(role);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void selectNoFetchMode() {
        logger.info("selectNoFetchMode");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Role> query = em.createQuery(
                    "select r from Role r where r.name like :name order by r.id", Role.class);
            query.setParameter("name", "name%");
            query.setFirstResult(6);
            query.setMaxResults(3);
            List<Role> roles = query.getResultList();
            roles.forEach(r -> {
                logger.info("role: id={} name={} perms={}", r.getId(), r.getName(), r.getPermissions().size());
            });
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void selectFetchMode() {
        logger.info("selectFetchMode");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Role> query = em.createQuery(
                    "select r from Role r left join fetch r.permissions where r.name like :name order by r.id", Role.class);
            query.setParameter("name", "name%");
            query.setFirstResult(6);
            query.setMaxResults(3);
            List<Role> roles = query.getResultList();
            roles.forEach(r -> {
                logger.info("role: id={} name={} perms={}", r.getId(), r.getName(), r.getPermissions().size());
            });
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}

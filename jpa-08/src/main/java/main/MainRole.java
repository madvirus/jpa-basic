package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.acl.domain.Role;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class MainRole {
    private static Logger logger = LoggerFactory.getLogger(MainRole.class);

    public static void main(String[] args) throws Exception {
        clearAll();
        EMF.init();
        String roleId = "R07";
        saveRole(roleId);
        readRole(roleId);
        updateRoleByModifyingCollection(roleId);
        String roleId2 = "R19";
        saveRole(roleId2);
        updateRoleByAssigningNewSet(roleId2);
        revokeRole(roleId2);
    }

    private static void clearAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("delete from role_perm where role_id != ''");
            stmt.executeUpdate("delete from role where id != ''");
        }
    }

    private static void saveRole(String roleId) {
        logger.info("saveRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = new Role(roleId, "관리자", Set.of("F1", "F2"));
            em.persist(role);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void readRole(String roleId) {
        logger.info("readRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            logger.info("role id: {}", role.getId());
            for (String perm : role.getPermissions()) {
                logger.info("perm: {}", perm);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void updateRoleByModifyingCollection(String roleId) {
        logger.info("updateRoleByModifyingCollection");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.getPermissions().add("F3");
            role.getPermissions().remove("F1");
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void updateRoleByAssigningNewSet(String roleId) {
        logger.info("updateRoleByAssigningNewSet");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.setPermissions(Set.of("F4", "F5"));
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void revokeRole(String roleId) {
        logger.info("revokeRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.revokeAll();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}

package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.acl.domain.Role;
import jpabasic.acl2.domain.GrantedPermission;
import jpabasic.acl2.domain.Role2;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Set;

public class MainRole2 {
    private static Logger logger = LoggerFactory.getLogger(MainRole2.class);

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
            Role2 role = new Role2(roleId, "관리자", Set.of(
                    new GrantedPermission("F1", "admin1"),
                    new GrantedPermission("F2", "admin1"))
            );
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
            Role2 role = em.find(Role2.class, roleId);
            logger.info("role id: {}", role.getId());
            for (GrantedPermission perm : role.getPermissions()) {
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
            Role2 role = em.find(Role2.class, roleId);
            role.getPermissions().add(new GrantedPermission("F3", "admin1"));
            Optional<GrantedPermission> permOpt = role.getPermissions().stream().filter(p -> "F1".equals(p.getPermission())).findFirst();
            permOpt.ifPresent(p -> role.getPermissions().remove(p));
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
            Role2 role = em.find(Role2.class, roleId);
            role.setPermissions(Set.of(
                    new GrantedPermission("F3", "admin1"),
                    new GrantedPermission("F4", "admin1")
            ));
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
            Role2 role = em.find(Role2.class, roleId);
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

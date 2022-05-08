package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.game.domain.Game;
import jpabasic.game.domain.Member;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainMap {
    private static Logger logger = LoggerFactory.getLogger(MainMap.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            addMember();
            addGameWithMembers();
            addRemove();
        } finally {
            EMF.close();
        }
    }

    private static void addMember() {
        logger.info("addMember");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Member("M1", "멤버1"));
            em.persist(new Member("M2", "멤버2"));
            em.persist(new Member("M3", "멤버3"));
            em.persist(new Member("M4", "멤버4"));
            em.persist(new Member("M5", "멤버5"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void addGameWithMembers() {
        logger.info("addGameWithMembers");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Member m1 = em.find(Member.class, "M1");
            Member m2 = em.find(Member.class, "M2");
            Member m3 = em.find(Member.class, "M3");
            Map<String, Member> members = new HashMap<>();
            members.put("C", m1);
            members.put("PG", m2);
            members.put("SG", m3);
            Game g = new Game("G1", "게임1", members);
            em.persist(g);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void addRemove() {
        logger.info("addRemove");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Game g = em.find(Game.class, "G1");
            Member m4 = em.find(Member.class, "M4");
            Member m5 = em.find(Member.class, "M5");
            g.add("C", m4);
            g.add("PF", m5);
            g.remove("SG");
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
            stmt.executeUpdate("truncate table game_member");
            stmt.executeUpdate("truncate table game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

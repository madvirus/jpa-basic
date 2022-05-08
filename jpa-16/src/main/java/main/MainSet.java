package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.team.domain.Player;
import jpabasic.team.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class MainSet {
    private static Logger logger = LoggerFactory.getLogger(MainSet.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            saveTeam();
            savePlayers();
            saveTeamWithPlayers();
            addRemove();
            removeAll();
        } finally {
            EMF.close();
        }
    }

    private static void saveTeam() {
        logger.info("saveTeam");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Team("T-01", "팀1"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void savePlayers() {
        logger.info("savePlayers");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Player("P-11", "선수11"));
            em.persist(new Player("P-12", "선수12"));
            em.persist(new Player("P-21", "선수21"));
            em.persist(new Player("P-22", "선수22"));
            em.persist(new Player("P-23", "선수23"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void saveTeamWithPlayers() {
        logger.info("saveTeamWithPlayers");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Player p21 = em.find(Player.class, "P-21");
            Player p22 = em.find(Player.class, "P-22");
            Set<Player> players = new HashSet<>();
            players.add(p21);
            players.add(p22);
            em.persist(new Team("T-02", "팀2", players));
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
            Player p21 = em.find(Player.class, "P-21");
            Player p23 = em.find(Player.class, "P-23");
            Team team = em.find(Team.class, "T-02");
            team.removePlayer(p21);
            team.addPlayer(p23);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void removeAll() {
        logger.info("removeAll");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Team team = em.find(Team.class, "T-02");
            team.removeAllPlayers();
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
            stmt.executeUpdate("truncate table team");
            stmt.executeUpdate("truncate table player");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

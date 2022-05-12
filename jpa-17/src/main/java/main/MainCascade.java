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

public class MainCascade {
    private static Logger logger = LoggerFactory.getLogger(MainCascade.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            saveTeam();
            // removeTeam();
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
            Player p21 = new Player("P-21", "선수21");
            Player p22 = new Player("P-22", "선수22");
            Set<Player> players = new HashSet<>();
            players.add(p21);
            players.add(p22);
            em.persist(new Team("T-01", "팀1", players));
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

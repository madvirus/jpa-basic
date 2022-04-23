package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.document.domain.Document;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainDocument1 {
    private static Logger logger = LoggerFactory.getLogger(MainDocument1.class);

    public static void main(String[] args) throws Exception {
        EMF.init();
        clearAll();
        saveDocument("DOC1");
        changeProps("DOC1");
        EMF.close();
    }

    private static void saveDocument(String id) {
        logger.info("saveDocument");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Map<String, String> props = new HashMap<>();
            props.put("p1", "v1");
            props.put("p2", "v2");
            Document doc = new Document(id, "제목", "내용", props);
            em.persist(doc);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void changeProps(String id) {
        logger.info("changeProps");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Document doc = em.find(Document.class, id);
            doc.setProp("p1", "v1new");
            doc.setProp("p10", "v10");
            doc.removeProp("p2");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private static void clearAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("delete from doc_prop where doc_id != ''");
            stmt.executeUpdate("delete from doc where id != ''");
        }
    }

}

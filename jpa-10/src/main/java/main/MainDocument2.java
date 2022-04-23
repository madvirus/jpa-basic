package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.document2.domain.Document2;
import jpabasic.document2.domain.PropValue;
import jpabasic.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainDocument2 {
    private static Logger logger = LoggerFactory.getLogger(MainDocument2.class);

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
            Map<String, PropValue> props = new HashMap<>();
            props.put("p1", new PropValue("v1", true));
            props.put("p2", new PropValue("v2", true));
            Document2 doc = new Document2(id, "제목", "내용", props);
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
            Document2 doc = em.find(Document2.class, id);
            doc.setProp("p1", new PropValue("v1new", true));
            doc.setProp("p10", new PropValue("v10", false));
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

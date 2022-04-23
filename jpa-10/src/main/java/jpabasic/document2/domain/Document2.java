package jpabasic.document2.domain;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "doc")
public class Document2 {
    @Id
    private String id;
    private String title;
    private String content;
    @ElementCollection
    @CollectionTable(
            name = "doc_prop",
            joinColumns = @JoinColumn(name = "doc_id")
    )
    @MapKeyColumn(name = "name")
    private Map<String, PropValue> props = new HashMap<>();

    protected Document2() {
    }

    public Document2(String id, String title, String content, Map<String, PropValue> props) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.props = props;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setProp(String name, PropValue value) {
        props.put(name, value);
    }

    public void removeProp(String name) {
        props.remove(name);
    }
}

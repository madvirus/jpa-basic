package jpabasic.document.domain;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "doc")
public class Document {
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
    @Column(name = "value")
    private Map<String, String> props = new HashMap<>();

    protected Document() {}

    public Document(String id, String title, String content, Map<String, String> props) {
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

    public void setProp(String name, String value) {
        props.put(name, value);
    }

    public void removeProp(String name) {
        props.remove(name);
    }
}

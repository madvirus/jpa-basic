package jpabasic.question2.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question2 {
    @Id
    private String id;
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "question_choice",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @OrderColumn(name = "idx")
    private List<Choice> choices = new ArrayList<>();

    protected Question2() {}

    public Question2(String id, String text, List<Choice> choices) {
        this.id = id;
        this.text = text;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}

package jpabasic.survey.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey")
public class Survey {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "survey_id")
    @OrderColumn(name = "order_no")
    private List<Question> questions = new ArrayList<>();

    protected Survey() {
    }

    public Survey(String id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public void removeQuestion(Question q) {
        this.questions.remove(q);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void removeAllQuestions() {
        questions.clear();
    }
}

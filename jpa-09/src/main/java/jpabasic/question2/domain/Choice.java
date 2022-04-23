package jpabasic.question2.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Choice {
    private String text;
    private boolean input;

    protected Choice() {}

    public Choice(String text, boolean input) {
        this.text = text;
        this.input = input;
    }

    public String getText() {
        return text;
    }

    public boolean isInput() {
        return input;
    }
}

package jpabasic.document2.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class PropValue {
    private String value;
    private boolean enabled;

    protected PropValue() {}

    public PropValue(String value, boolean enabled) {
        this.value = value;
        this.enabled = enabled;
    }

    public String getValue() {
        return value;
    }

    public boolean isEnabled() {
        return enabled;
    }
}

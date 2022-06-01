package jpabasic.notice.domain;

import jakarta.persistence.AttributeConverter;

import java.util.Objects;

public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Objects.equals(Boolean.TRUE, attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData) ? true : false;
    }
}

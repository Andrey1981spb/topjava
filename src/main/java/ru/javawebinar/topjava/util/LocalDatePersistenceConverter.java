package ru.javawebinar.topjava.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements
        AttributeConverter<LocalDateTime, java.sql.Timestamp> {

    @Override
    public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
        return databaseValue.toLocalDateTime();
    }
    @Override
    public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return java.sql.Timestamp.valueOf(entityValue);
    }

}

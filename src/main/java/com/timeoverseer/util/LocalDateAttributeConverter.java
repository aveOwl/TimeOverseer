package com.timeoverseer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
    private static final Logger LOG = LoggerFactory.getLogger(LocalDateAttributeConverter.class);

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        Assert.notNull(localDate, "Date must be not null");
        LOG.debug("> Converting localDate: {} to sqlDate", localDate);
        return Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        Assert.notNull(sqlDate, "Date must be not null");
        LOG.debug("> Converting sqlDate: {} to localDate", sqlDate);
        return sqlDate.toLocalDate();
    }
}

package com.timeoverseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final Logger LOG = LoggerFactory.getLogger(LocalDateDeserializer.class);

    @Override
    public LocalDate deserialize(JsonParser p,
                                 DeserializationContext ctxt) throws IOException {
        LOG.debug("> Deserializing date: {}", p.getText());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US);
        LocalDate date = LocalDate.parse(p.getText(), dtf);
        LOG.debug("< LocalDate: {} deserialized", date);
        return date;
    }
}

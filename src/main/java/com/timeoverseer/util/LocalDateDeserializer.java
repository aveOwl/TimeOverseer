package com.timeoverseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final Logger LOG = LoggerFactory.getLogger(LocalDateDeserializer.class);

    @Override
    public LocalDate deserialize(JsonParser json,
                                 DeserializationContext ctxt) throws IOException {
        LOG.debug("> Deserializing date: {}", json.getText());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate date = LocalDate.parse(json.getText(), dtf);
        LOG.debug("< LocalDate: {} deserialized", date);
        return date;
    }
}

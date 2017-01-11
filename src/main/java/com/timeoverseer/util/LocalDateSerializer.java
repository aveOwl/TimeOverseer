package com.timeoverseer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    private static final Logger LOG = LoggerFactory.getLogger(LocalDateSerializer.class);

    @Override
    public void serialize(LocalDate localDate,
                          JsonGenerator json,
                          SerializerProvider serializers) throws IOException {
        LOG.debug("> Serializing date: {}", localDate);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String date = localDate.format(dtf);
        json.writeString(date);
        LOG.debug("< Serialized date: {}", date);
    }
}

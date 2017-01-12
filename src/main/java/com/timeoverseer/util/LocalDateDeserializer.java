package com.timeoverseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser json,
                                 DeserializationContext ctxt) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return LocalDate.parse(json.getText(), dtf);
    }
}

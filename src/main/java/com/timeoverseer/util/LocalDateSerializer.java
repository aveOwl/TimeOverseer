package com.timeoverseer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate,
                          JsonGenerator json,
                          SerializerProvider serializers) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String date = localDate.format(dtf);
        json.writeString(date);
    }
}

package com.knu.ynortman.lab2.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

	public LocalDateTimeDeserializer() {
		this(null);
	}
	
	public LocalDateTimeDeserializer(Class t) {
		super(t);
	}
	
	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDateTime.parse(jp.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	

}

package hexlet.code.formatter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.diff.DiffEntry;
import hexlet.code.formatter.Formatter;

import java.util.List;

public final class JsonFormatter implements Formatter {
    /**
     * Маппер для сериализации.
     */
    private final ObjectMapper mapper;

    public JsonFormatter() {
        this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String format(final List<DiffEntry> compareResults) {
        if (compareResults == null || compareResults.isEmpty()) {
            throw new IllegalArgumentException("Comparison result list is empty or null and cannot be formatted.");
        }

        try {
            return mapper.writeValueAsString(compareResults);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize to json", e);
        }
    }
}

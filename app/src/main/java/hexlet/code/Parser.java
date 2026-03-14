package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public final class Parser {
    private Parser() {

    }

    public static Map<String, Object> toKeyValuePairs(final ExtractedData extractedData) throws IOException {
        ObjectMapper objectMapper = determineMapper(extractedData.dataKind());
        return objectMapper.readValue(extractedData.rawText(), new TypeReference<>() {
        });
    }

    private static ObjectMapper determineMapper(final DataKind dataKind) {
        return switch (dataKind) {
            case YAML -> new YAMLMapper();
            case JSON -> new ObjectMapper();
        };
    }
}

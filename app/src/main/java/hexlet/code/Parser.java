package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public final class Parser {
    private Parser() {

    }

    public static Map<String, Object> toKeyValuePairs(final FileData fileData) throws IOException {
        try {
            ObjectMapper objectMapper = determineMapper(fileData.fileKind());
            return objectMapper.readValue(fileData.fileText(), new TypeReference<>() { });
        } catch (IOException | UnsupportedOperationException e) {
            throw new IOException("Error while parsing fileText: %s".formatted(fileData), e);
        }
    }

    private static ObjectMapper determineMapper(final FileKind fileKind) {
        return switch (fileKind) {
            case YAML -> new YAMLMapper();
            case JSON -> new ObjectMapper();
        };
    }
}

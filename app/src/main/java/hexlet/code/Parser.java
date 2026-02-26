package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class Parser {
    private Parser() {

    }

    public static Map<String, Object> parseFile(final String filePath) throws IOException {
        try {
            byte[] contentBytes = Files.readAllBytes(Path.of(filePath));
            ObjectMapper objectMapper = determineMapper(filePath);
            return objectMapper.readValue(contentBytes, new TypeReference<>() { });
        } catch (IOException e) {
            throw new IOException("Error while parsing file: " + filePath, e);
        }
    }

    private static ObjectMapper determineMapper(final String filename) {
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            return new YAMLMapper();
        } else {
            return new ObjectMapper();

        }
    }
}

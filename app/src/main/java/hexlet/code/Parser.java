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
        } catch (IOException | UnsupportedOperationException e) {
            throw new IOException("Error while parsing file: " + filePath, e);
        }
    }

    private static ObjectMapper determineMapper(final String filename) {
        var extension = extractFileExtension(filename);
        return switch (extension) {
            case ".yml", ".yaml" -> new YAMLMapper();
            case ".json" -> new ObjectMapper();
            default -> throw new UnsupportedOperationException("Unsupported file type: " + extension);
        };
    }

    private static String extractFileExtension(final String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 && lastDotIndex < filename.length() - 1
                ? filename.substring(lastDotIndex)
                : "";
    }
}

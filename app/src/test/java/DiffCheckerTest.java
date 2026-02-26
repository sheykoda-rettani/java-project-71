import hexlet.code.DiffChecker;
import hexlet.code.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffCheckerTest {
    private final String RESOURCES_PATH = "src/test/resources/";

    @ParameterizedTest
    @MethodSource("intRangeProvider")
    void testJsonFormat(final int fileNumber) throws IOException {
        final String beforePath = RESOURCES_PATH + String.format("before_%d.json", fileNumber);
        final String afterPath = RESOURCES_PATH + String.format("after_%d.json", fileNumber);
        final String outputPath = RESOURCES_PATH + String.format("output_%d.txt", fileNumber);
        final Map<String, Object> beforeMap = Parser.parseFile(beforePath);
        final Map<String, Object> afterMap = Parser.parseFile(afterPath);
        String expectedOutput = Files.readString(Path.of(outputPath));
        String actualOutput = DiffChecker.compareMaps(beforeMap, afterMap);
        assertEquals(expectedOutput, actualOutput);
    }

    @ParameterizedTest
    @MethodSource("intRangeProvider")
    void testYmlFormat(final int fileNumber) throws IOException {
        final String beforePath = RESOURCES_PATH + String.format("before_%d.yml", fileNumber);
        final String afterPath = RESOURCES_PATH + String.format("after_%d.yml", fileNumber);
        final String outputPath = RESOURCES_PATH + String.format("output_%d.txt", fileNumber);
        final Map<String, Object> beforeMap = Parser.parseFile(beforePath);
        final Map<String, Object> afterMap = Parser.parseFile(afterPath);
        String expectedOutput = Files.readString(Path.of(outputPath));
        String actualOutput = DiffChecker.compareMaps(beforeMap, afterMap);
        assertEquals(expectedOutput, actualOutput);
    }

    private static IntStream intRangeProvider() {
        return IntStream.range(1, 4);
    }
}

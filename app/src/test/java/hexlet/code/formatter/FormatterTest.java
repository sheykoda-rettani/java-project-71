package hexlet.code.formatter;

import hexlet.code.diff.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public final class FormatterTest {
    /**
     * Папка с ресурсами для тестов.
     *
     */
    private static final String RESOURCES_DIR = "src/test/resources";

    @ParameterizedTest(name = "{index}: Test with format={0}, file number={1}")
    @MethodSource("provideArguments")
    void testOutput(final String format, final int fileNumber) throws IOException {
        String beforePath = "%s/before_%d.json".formatted(RESOURCES_DIR, fileNumber);
        String afterPath = "%s/after_%d.json".formatted(RESOURCES_DIR, fileNumber);
        String expectedOutputPath = "%s/expected_output_%s_%d.txt".formatted(RESOURCES_DIR, format, fileNumber);
        String expectedOutput = Files.readString(Path.of(expectedOutputPath));
        String actualOutput = Differ.generateDiffString(beforePath, afterPath, format);
        assertEquals(expectedOutput, actualOutput);
    }

    private static List<Arguments> provideArguments() {
        int[] fileNumbers = IntStream.rangeClosed(1, 3).toArray();
        List<String> allowedFormats = List.of("stylish", "plain", "json");
        List<Arguments> result = new ArrayList<>();
        for (String format: allowedFormats) {
            for (int fileNumber: fileNumbers) {
                result.add(Arguments.of(format, fileNumber));
            }
        }

        return result;
    }
}

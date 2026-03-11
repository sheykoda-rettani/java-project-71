package hexlet.code.formatter;

import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("temporarily disable test while fixing data")
@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public final class FormatterTest {
    /**
     * Папка с ресурсами для тестов.
     *
     */
    private static final String RESOURCES_DIR = "src/test/resources";

    void testDefault() {}

    void testOutput(final String fileNumber, final String fileExt) { }

    @ParameterizedTest(name = "{index}: Test with format={0}, file number={1}")
    @MethodSource("provideArguments2")
    void testOutput(final String format, final int fileNumber) throws IOException {
        String beforePath = "%s/before_%d.json".formatted(RESOURCES_DIR, fileNumber);
        String afterPath = "%s/after_%d.json".formatted(RESOURCES_DIR, fileNumber);
        String expectedOutputPath = "%s/expected_output_%s_%d.txt".formatted(RESOURCES_DIR, format, fileNumber);
        String expectedOutput = Files.readString(Path.of(expectedOutputPath));
        String actualOutput = Differ.generate(beforePath, afterPath, format);
        assertEquals(expectedOutput, actualOutput);
    }

    private static List<Arguments> provideArguments2() {
        final int lowerBound = 1;
        final int upperBound = 3;
        int[] fileNumbers = IntStream.rangeClosed(lowerBound, upperBound).toArray();
        List<String> allowedFormats = List.of("stylish", "plain", "json");
        List<Arguments> result = new ArrayList<>();
        for (String format: allowedFormats) {
            for (int fileNumber: fileNumbers) {
                result.add(Arguments.of(format, fileNumber));
            }
        }

        return result;
    }

    /**
     * Генерирует тестовые данные
     * @return Набор тестовых данных (2 типа файлов и 4 вида "вывода", один из которых пустой)
     */
    private static List<Arguments> provideArguments() {
        final List<String> extensions = List.of("json", "yml");
        final List<String> allowedFormats = new ArrayList<>();
        allowedFormats.add(null);
        allowedFormats.addAll(List.of("stylish", "plain", "json"));
        List<Arguments> result = new ArrayList<>();
        for (String extension: extensions) {
            for (String format: allowedFormats) {
                result.add(Arguments.of(extension, format));
            }
        }
        return result;
    }
}

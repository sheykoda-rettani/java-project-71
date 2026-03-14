package hexlet.code.formatter;

import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public final class FormatterTest {
    /**
     * Папка с ресурсами для тестов.
     *
     */
    private static final String RESOURCES_DIR = "src/test/resources";

    /**
     * Разрешения с которыми умеет работать программа.
     */
    private static final List<String> EXTENSIONS = List.of("json", "yml");

    /**
     * Допустимые форматы вывода.
     */
    private static final List<String> FORMATS = List.of("stylish", "plain", "json");

    @ParameterizedTest(name = "{index}: Testing default with extension={0}")
    @MethodSource("argumentsDefault")
    void testDefault(final String extension) throws Exception {
        String beforePath = "%s/before.%s".formatted(RESOURCES_DIR, extension);
        String afterPath = "%s/after.%s".formatted(RESOURCES_DIR, extension);
        String expectedOutputPath = "%s/expected_output_stylish.txt".formatted(RESOURCES_DIR);
        String expectedOutput = Files.readString(Path.of(expectedOutputPath));
        String actualOutput = Differ.generate(beforePath, afterPath);
        assertEquals(expectedOutput, actualOutput);
    }

    @ParameterizedTest(name = "{index}: Testing parametrized with extension={0} an format={1}")
    @MethodSource("argumentsParametrized")
    void testParametrized(final String extension, final String format) throws Exception {
        String beforePath = "%s/before.%s".formatted(RESOURCES_DIR, extension);
        String afterPath = "%s/after.%s".formatted(RESOURCES_DIR, extension);
        String expectedOutputPath = "%s/expected_output_%s.txt".formatted(RESOURCES_DIR, format);
        String expectedOutput = Files.readString(Path.of(expectedOutputPath));
        String actualOutput = Differ.generate(beforePath, afterPath, format);
        assertEquals(expectedOutput, actualOutput);
    }

    private static Stream<Arguments> argumentsDefault() {
        return EXTENSIONS.stream().map(Arguments::of);
    }

    private static List<Arguments> argumentsParametrized() {
        List<Arguments> result = new ArrayList<>();
        for (String extension : EXTENSIONS) {
            for (String format : FORMATS) {
                result.add(Arguments.of(extension, format));
            }
        }
        return result;
    }
}

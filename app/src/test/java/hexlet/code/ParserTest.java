package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public final class ParserTest {
    /**
     * Расположение тестовых файлов.
     */
    private static final String RESOURCES_PATH = "src/test/resources/";

    @ParameterizedTest(name = "{index}: Testing parsing of file: {0}")
    @ValueSource(strings = {"parser.json", "parser.yml"})
    public void testParseFile(final String fileName) throws IOException {
        String fileToTest = RESOURCES_PATH + fileName;
        Map<String, Object> expectedMap = generateExpectedMap();

        Map<String, Object> actualMap = Parser.parseFile(fileToTest);
        assertEquals(expectedMap, actualMap);
    }

    private Map<String, Object> generateExpectedMap() {
        final int setting2 = 300;
        final List<Integer> numbers1 = List.of(1, 2, 3, 4);
        final List<Integer> numbers2 = List.of(22, 33, 44, 55);
        final List<Integer> numbers4 = List.of(4, 5, 6);
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("setting1", "Another value");
        expectedMap.put("setting2", setting2);
        expectedMap.put("setting3", "none");
        expectedMap.put("key2", "value2");
        expectedMap.put("numbers1", numbers1);
        expectedMap.put("numbers2", numbers2);
        expectedMap.put("id", null);
        expectedMap.put("default", List.of("value1", "value2"));
        expectedMap.put("checked", true);
        expectedMap.put("numbers4", numbers4);
        expectedMap.put("chars1", List.of("a", "b", "c"));
        expectedMap.put("chars2", false);
        expectedMap.put("obj1", Map.of("nestedKey", "value", "isNested", true));
        return expectedMap;
    }
}

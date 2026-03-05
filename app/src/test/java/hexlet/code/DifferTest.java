package hexlet.code;

import hexlet.code.diff.DiffEntry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static hexlet.code.diff.ActionType.ADDED;
import static hexlet.code.diff.ActionType.CHANGED;
import static hexlet.code.diff.ActionType.REMOVED;
import static hexlet.code.diff.ActionType.UNCHANGED;
import static hexlet.code.diff.Differ.compareMaps;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public class DifferTest {
    @Test
    public void testDiffer() {
        Map<String, Object> beforeMap = Map.of(
                "setting1", "unchanged",
                "setting2", "changed",
                "setting3", "removed");
        Map<String, Object> afterMap = Map.of(
                "setting1", "unchanged",
                "setting2", List.of(1, 2, 3),
                "setting4", Map.of("action", "added", "value", "newValue"));
        List<DiffEntry> expectedResult = List.of(
                new DiffEntry("setting1", "unchanged", "unchanged", UNCHANGED),
                new DiffEntry("setting2", "changed", List.of(1, 2, 3), CHANGED),
                new DiffEntry("setting3", "removed", null, REMOVED),
                new DiffEntry("setting4", null,
                        Map.of("action", "added", "value", "newValue"), ADDED)
        );
        List<DiffEntry> actualResult = compareMaps(beforeMap, afterMap);
        assertEquals(expectedResult, actualResult);
    }
}

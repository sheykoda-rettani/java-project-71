package hexlet.code.diff;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.FormatterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static hexlet.code.Parser.parseFile;

public final class Differ {
    private Differ() {

    }

    public static String generateDiffString(
            final String filePath1,
            final String filePath2,
            final String format
    ) throws IOException {
        Map<String, Object> beforeMap = parseFile(filePath1);
        Map<String, Object> afterMap = parseFile(filePath2);
        List<DiffEntry> compareResults = compareMaps(beforeMap, afterMap);
        Formatter formatter = FormatterFactory.createFormatter(format);
        return formatter.format(compareResults);
    }

    public static List<DiffEntry> compareMaps(final Map<String, Object> beforeMap,
                                               final Map<String, Object> afterMap) {
        List<DiffEntry> compareResult = new ArrayList<>();
        Set<String> allKeys = new TreeSet<>(beforeMap.keySet());
        allKeys.addAll(afterMap.keySet());
        for (String key : allKeys) {
            if (!beforeMap.containsKey(key)) {
                compareResult.add(new DiffEntry(key, null, afterMap.get(key), ActionType.ADDED));
            } else if (!afterMap.containsKey(key)) {
                compareResult.add(new DiffEntry(key, beforeMap.get(key), null, ActionType.REMOVED));
            } else if (!Objects.equals(beforeMap.get(key), afterMap.get(key))) {
                compareResult.add(new DiffEntry(key, beforeMap.get(key), afterMap.get(key), ActionType.CHANGED));
            } else {
                compareResult.add(new DiffEntry(key, beforeMap.get(key), afterMap.get(key), ActionType.UNCHANGED));
            }
        }
        return compareResult;
    }
}

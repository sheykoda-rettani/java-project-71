package hexlet.code;

import hexlet.code.diff.ActionType;
import hexlet.code.diff.DiffEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class DiffBuilder {
    private DiffBuilder() { }

    public static List<DiffEntry> findDiff(final Map<String, Object> beforeMap,
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

package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class DiffChecker {
    private DiffChecker() {

    }

    public static String compareMaps(final Map<String, Object> beforeMap, final Map<String, Object> afterMap) {
        final String added = "  + ";
        final String removed = "  - ";
        final String separator = ": ";
        final String unChanged = "    ";
        Set<String> allKeys = new TreeSet<>(beforeMap.keySet());
        allKeys.addAll(afterMap.keySet());

        StringBuilder resultBuilder = new StringBuilder("{").append(System.lineSeparator());
        for (String key : allKeys) {
            if (!beforeMap.containsKey(key)) {
                resultBuilder.append(added).append(key).append(separator).append(afterMap.get(key));
            } else if (!afterMap.containsKey(key)) {
                resultBuilder.append(removed).append(key).append(separator).append(beforeMap.get(key));
            } else if (!Objects.equals(beforeMap.get(key), afterMap.get(key))) {
                resultBuilder.append(removed).append(key).append(separator).append(beforeMap.get(key));
                resultBuilder.append(System.lineSeparator());
                resultBuilder.append(added).append(key).append(separator).append(afterMap.get(key));
            } else {
                resultBuilder.append(unChanged).append(key).append(separator).append(beforeMap.get(key));
            }
            resultBuilder.append(System.lineSeparator());
        }
        resultBuilder.append("}");
        return resultBuilder.toString();
    }
}

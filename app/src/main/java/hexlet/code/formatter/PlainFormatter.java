package hexlet.code.formatter;

import hexlet.code.diff.ActionType;
import hexlet.code.diff.DiffEntry;

import java.util.List;
import java.util.Map;

public final class PlainFormatter implements Formatter {
    @Override
    public String format(final List<DiffEntry> diffResults) {
        if (diffResults == null || diffResults.isEmpty()) {
            throw new IllegalArgumentException("Comparison result list is empty or null and cannot be formatted.");
        }

        StringBuilder output = new StringBuilder();

        for (DiffEntry entry : diffResults) {
            String propertyName = entry.key();
            ActionType action = entry.actionType();

            switch (action) {
                case ADDED -> output.append(buildAddedMessage(propertyName, entry.newValue()));
                case REMOVED -> output.append(buildRemovedMessage(propertyName));
                case CHANGED -> output.append(buildUpdatedMessage(propertyName, entry.oldValue(), entry.newValue()));
                case UNCHANGED -> { }
                default -> throw new IllegalStateException("Unexpected value: " + action);
            }
        }

        return output.toString();
    }

    private String buildAddedMessage(final String propertyName, final Object newValue) {
        return "Property '%s' was added with value: %s%n".formatted(propertyName, formatValue(newValue));
    }

    private String buildRemovedMessage(final String propertyName) {
        return "Property '%s' was removed%n".formatted(propertyName);
    }

    private String buildUpdatedMessage(final String propertyName, final Object oldValue, final Object newValue) {
        return "Property '%s' was updated. From %s to %s%n".
                formatted(propertyName, formatValue(oldValue), formatValue(newValue));
    }

    private String formatValue(final Object value) {
        if (isComplexObject(value)) {
            return "[complex value]";
        }
        return String.valueOf(value);
    }

    private boolean isComplexObject(final Object obj) {
        return obj instanceof Map || obj instanceof List;
    }
}

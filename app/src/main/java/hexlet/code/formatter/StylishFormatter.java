package hexlet.code.formatter;

import hexlet.code.diff.DiffEntry;

import java.util.List;

public final class StylishFormatter implements Formatter {

    @Override
    public String format(final List<DiffEntry> diffResults) {
        if (diffResults == null || diffResults.isEmpty()) {
            throw new IllegalArgumentException("Comparison result list is empty or null and cannot be formatted.");
        }

        final String added = "  + ";
        final String removed = "  - ";
        final String unChanged = "    ";

        StringBuilder outputBuilder = new StringBuilder("{").append(System.lineSeparator());
        for (DiffEntry entry: diffResults) {
            switch (entry.actionType()) {
                case ADDED -> appendChangeLine(outputBuilder, added, entry.key(), entry.newValue());
                case REMOVED -> appendChangeLine(outputBuilder, removed, entry.key(), entry.oldValue());
                case CHANGED -> {
                    appendChangeLine(outputBuilder, removed, entry.key(), entry.oldValue());
                    appendChangeLine(outputBuilder, added, entry.key(), entry.newValue());
                }
                case UNCHANGED -> appendChangeLine(outputBuilder, unChanged, entry.key(), entry.oldValue());
                default -> throw new IllegalStateException("Unexpected value of action type: " + entry.actionType());
            }
        }
        outputBuilder.append("}");
        return outputBuilder.toString();
    }

    private void appendChangeLine(final StringBuilder builder,
                                  final String prefix,
                                  final String key,
                                  final Object value) {
        builder.append("%s%s: %s%n".formatted(prefix, key, value));
    }
}

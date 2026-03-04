package hexlet.code.formatter.impl;

import hexlet.code.diff.DiffEntry;
import hexlet.code.formatter.Formatter;

import java.util.List;

public final class StylishFormatter implements Formatter {

    @Override
    public String format(final List<DiffEntry> compareResults) {
        if (compareResults == null || compareResults.isEmpty()) {
            throw new IllegalArgumentException("Comparison result list is empty or null and cannot be formatted.");
        }

        final String added = "  +";
        final String removed = "  -";
        final String unChanged = "   ";

        StringBuilder outputBuilder = new StringBuilder("{").append(System.lineSeparator());
        for (DiffEntry entry: compareResults) {
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
                                  final Object value
    ) {
        builder.append("%s %s: %s%n".formatted(prefix, key, value));
    }
}

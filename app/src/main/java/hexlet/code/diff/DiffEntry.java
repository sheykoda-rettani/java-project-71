package hexlet.code.diff;

public record DiffEntry(String key, Object oldValue, Object newValue, ActionType actionType) { }

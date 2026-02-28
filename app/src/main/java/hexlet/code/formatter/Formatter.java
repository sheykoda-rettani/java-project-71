package hexlet.code.formatter;

import hexlet.code.diff.DiffEntry;

import java.util.List;

public interface Formatter {
    /**
     * Превращает результаты сравнения в отформатированную строку.
     * @param diffResult список отличий
     * @return отформатированная строка
     */
    String format(List<DiffEntry> diffResult) throws IllegalArgumentException;
}

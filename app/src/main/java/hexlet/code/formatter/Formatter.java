package hexlet.code.formatter;

import hexlet.code.diff.DiffEntry;

import java.util.List;

public interface Formatter {
    /**
     * Превращает результаты сравнения в отформатированную строку.
     * @param diffResults список отличий
     * @return отформатированная строка
     * @throws IllegalArgumentException если аргумент некорректен
     * @throws IllegalStateException если состояние системы неверно
     */
    String format(List<DiffEntry> diffResults);
}

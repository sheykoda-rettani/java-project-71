package hexlet.code;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.FormatterFactory;
import hexlet.code.diff.DiffEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static hexlet.code.DiffBuilder.findDiff;
import static hexlet.code.Parser.toKeyValuePairs;

public final class Differ {
    /**
     * Текст ошибки для пустого или null имени файла.
     */
    public static final String FILE_NAME_NOT_NULL_OR_EMPTY = "File name shouldn't be null or empty.";

    private Differ() { }

    public static String generate(
        final String filePath1,
        final String filePath2
    ) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(
            final String filePath1,
            final String filePath2,
            final String format
    ) throws IOException {
        FileData beforeData = extractData(filePath1);
        Map<String, Object> beforeMap = toKeyValuePairs(beforeData);
        FileData afterData = extractData(filePath2);
        Map<String, Object> afterMap = toKeyValuePairs(afterData);
        List<DiffEntry> compareResults = findDiff(beforeMap, afterMap);
        Formatter formatter = FormatterFactory.createFormatter(format);
        return formatter.format(compareResults);
    }

    /**
     * Извлекает содержимое файла и определяет его тип по указанному пути.
     *
     * @param filename Абсолютный или относительный путь к файлу
     * @return Экземпляр FileData с текстом файла и типом
     * @throws IOException в случае проблем с доступом к файлу
     * @throws IllegalArgumentException если расширение файла не поддерживается
     */
    public static FileData extractData(final String filename) throws IOException, IllegalArgumentException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException(FILE_NAME_NOT_NULL_OR_EMPTY);
        }

        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("The specified file does not exist: %s".formatted(filename));
        }

        byte[] contentBytes = Files.readAllBytes(path);
        String fileText = new String(contentBytes, StandardCharsets.UTF_8);

        FileKind fileKind = extractFileKind(filename);

        return new FileData(fileText, fileKind);
    }

    public static FileKind extractFileKind(final String filename) throws IllegalArgumentException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException(FILE_NAME_NOT_NULL_OR_EMPTY);
        }

        int lastDotIndex = filename.lastIndexOf('.');

        if (lastDotIndex <= 0 || lastDotIndex >= filename.length() - 1) {
            throw new IllegalArgumentException("Wrong file format: '%s'".formatted(filename));
        }

        String fileExtension = filename.substring(lastDotIndex).toLowerCase();
        return FileKind.forExtString(fileExtension);
    }
}

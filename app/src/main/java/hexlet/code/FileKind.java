package hexlet.code;

import java.util.HashSet;
import java.util.Set;

public enum FileKind {
    JSON(".json"),
    YAML(".yaml", ".yml");

    /**
     * Основное расширение вида файла.
     */
    private final String primaryExtension;

    /**
     * все расширения вида файла.
     */
    private final Set<String> allExtensions;

    FileKind(final String... extensions) {
        this.primaryExtension = extensions[0];
        this.allExtensions = new HashSet<>(Set.of(extensions));
    }

    public String getPrimaryExtension() {
        return primaryExtension;
    }

    public Set<String> getAllExtensions() {
        return allExtensions;
    }

    public boolean isSupported(final String ext) {
        return allExtensions.contains(ext.toLowerCase());
    }

    /**
     * Получает FileKind по переданной строке расширения.
     *
     * @param ext Строка расширения файла
     * @return Элемент FileKind
     * @throws IllegalArgumentException если расширение не поддерживается
     */
    public static FileKind forExtString(final String ext) {
        if (ext == null || ext.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка расширения должна быть задана.");
        }

        String normalizedExt = ext.trim().toLowerCase();

        for (FileKind kind : values()) {
            if (kind.isSupported(normalizedExt)) {
                return kind;
            }
        }

        throw new IllegalArgumentException("Расширение '%s' не поддерживается.".formatted(ext));
    }
}

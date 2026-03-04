package hexlet.code.formatter;

import hexlet.code.formatter.impl.JsonFormatter;
import hexlet.code.formatter.impl.PlainFormatter;
import hexlet.code.formatter.impl.StylishFormatter;

import java.util.Locale;

public final class FormatterFactory {
    private FormatterFactory() {

    }

    public static Formatter createFormatter(final String format) {
        return switch (format.trim().toLowerCase(Locale.ENGLISH)) {
            case "stylish" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            case "json" -> new JsonFormatter();
            default -> throw new IllegalArgumentException("Unknown format value: '" + format + "'");
        };
    }
}

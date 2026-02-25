package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.",
        showDefaultValues = true)
public final class App implements Callable<Integer> {
    /**
     * Для парсинга JSON файлов.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Help-комманда.
     */
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean helpRequested;

    /**
     * Version-комманда.
     */
    @Option(names = {"-V", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean versionInfoRequested;

    /**
     * Путь к 1-му файлу.
     */
    @CommandLine.Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    /**
     * Путь ко 2-му файлу.
     */
    @CommandLine.Parameters(index = "1", paramLabel = "filepath2", description = "path to first file")
    private String filePath2;

    public static void main(final String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        Map<String, Object> beforeMap = parseFile(filePath1);
        Map<String, Object> afterMap = parseFile(filePath2);
        System.out.println(compareMaps(beforeMap, afterMap));
        return CommandLine.ExitCode.OK;
    }

    private static String compareMaps(final Map<String, Object> beforeMap, final Map<String, Object> afterMap) {
        final String added = "  + ";
        final String removed = "  - ";
        final String separator = ": ";
        final String unChanged = "   ";
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

    private Map<String, Object> parseFile(final String filePath) throws IOException {
        try {
            byte[] contentBytes = Files.readAllBytes(Path.of(filePath));
            return objectMapper.readValue(contentBytes, new TypeReference<>() { });
        } catch (IOException e) {
            throw new IOException("Error while parsing file: " + filePath, e);
        }
    }
}

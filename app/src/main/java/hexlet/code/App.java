package hexlet.code;

import java.util.Map;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static hexlet.code.DiffChecker.compareMaps;
import static hexlet.code.Parser.parseFile;

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
}

package hexlet.code;

import java.util.concurrent.Callable;

import hexlet.code.diff.Differ;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
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
     * Формат вывода.
     */
    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]", defaultValue = "stylish")
    private String outputFormat;

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
        String diff = Differ.generateDiffString(filePath1, filePath2, outputFormat);
        System.out.println(diff);
        return CommandLine.ExitCode.OK;
    }
}

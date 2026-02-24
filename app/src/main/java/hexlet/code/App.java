package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
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
    ObjectMapper objectMapper = new ObjectMapper();
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean helpRequested;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean versionInfoRequested;

    @CommandLine.Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @CommandLine.Parameters(index = "1", paramLabel = "filepath2", description = "path to first file")
    private String filePath2;

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        Map<String, Object> file1Values = parseFile(filePath1);
        Map<String, Object> file2Values = parseFile(filePath2);
        System.out.println(file1Values);
        System.out.println(file2Values);
        return CommandLine.ExitCode.OK;
    }

    private Map<String, Object> parseFile(String filePath) throws IOException {
        try {
            byte[] contentBytes = Files.readAllBytes(Path.of(filePath)); // Читаем весь файл в байтовый массив
            return objectMapper.readValue(contentBytes, new TypeReference<>() {}); // Десериализируем контент в map
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении файла: " + filePath, e);
        }
    }
}
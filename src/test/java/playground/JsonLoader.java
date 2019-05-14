package playground;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class JsonLoader {

    private static final String FILE_NAME = "sample.json";

    private JsonLoader() {
    }

    public static String json() {
        Path filePath = getFilePath();
        return new String(loadBytes(filePath), Charset.forName("UTF-8"));
    }

    private static Path getFilePath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            return Paths.get(classLoader.getResource(FILE_NAME).toURI());
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("File not found");
        }
    }

    private static byte[] loadBytes(Path filePath) {
        try {
            return Files.readAllBytes(filePath);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read file");
        }
    }
}

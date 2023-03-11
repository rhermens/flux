
package nl.rhdev.wordpressrepository.zip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import lombok.Getter;

public class WordPressZip {

    @Getter
    private final ZipFile zipFile;

    public WordPressZip(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public void close() throws IOException {
        zipFile.close();
    }

    public String getRootDirectory() throws NoSuchElementException {
        ZipEntry zipEntryRootDirectory = zipFile.stream()
            .filter(e -> e.isDirectory())
            .findFirst()
            .orElseThrow();

        return zipEntryRootDirectory.getName().replaceAll("/", "");
    }

    public String readFile(Path file) throws IOException, NoSuchElementException {
        ZipEntry headerEntry = zipFile.stream()
            .filter(e -> e.getName().equals(file.toString()))
            .findFirst()
            .orElseThrow();
        InputStream zipStream = zipFile.getInputStream(headerEntry);
        BufferedReader reader = new BufferedReader(new InputStreamReader(zipStream));
        StringBuilder builder = new StringBuilder();

        int r;
        while ((r = reader.read()) != -1) {
            char c = (char)r;
            builder.append(c);

            if (c == '/' && builder.toString().endsWith("*/")) {
                break;
            }
        }

        reader.close();
        zipStream.close();

        return builder.toString();
    }
}

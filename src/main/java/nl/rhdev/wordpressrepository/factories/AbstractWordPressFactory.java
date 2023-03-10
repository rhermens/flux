package nl.rhdev.wordpressrepository.factories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nl.rhdev.wordpressrepository.marshal.HeaderMarshaller;

public abstract class AbstractWordPressFactory {

    protected static String readHeaderFile(String file, ZipFile zipFile) throws IOException, NoSuchElementException {
        ZipEntry headerEntry = zipFile.stream()
            .filter(e -> e.getName().equals(file))
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

    protected static <T> void unmarshalHeader(String rawHeader, T header) {
        HeaderMarshaller<T> marshaller = new HeaderMarshaller<>();
        marshaller.unmarshal(rawHeader, header);
    }

    protected static String getRootDirectory(ZipFile zipFile) throws NoSuchElementException {
        ZipEntry zipEntryRootDirectory = zipFile.stream()
            .filter(e -> e.isDirectory())
            .findFirst()
            .orElseThrow();

        return zipEntryRootDirectory.getName().replaceAll("/", "");
    }
}

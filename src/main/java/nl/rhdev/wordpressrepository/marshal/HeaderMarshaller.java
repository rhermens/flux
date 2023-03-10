package nl.rhdev.wordpressrepository.marshal;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import nl.rhdev.wordpressrepository.annotations.HeaderKey;

public class HeaderMarshaller<T> {

    public void unmarshal(String header, T obj) {
        header = header.substring(header.indexOf("/*"), header.indexOf("*/"));
        header = header.replaceAll("\\*/", "");
        header = header.replaceAll("\\*", "");

        Stream.of(header.split("\n"))
            .map(String::trim)
            .filter(l -> !l.isEmpty() && l.contains(":"))
            .map(l -> {
                String[] split = l.split(":", 2);
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                return split;
            })
            .forEach(kv -> {
                try {
                    Field field = Stream.of(obj.getClass().getFields())
                        .filter(f -> f.getAnnotation(HeaderKey.class).normalizedKey().equals(kv[0].toUpperCase()))
                        .findFirst()
                        .orElseThrow();
                    field.set(obj, kv[1]);
                } catch (NoSuchElementException|IllegalAccessException e) {}
            });
    }
}


package com.sergent.disc.file.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sergent.disc.file.exception.UnableToAccessResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DirectoryFileReaderService {

    @Value("${file.location}")
    String fileLocation;

    public Set<Path> searchForFiles(final List<String> words) throws UnableToAccessResourceException {
        final Set<Path> collectedPaths = new HashSet<>();
        try (Stream<Path> paths = Files.walk(Path.of(fileLocation))) {
            paths.peek(path -> matchWordsInline(words, collectedPaths, path)).count();// necessary to terminate the stream
        } catch (IOException e) {
            throw new UnableToAccessResourceException();
        }
        return collectedPaths;

    }

    private void matchWordsInline(final List<String> words, final Set<Path> collectedPaths, final Path path) {
        try {
            List<String> lines;
            if (!Files.isDirectory(path)) {
                lines = Files.readAllLines(path);
                Set<Path> foundPaths = lines.stream().map(line -> isWordInLine(words, line, path)).collect(Collectors.toSet());
                collectedPaths.addAll(foundPaths);
            }
        } catch (Exception e) {
            // we can ignore this we read text and csv files others we ignore for now
            log.error("Error reading paths... continue " + e.getMessage());
        }
    }

    private Path isWordInLine(final List<String> words, final String line, final Path path) {
        List<String> foundWords = words.stream().filter(line::contains).collect(Collectors.toList());
        Set<String> found = words.stream().filter(word -> foundWords.stream().anyMatch(word::equals)).collect(Collectors.toSet());
        return !found.isEmpty() ? path : null;
    }

}

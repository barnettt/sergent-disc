package com.sargentdisc.file.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sargentdisc.file.exception.UnableToAccessResourceException;
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

    public String getFileContent(String filePath) {
        List<String> lines = null;
        try {
             lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new UnableToAccessResourceException("Error reading file content");
        }
        final StringBuffer buffer = new StringBuffer();
        if(lines != null) {
            lines.forEach(line -> {buffer.append(line);
                buffer.append("\n");});
        } else {
            throw new UnableToAccessResourceException("File Not Found");
        }
         return buffer.toString();
    }

    private void matchWordsInline(final List<String> words, final Set<Path> collectedPaths, final Path path) {
        try {
            List<String> lines;
            if (!Files.isDirectory(path)) {
                lines = Files.readAllLines(path);
                List<Path> foundPaths = lines.stream().map(line -> isWordInLine(words, line,
                                path))
                        .filter(Objects::nonNull).collect(Collectors.toList());
                if (foundPaths.size() >= words.size()) { // then all words found in file preserve this path
                    collectedPaths.addAll(foundPaths);
                }
            }
        } catch (Exception e) {
            // we can ignore this we read text and csv files others we ignore for now
            log.error("Error reading paths... continue " + e.getMessage());
        }
    }

    private Path isWordInLine(final List<String> words, final String line,
                              final Path path) {
        List<String> foundWords = words.stream().filter(line::contains).collect(Collectors.toList());
        Set<String> found = words.stream()
                .filter(word -> foundWords.stream().anyMatch(word::equals))
                .collect(Collectors.toSet());
        return !found.isEmpty() ? path : null;
    }

}

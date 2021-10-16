package com.sergent.disc.file.reader;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.sergent.disc.file.exception.UnableToAccessResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})

public class DirectoryScanTest {

    DirectoryFileReaderService reader;


    @BeforeEach
    void setup() {
        reader = new DirectoryFileReaderService();
        ReflectionTestUtils.setField(reader, "fileLocation", "/Users/abuayyub/git/test-files");
    }

    @ParameterizedTest
    @MethodSource
    void shouldSearchDirectoryForFilesContaining(List<String> words) {
        Set<Path> foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals(3,foundFilePaths.size() ); // one null value and 2 file paths
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindInCsvFilesContaining(List<String> words) {
        Set<Path>  foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals( 3, foundFilePaths.size()); // one null value and 2 file paths
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindMultipleFilesContaining(List<String> words)  {
        Set<Path>  foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals(6, foundFilePaths.size());// 1 null and 5 file paths
    }

    @ParameterizedTest
    @MethodSource
    void shouldThrowResourceInAccessibleException(List<String> words)  {
        ReflectionTestUtils.setField(reader, "fileLocation", "/Users/abuayyub/git/test-files/empty");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> reader.searchForFiles(words), "Unable to load files from resource directory");
    }


    static Stream<Arguments> shouldSearchDirectoryForFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Locale");
        return Stream.of(Arguments.of(searchCriteria));  // expect 1 file
    }

    static Stream<Arguments> shouldFindInCsvFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Furniture");
        searchCriteria.add("Washroom");

        return Stream.of(Arguments.of(searchCriteria));
    }

    static Stream<Arguments> shouldFindMultipleFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Furniture");
        searchCriteria.add("Washroom");
        searchCriteria.add("administrator");
        searchCriteria.add("pleasedlnow");
        searchCriteria.add("Locale");
        return Stream.of(Arguments.of(searchCriteria));
    }

    static Stream<Arguments>  shouldThrowResourceInAccessibleException() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Locale");
        return Stream.of(Arguments.of(searchCriteria));  // expect 1 file

    }

}

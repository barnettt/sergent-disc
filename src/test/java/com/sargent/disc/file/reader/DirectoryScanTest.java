package com.sargent.disc.file.reader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.sargent.disc.file.SergentDiscTestAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

public class DirectoryScanTest extends SergentDiscTestAbstract {

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
        Assertions.assertEquals(2,foundFilePaths.size() );
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindInCsvFilesContaining(List<String> words) {
        Set<Path>  foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals( 2, foundFilePaths.size());
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindFilesContaining(List<String> words)  {
        Set<Path>  foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals(1, foundFilePaths.size());
    }

    @ParameterizedTest
    @MethodSource
    void shouldNotFindFilesNotAllWordsContained(List<String> words)  {
        Set<Path>  foundFilePaths = reader.searchForFiles(words);
        Assertions.assertNotNull(foundFilePaths);
        Assertions.assertEquals(0, foundFilePaths.size());
    }

    @ParameterizedTest
    @MethodSource
    void shouldThrowResourceInAccessibleException(List<String> words)  {
        ReflectionTestUtils.setField(reader, "fileLocation", LOCATION+"/empty");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> reader.searchForFiles(words)
                , "Unable to load files from resource directory");
    }


    static Stream<Arguments> shouldSearchDirectoryForFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Locale");
        return Stream.of(Arguments.of(searchCriteria));
    }

    static Stream<Arguments> shouldFindInCsvFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Furniture");
        searchCriteria.add("Washroom");

        return Stream.of(Arguments.of(searchCriteria));
    }

    static Stream<Arguments> shouldFindFilesContaining() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("administrator");
        searchCriteria.add("pleasedlnow");
        return Stream.of(Arguments.of(searchCriteria));
    }

    static Stream<Arguments> shouldNotFindFilesNotAllWordsContained() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("administrator");
        searchCriteria.add("Furniture");
        return Stream.of(Arguments.of(searchCriteria));

    }
    static Stream<Arguments>  shouldThrowResourceInAccessibleException() {
        List<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Locale");
        return Stream.of(Arguments.of(searchCriteria));

    }

}

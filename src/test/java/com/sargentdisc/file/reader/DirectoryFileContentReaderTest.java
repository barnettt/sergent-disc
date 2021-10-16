package com.sargentdisc.file.reader;

import com.sargentdisc.file.SargentDiscTestAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class DirectoryFileContentReaderTest extends SargentDiscTestAbstract {

    DirectoryFileReaderService reader;

    @BeforeEach
    void setup() {
        reader = new DirectoryFileReaderService();
        ReflectionTestUtils.setField(reader, "fileLocation", LOCATION);
    }

    @DisplayName("I want to see file not found message")
    @Test
    void shouldReturnFileNotFoundMessage() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        reader.getFileContent(LOCATION + "/jamiDoger.log")
                , "File not found");
    }

    @DisplayName("I want to see file content")
    @Test
    void shouldFindFilesContaining() {
        String foundFileContent = reader.getFileContent(LOCATION + "/jmeter.log");
        Assertions.assertNotNull(foundFileContent);
        Assertions.assertTrue(foundFileContent.length() > 0);
    }
}

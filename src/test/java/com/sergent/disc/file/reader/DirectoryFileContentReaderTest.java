package com.sergent.disc.file.reader;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import com.sergent.disc.file.SergentDiscTestAbstract;
import com.sergent.disc.file.exception.UnableToAccessResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

public class DirectoryFileContentReaderTest extends SergentDiscTestAbstract {

    DirectoryFileReaderService reader;

    @BeforeEach
    void setup() {
        reader = new DirectoryFileReaderService();
        ReflectionTestUtils.setField(reader, "fileLocation", LOCATION);
    }

    @DisplayName("I want to see file not found message")
    @Test
    void shouldReturnFileNotFoundMessage()  {
        org.assertj.core.api.Assertions.assertThatThrownBy(()->
                        reader.getFileContent(LOCATION+"/jamiDoger.log")
                ,"File not found");
    }

    @DisplayName("I want to see file content")
    @Test
    void shouldFindFilesContaining()  {
        String foundFileContent = reader.getFileContent(LOCATION+"/jmeter.log");
        Assertions.assertNotNull(foundFileContent);
        Assertions.assertTrue(foundFileContent.length() > 0);
    }
}

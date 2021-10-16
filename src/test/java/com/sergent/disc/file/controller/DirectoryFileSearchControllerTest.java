package com.sergent.disc.file.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sergent.disc.file.reader.DirectoryFileReaderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest()
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@AutoConfigureMockMvc
public class DirectoryFileSearchControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    DirectoryFileReaderService service;

    @Autowired
    DirectoryFileSearchController directoryFileSearchController;

    @DisplayName("I want to see files containing word")
    @Test
    void shouldGetFilePathsFromDirectoryService() throws Exception {
        Set<Path> expected = new HashSet<>();
        expected.add(Paths.get("/path/to/file/one.text"));
        expected.add(Paths.get("/path/to/file/two.text"));
        when(service.searchForFiles(anyList())).thenReturn(expected);
        MvcResult result = mvc.perform(get("/sergent-disc/v0.1/file/search")
                        .param("searchCriteria", "Locale")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsString().contains(expected.toString()));
      //  Assertions.assertThat(result.getResponse().getContentAsString().contains(expected.get(1)));
    }

}

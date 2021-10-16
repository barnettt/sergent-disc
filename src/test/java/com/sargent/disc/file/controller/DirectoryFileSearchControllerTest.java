package com.sargent.disc.file.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.sargent.disc.file.SergentDiscTestAbstract;
import com.sargent.disc.file.reader.DirectoryFileReaderService;
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


@SpringBootTest()
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@AutoConfigureMockMvc
public class DirectoryFileSearchControllerTest extends SergentDiscTestAbstract  {

    @Autowired
    MockMvc mvc;

    @MockBean
    DirectoryFileReaderService service;

    @Autowired
    DirectoryFileSearchController directoryFileSearchController ;

    @DisplayName("I want to see files containing word")
    @Test
    void shouldGetFilePathsFromDirectoryService() throws Exception {
        Set<Path> expected = new HashSet<>();
        expected.add(Paths.get(LOCATION+"/jmeter.log"));
        expected.add(Paths.get(LOCATION+"/jmeter-log.log"));
        when(service.searchForFiles(anyList())).thenReturn(expected);
        MvcResult result = mvc.perform(get("/sergent-disc/v0.1/file/search")
                        .param("searchCriteria", "Locale")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // expecting two files
        assertTrue(result.getResponse().getContentAsString().contains(expected.iterator().next().toString()));
        assertTrue(result.getResponse().getContentAsString().contains(expected.iterator().next().toString()));
    }

}

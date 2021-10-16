package com.sargentdisc.file.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.sargentdisc.file.SargentDiscTestAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest()
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@AutoConfigureMockMvc
class DirectoryFileSearchIntegrationTest extends SargentDiscTestAbstract {


    @Autowired
    MockMvc mvc;

    @DisplayName("I want to see file containing words")
    @Test
    void shouldGetFileContentFromDirectoryService() throws Exception {
        Set<Path> expected = new HashSet<>();
        expected.add(Paths.get(LOCATION + "/jmeter.log"));
        expected.add(Paths.get(LOCATION + "jmeter-log.log"));
        MvcResult result = mvc.perform(get("/sargent-disc/v0.1/file/content")
                        .param("fileName", LOCATION + "/jmeter.log")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertNotNull(result.getResponse().getContentAsString());
        Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
    }
}

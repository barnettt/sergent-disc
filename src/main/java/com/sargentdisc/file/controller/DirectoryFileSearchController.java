package com.sargentdisc.file.controller;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

import com.sargentdisc.file.reader.DirectoryFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DirectoryFileSearchController {

    private DirectoryFileReaderService service;

    @Autowired
    DirectoryFileSearchController(DirectoryFileReaderService service) {
        this.service = service;
    }

    @GetMapping(value = "sargentdisc/v01/file/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Path> findBySearchCriteria(@RequestParam String searchCriteria) {

        return service.searchForFiles(Arrays.asList(searchCriteria));
    }

    @GetMapping(value = "sargentdisc/v01/file/content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findByFileName(@RequestParam String fileName) {

        return service.getFileContent(fileName);
    }
}

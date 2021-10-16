package com.sargent.disc.file.controller;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

import com.sargent.disc.file.reader.DirectoryFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/sergent-disc/v0.1")
public class DirectoryFileSearchController {

    DirectoryFileReaderService service;

    @Autowired
    DirectoryFileSearchController(DirectoryFileReaderService service){
        this.service = service;
    }

    @GetMapping(value = "/file/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Path> findBySearchCriteria(@RequestParam(name="searchCriteria",required=false) String   searchCriteria) {
        String[] searchTerms = searchCriteria.split(",");
        return service.searchForFiles(Arrays.asList());
    }
}

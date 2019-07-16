package com.dynamiko.ml.api;

import com.dynamiko.ml.service.GenericService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/generic")
@Api(value = "generic", description = "The Generic API", tags = {"Generic"})
public class GenericController {

    @Autowired
    GenericService genericService;

    @GetMapping("/")
    public ResponseEntity<String> sampleML() {
        String str = genericService.doML();
        return new ResponseEntity(str, HttpStatus.OK);
    }
}

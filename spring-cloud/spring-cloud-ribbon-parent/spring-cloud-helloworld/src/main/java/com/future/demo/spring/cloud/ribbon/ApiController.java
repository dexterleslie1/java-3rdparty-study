package com.future.demo.spring.cloud.ribbon;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ApiController {
    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/v1/sayHello", method = RequestMethod.POST)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name + "!!(Ribbon)2";
    }
}

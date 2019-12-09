package com.duoying.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gezhiwei
 */
@RestController
public class TestController {
    @GetMapping("/sdf")
    public Object getMapp() {
        return "ok";
    }
}

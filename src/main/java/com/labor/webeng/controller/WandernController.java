package com.labor.webeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wandern")
public class WandernController {

    @GetMapping("/{location}")
    public Map<String, String> getWandern(@PathVariable String location) {
        return Map.of("location", location);
    }
}

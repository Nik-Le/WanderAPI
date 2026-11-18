package com.labor.webeng.controller;

import com.labor.webeng.model.Wandern;
import com.labor.webeng.service.WandernService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wandern")
public class WandernController {

    private final WandernService service;

    public WandernController(WandernService service) {
        this.service = service;
    }

    @GetMapping("/{city}")
    public Wandern getRecommendation(@PathVariable String city) {
        return service.getRecommendation(city);
    }

}
